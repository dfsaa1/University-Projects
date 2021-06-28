#!/bin/bash

###############################################################################
## ISCTE-IUL: Trabalho prático de Sistemas Operativos
##
## Aluno: Nº:98591       Nome:Diogo Filipe Sádio Azeitona 
## Nome do Módulo: menu.sh
## Descrição/Explicação do Módulo:
## Este script começa por desativar a função de sair para que o ciclo criado seja executado iterativamente enquanto esta função estiver a "off". Seguidamente cria
## a estética do menu e lê a opção digitada pelo utilizador. Procede à utilização do "case" para escolher um caso dependendo do que for escrito como input pelo 
## utilizador, executanto o script "./lista_cidadaos.sh" se o utilizador digitar "1". Se o utilizador digitar "2" o script pede ao utilizador para fornecer as   
## informações necessárias para a realização do script "./adiciona_enfermeiros.sh". Caso o utilizador digite "3" o script pede ao utilizador para fornecer as    
## informações necessárias para a realização do script "./stats.sh". Se o "4" for escrito pelo utilizador, o script "./agendamento.sh" é executado. Caso o       
## utilizador digite "0", a função "quit" é ativa e é possível ao utilizador sair do menu. Por fim, se nenhum dos anteriores digitos for o escolhido pelo        
## utilizador, irá aparacer que a opção escolhida é inválida.
###############################################################################

#Desativa a função de sair 
quit=off

#Criação do ciclo que é executado iterativamente enquanto o "quit" estiver a "off"
while [ "$quit" = "off" ]; do

#Cria a estética do menu para quando ele aparecer ao executar o script 
echo
echo "-----Covid-IUL-----"
echo "-------MENU--------"
echo
echo "1. Listar cidadãos"
echo "2. Adicionar enfermeiro"
echo "3. Stats"
echo "4. Agendar vacinação"
echo "0. Sair"
echo
echo -n "Escolha uma opção:"

#Lê a opção escolhida pelo utilizador
read option
#O "case" permite ao script fazer coisas diferentes dependendo da opção escolhida
case $option in

1)

  clear
  #Executa o script
  ./lista_cidadaos.sh
  ;;
  
2)

  clear
  
  #Pede ao utilizador para inserir o nome
  echo "Insira o nome:"
    #Lê o nome introduzido
    read nome
  #Pede ao utilizador para inserir o número de cédula do enfermeiro
  echo "Insira o número de cédula:"
    #Lê o número de cédula introduzido
    read numero_cedula
  #Pede ao utilizador para inserir o centro de saúde
  echo "Insira o centro de saúde:"
    #Lê o centro de saúde introduzido
    read centro_saude
  #Pede ao utilizador para inserir a disponibilidade do enfermeiro  
  echo "Insira a disponibilidade:"
    #Lê a disponibilidade introduzida
    read disponibilidade
    
    #Executa o script
    ./adiciona_enfermeiros.sh "$nome" $numero_cedula "$centro_saude" $disponibilidade
    
    ;;
    
3)

  clear
  
    #Pede ao utilizador para inserir a informação que deseja receber
    echo -n "Insira a informação que deseja receber:"
    #Lê a informação que o utilizador deseja receber
    read informacao
  
  #Se a informação introduzida for "cidadaos":
  if [ "$informacao" = "cidadaos" ]; then
    #Pede ao utilizador para inserir a cidade sobre a qual deseja receber a informação
    echo -n "Insira a cidade:"
    #Lê a cidade
    read cidade
   
   #Executa o script
   ./stats.sh $informacao $cidade
   
   #Se a informação introduzida for "registados" ou "enfermeiros":
   else
   
   #Executa o script correspondente
   ./stats.sh $informacao
   
   fi
    
  ;;
  
4)

  clear
  
  #Executa o script
  ./agendamento.sh
  
  ;;
  
 0) 
   #Ativa o "quit" para poder sair do menu
   quit=on
   clear
   
   ;;
   
 *)
   #Caso o utilizador escreva algo diferente de {1 ou 2 ou 3 ou 4 ou 0} irá apresentar que a opção que escolheu é inválida
   echo
   echo "Opção Inválida!"
   
   
   
   esac
   done 