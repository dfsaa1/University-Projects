/******************************************************************************
 ** ISCTE-IUL: Trabalho prático de Sistemas Operativos
 **
 ** Aluno: Nº:98591       Nome:Diogo Filipe Sádio Azeitona 
 ** Nome do Módulo: cidadao.c
 ** Descrição/Explicação do Módulo: Este módulo simula, na prática, a chegada do cidadão ao centro de saúde da sua localidade para
 ** iniciar o processo de vacinação, seguindo as regras do plano de Vacinação.
 **
 ******************************************************************************/
  #include <stdio.h>
  #include <string.h>
  #include <signal.h>
  #include <stdlib.h>
  #include <unistd.h>
  #include <sys/wait.h>
  #include "common.h"
  
  //Variáveis globais
  const int estado_vacinacao=0; 
  Cidadao cid;
  int valor_pausa;
  
  
  //c9
  //Arma e trata o sinal SIGTERM para o caso de receber por parte do processo servidor que não é possível realizar a vacinação do cidadão. Se este sinal for recebido, transmite uma mensagem de não ser possível a vacinação do cidadão introduzido, removendo posteriormente o FILE_PEDIDO_VACINA (pedidovacina.txt) e termina o processo cidadão (exit(0)).
  void trata_sinalSIGTERM(int sinal){
   sucesso("C9) Não é possível vacinar o cidadão no pedido nº %d",cid.PID_cidadao);
   remove(FILE_PEDIDO_VACINA);
   exit(0);
}

  //c8
  //Arma e trata o sinal SIGUSR2 para o caso de o processo servidor enviar um sinal sobre o término da vacinação do cidadão. Se receber este sinal, transmite que a vacinação do cidadão está concluída e termina o processo cidadão (exit(0)).
  void trata_sinalSIGUSR2(int sinal){
  sucesso("C8) Vacinação do cidadão com o pedido nº %d concluída",cid.PID_cidadao);
  exit(0);
}

  //c1
  //Imprime no ecrã dados a serem introduzidos sobre o cidadão a vacinar e lê os mesmos para os colocar nas respetivas variáveis. No final transmite o output de sucesso com todas as informações sobre o cidadão.
  void cria_cidadao() {
    printf("Introduza o número de utente: \n");
    scanf("%d", &cid.num_utente);
    printf("Introduza o nome do cidadão: \n");
    scanf(" %99[^\n]", cid.nome);
    printf("Introduza a idade do cidadão: \n");
    scanf("%d", &cid.idade);
    printf("Introduza a localidade do cidadão: \n");
    scanf(" %99[^\n]", cid.localidade);
    printf("Introduza o número de telemóvel do cidadão: \n");
    scanf("%9s", cid.nr_telemovel);
    sucesso("C1) Dados Cidadão: %d; %s; %d; %s; %s; 0",cid.num_utente,cid.nome,cid.idade,cid.localidade,cid.nr_telemovel);
  //c2
  //Preenche o campo PID_cidadao com o PID deste processo cidadão específico através de um getpid() e transmite a mensagem de sucesso com o PID do processo pedido.
  cid.PID_cidadao=getpid();
  sucesso("C2) PID Cidadão: %d",cid.PID_cidadao);
  }
  
  void tirar_pausa(){
  valor_pausa=0;
}
  
  //c3
  //Através do access vamos verificar se o ficheiro FILE_PEDIDO_VACINA (pedidovacina.txt) ja existe. Caso o mesmo exista, transmite uma mensagem de erro de como não é possível iniciar o processo de vacinação e tenta iniciá-lo de 5 em 5 segundos (C10).
  void pedido_vacina_erro(){
  while(1){
  if( access( FILE_PEDIDO_VACINA, F_OK ) == 0 ){
  erro("C3) Não é possível iniciar o processo de vacinação neste momento");
  signal(SIGALRM,tirar_pausa);
  alarm(5);
  valor_pausa=1;
  while(valor_pausa){
  pause();
}
  
  }else{
  sucesso("C3) Ficheiro FILE_PEDIDO_VACINA pode ser criado");
  
  //c4
  //Caso este ficheiro FILE_PEDIDO_VACINA (pedidovacina.txt) não exista, então cria o mesmo com as informações do cidadão na forma pedida e transmite uma mensagem sucesso uma vez que foi criado e preenchido.
  FILE *file;
	file=fopen(FILE_PEDIDO_VACINA, "w");
  fprintf(file, "%d:", cid.num_utente);
	fprintf(file, "%s:", cid.nome);
  fprintf(file, "%d:", cid.idade);
  fprintf(file, "%s:", cid.localidade);
  fprintf(file, "%s:", cid.nr_telemovel);
  fprintf(file, "%d:", cid.estado_vacinacao);
	fprintf(file, "%d\n", cid.PID_cidadao);	
    fclose(file);
    sucesso("C4) Ficheiro FILE_PEDIDO_VACINA criado e preenchido");
    return;
    }
   }
}


  //c5
  //Arma e trata o sinal SIGINT para que se o utilizador interromper com o atalho pedido (CTRL+C) escreva no ecrã a mensagem de sucesso de cancelamento da vacinação e remova o ficheiro FILE_PEDIDO_VACINA criado.
  void trata_sinalSIGINT(){
  sucesso("C5) O cidadão cancelou a vacinação, o pedido nº %d foi cancelado",cid.PID_cidadao);
  remove(FILE_PEDIDO_VACINA);
  exit(0);
}

  //c6
  //Caso o ficheiro FILE_PID_SERVIDOR (servidor.pid) exista (Verificamos através do access que retorna 0 caso o ficheiro exista), envia o sinal SIGUSR1 ao processo servidor para que este lhe indique sobre se o cidadão pode ou não ser vacinado. 
  void le_pedido_vacinacao(){
  int pid_lido;
  if ( access(FILE_PID_SERVIDOR, F_OK ) == 0 ){
  FILE *file;
  file=fopen(FILE_PID_SERVIDOR, "r");
  fscanf(file, "%d", &pid_lido);
  fclose(file);
  kill(pid_lido, SIGUSR1);
  sucesso("C6) Sinal enviado ao Servidor: %d",pid_lido);
  }else{
  erro("C6) Não existe ficheiro FILE_PID_SERVIDOR!");
}
}
  
  //c7
  //Arma e trata o sinal SIGUSR1 para o caso de o processo servidor indicar que existe um enfermeiro disponível, fazendo assim com que a vacina seja administrada. Caso este sinal seja enviado por parte do processo servidor, transmite uma mensagem de vacinação em curso e apaga posteriormente o FILE_PEDIDO_VACINA (pedidovacina.txt).
  void trata_sinalSIGUSR1(int sinal){
  sucesso("C7) Vacinação do cidadão com o pedido nº %d em curso",cid.PID_cidadao);
  remove(FILE_PEDIDO_VACINA);
}
 
  //MAIN
  int main(){
  cria_cidadao(); //c1
  pedido_vacina_erro(); //C3
  signal(SIGINT,trata_sinalSIGINT); //c5
  

  le_pedido_vacinacao(); // C6
  signal(SIGUSR1,trata_sinalSIGUSR1); //C7
  signal(SIGUSR2,trata_sinalSIGUSR2); //c8
  signal(SIGTERM,trata_sinalSIGTERM); //c9
  
  while(1) { 
  pause();
}
}
    
  