/******************************************************************************
 ** ISCTE-IUL: Trabalho prático de Sistemas Operativos
 **
 ** Aluno: Nº:98591       Nome:Diogo Filipe Sádio Azeitona 
 ** Nome do Módulo: servidor.c
 ** Descrição/Explicação do Módulo: O processo Servidor é responsável pela atribuição de 
 **um enfermeiro para administrar as vacinas aoscidadãos que chegam aos Centros de Saúde.
 **Este módulo estará sempre ativo, à espera dachegada de cidadãos.
 ******************************************************************************/
#include <stdio.h>
#include <string.h>
#include <signal.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include "common.h"

//Variáveis globais
Cidadao cid;
Enfermeiro* enfermeiro;
Vaga vagas[NUM_VAGAS];
int vaga_atual;
int n;
int num_enfermeiros;
int valor_pausa;


// S1
//Caso o ficheiro FILE_PID_SERVIDOR exista (verifica a existência através do access), regista o PID do processo servidor no mesmo. Caso este ficheiro não exista transmite um erro uma vez que não consegue registar o PID pedido.
void registar_PID(){

FILE* file;
file=fopen(FILE_PID_SERVIDOR, "w");
if(file){
fprintf(file, "%d" , getpid());
sucesso("S1) Escrevi no ficheiro FILE_PID_SERVIDOR o PID: %d",getpid());
fclose(file);

}else{
erro("S1) Não consegui registar o servidor!");
}
}

// S2
//Define uma estrutura de dados dinâmica em memória, enfermeiros com o tamanho certo para comportar toda a lista de enfermeiros, sendo preenchida com os dados do ficheiro enfermeiros.dat.
void enfermeiros(){
FILE* file = fopen(FILE_ENFERMEIROS,"r");
if (file) {
   // Posiciona o indicador do ficheiro no final
   fseek(file,0,SEEK_END);
   // O tamanho do ficheiro será o valor da posição
   long filesize = ftell(file);
   
   num_enfermeiros=filesize/sizeof(Enfermeiro);
   sucesso("S2) Ficheiro FILE_ENFERMEIROS tem %ld bytes, ou seja, %d enfermeiros",filesize,num_enfermeiros);
   enfermeiro=(Enfermeiro*)malloc(num_enfermeiros*sizeof(Enfermeiro));
   
   for(int i=0; i<num_enfermeiros;i++){
   // Posiciona o indicador do ficheiro onde queremos
   fseek(file,i*sizeof(Enfermeiro),SEEK_SET);
   fread(&enfermeiro[i],sizeof(Enfermeiro),1,file);
   }
   
   
   }else{
   erro("S2) Não consegui ler o ficheiro FILE_ENFERMEIROS!");
   }
   fclose(file);
   }
   

// S3
//Inicializa a lista de vagas correspondentes ao index_enfermeiro com o valor "-1" para que a lista seja "limpa".
void inicializar(){
for(int i=0; i<NUM_VAGAS;i++){
vagas[i].index_enfermeiro=-1;
  }
  sucesso("S3) Iniciei a lista de %d vagas",NUM_VAGAS);
}

// S5.6.1
void terminou(){
kill(SIGTERM,cid.PID_cidadao);
sucesso("S5.6.1) SIGTERM recebido, servidor dedicado termina Cidadão");
}

// S5/S5.1
//Após receber o sinal SIGUSR1 esta função é invocada pela alínea S4 para que seja lida a informação que o ficheiro FILE_PEDIDO_VACINA contém sobre o cidadão, verificando primeiramente se este ficheiro existe e se é possível lê-lo.
void pausa(){
  n=0;
  FILE *file;
  if(access(FILE_PEDIDO_VACINA, F_OK)==0){
	file=fopen(FILE_PEDIDO_VACINA, "r");
  if (file){
  fscanf(file, "%d:%99[^:]:%d:%99[^:]:%9[^:]:%d:%d", &cid.num_utente,cid.nome,&cid.idade,cid.localidade,cid.nr_telemovel,&cid.estado_vacinacao,&cid.PID_cidadao);
  fclose(file);
	sucesso("S5.1) Dados Cidadão: %d; %s; %d; %s; %s; 0",cid.num_utente,cid.nome,cid.idade,cid.localidade,cid.nr_telemovel);

    }else{
        erro("S5.1) Não foi possível abrir o ficheiro FILE_PEDIDO_VACINA");
        return;
}
    }else{
    erro("S5.1) Não foi possível ler o ficheiro FILE_PEDIDO_VACINA");
    }
}


// S5.2.2
//É chamada somente se o enfermeiro do centro de saúde respetivo se encontrar disponível. Verifica se existe vaga para proceder à vacinação. Caso não exista vaga, transmite uma mensagem de erro e envia um sinal SIGTERM ao processo PID_cidadao para que o mesmo informe o processo cidadão de que a vacinação não é possível no momento, retornando ao ponto S5.
int valida_vaga(int j){
for(int i=0; i<NUM_VAGAS;i++){
if(vagas[i].index_enfermeiro==-1){
sucesso("S5.2.2) Há vaga para vacinação para o pedido %d",cid.PID_cidadao);
vaga_atual=i;
// S5.3
//Caso exista vaga para a realização da vacinação, esta função preenche a posição "i" com a informação submetida sobre o cidadão, alterando seguidamente a disponibilidade do enfermeiro para 0.
vagas[i].index_enfermeiro=j;
vagas[i].cidadao=cid;
enfermeiro[j].disponibilidade=0;
return 1;
}
}
erro("S5.2.2) Não há vaga para vacinação para o pedido %d",cid.PID_cidadao);
kill(cid.PID_cidadao,SIGTERM);
return 0;
}

void tirar_pausa(){
valor_pausa=0;
}

// S5.4
//O processo Servidor cria, através do fork, um processo Servidor-Filho. O inteiro criado na função ("filho") poderá assim ter 3 diferentes valores. Caso este valor seja 0, estamos a trabalhar no processo Servidor-Filho. Caso este valor seja -1, transmite o erro uma vez que não é possível criar o servidor, Caso este valor não seja nenhum dos referidos anteriormente, estamos a trabalhar no processo Servidor. 
void cria_filho(){
int filho=fork();
if(filho == 0){ 
//filho

// S5.6/S5.6.1
//Arma o sinal SIGTERM que poderá ser enviado pelo processo Servidor-Pai em caso de este terminar. Caso receba este sinal envia um sinal SIGTERM ao processo PID_cidadao para o informar que o processo de vacinação não é possível, terminando, seguidamente, o processo filho.
signal(SIGTERM,terminou);
sucesso("S5.6.1) SIGTERM recebido, servidor dedicado termina Cidadão");

// S5.6.2
//Envia um sinal do tipo SIGUSR1 ao processo PID_cidadao, informando o cidadão de que a sua vacinação começou.
kill(cid.PID_cidadao,SIGUSR1);
sucesso("S5.6.2) Servidor dedicado inicia consulta de vacinação");

// S5.6.3
//Espera o tempo correspondente à duração da vacinação (TEMPO_CONSULTA) escrevendo no ecrã sobre o término da mesma.
signal(SIGALRM,tirar_pausa);
alarm(TEMPO_CONSULTA);
valor_pausa=1;
while(valor_pausa){
pause();
}
sucesso("S5.6.3) Vacinação terminada para o cidadão com o pedido nº %d",cid.PID_cidadao);

// S5.6.4
//No final do processo de vacinação, envia um sinal do tipo SIGUSR2 ao processo PID_cidadao e termina o processo Servidor-Filho.
kill(cid.PID_cidadao,SIGUSR2);
sucesso("S5.6.4) Servidor dedicado termina consulta de vacinação");

}else if(filho==-1){
erro("S5.4) Não foi possível criar o servidor dedicado");


}else{ 
//pai
// S5.5/S5.5.1
//Atualiza a entrada na lista de Vagas preenchida anteriormente.
vagas[vaga_atual].PID_filho=filho;
sucesso("S5.5.1) Servidor dedicado %d na vaga %d",filho,vaga_atual+1);

// S5.5.2
//Arma o sinal SIGCHLD para acordar o processo Servidor quando o processo Servidor-filho terminar
signal(SIGCHLD, SIG_IGN); 
sucesso("S5.5.2) Servidor aguarda fim do servidor dedicado %d", vagas[vaga_atual].PID_filho);
  }
}

// S5.2/S5.2.1
//Verifica se o enfermeiro correspondente à localidade do cidadão registado se encontra disponível. Se este enfermeiro não se encontrar disponível, trata de enviar um sinal SIGUSR1 ao processo PID_cidadao para este informar o processo cidadão que a vacinação não é possível neste momento(kill(cid.PID_cidadao)),retornando ao ponto S5 como pedido. Caso o enfermeiro se encontre disponível, invoca a alínea S5.2.2 para verificar a existência de vaga. 
void enfermeiro_indisponivel(){
char CS[15];
strcpy(CS,"CS");
char CS_localidade[20];
strcpy(CS_localidade,cid.localidade);
strcat(CS,CS_localidade);
int valor;
for(int i=0; i<num_enfermeiros;i++){
char* Centro_Saude=enfermeiro[i].CS_enfermeiro;
      if(strcmp(CS,Centro_Saude)==0){
        if(enfermeiro[i].disponibilidade==0){
          erro("S5.2.1) Enfermeiro %d indisponível para o pedido %d para o Centro de Saúde %s\n", i, cid.PID_cidadao, cid.localidade);
          kill(cid.PID_cidadao,SIGTERM);
        }else{
          sucesso("S5.2.1) Enfermeiro %d disponível para o pedido %d\n", i, cid.PID_cidadao);
          valor=valida_vaga(i);
          break;
      }
    }
  }
  
  if(valor==1){
  cria_filho();
}
}

// S4
//Arma e trata o sinal SIGUSR1 para cuidar de todos os cidadãos que são introduzidos para serem vacinados. Esta função garante que exista uma espera passiva até que receba o sinal do processo cidadão.
  void chega_pedido(){
  for(;;){
  sucesso("S4) Servidor espera pedidos");
  signal(SIGUSR1,pausa);
  n=1;
  while(n==1){
  pause();
  }
  enfermeiro_indisponivel();
}
}

// S5.5.3.1
//Limpa a entrada correspondente ao servidor-filho.
void acordar(){
int pid_acabou=wait(NULL);
for(int i=0; i<NUM_VAGAS;i++){
int index_enf=vagas[i].index_enfermeiro;
if(vagas[i].PID_filho==pid_acabou){
vaga_atual=i;
break;
}
vagas[vaga_atual].index_enfermeiro=-1;
sucesso("S5.5.3.1) Vaga %d que era do servidor dedicado %d libertada",vaga_atual,pid_acabou);

// S5.5.3.2
//Atualiza o perfil do enfermeiro que deu a vacina como disponível.
enfermeiro[index_enf].disponibilidade=1;
sucesso("S5.5.3.2) Enfermeiro %d atualizado para disponível",index_enf);

// S5.5.3.3
// Incrementa o nº de vacinas dadas por esse enfermeiro
enfermeiro[index_enf].num_vac_dadas++;
sucesso("S5.5.3.3) Enfermeiro %d atualizado para %d vacinas dadas",index_enf,enfermeiro[index_enf].num_vac_dadas);

// S5.5.3.4
//Atualiza o ficheiro enfermeiros.dat o nº de vacinas dadas pelo enfermeiro.
FILE *file;
file=fopen(FILE_ENFERMEIROS, "r");
fseek(file,(index_enf-1)*sizeof(Enfermeiro), SEEK_SET);
fwrite(&enfermeiro[index_enf], sizeof(enfermeiro[index_enf]),1,file);
fclose(file);
sucesso("Ficheiro FILE_ENFERMEIROS %d atualizado para %d vacinas dadas",index_enf,enfermeiro[index_enf].num_vac_dadas);

// S5.5.3.5
//Volta ao que estava a fazer antes do processo Servidor-Filho terminar.
return;
sucesso("S5.5.3.5) Retorna");
}
}

// S6
//Caso outilizador utilize o atalho "CTRL+C" arma e trata o sinal SIGINT para que possa ser encerrado. Seguidamente envia um sinal SIGTERM a todos os processos Servidor-Filho existentes para os terminar, remove o ficheiro servidor.pid e termina o processo Servidor.
void servidor_termina(){
sucesso("S6) Servidor terminado");
kill(vagas[vaga_atual].PID_filho,SIGTERM);
remove(FILE_PID_SERVIDOR);
exit(0);
}

//MAIN
int main (){
signal(SIGINT,servidor_termina); // S6
registar_PID(); // S1
enfermeiros(); // S2
inicializar(); // S3
chega_pedido(); // S4
acordar(); // S5.5.3.1
cria_filho(); // S5.4
 
}