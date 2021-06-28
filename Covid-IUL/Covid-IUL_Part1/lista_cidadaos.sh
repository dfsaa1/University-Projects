#!/bin/bash

###############################################################################
## ISCTE-IUL: Trabalho prático de Sistemas Operativos
##
## Aluno: Nº:98591       Nome:Diogo Filipe Sádio Azeitona
## Nome do Módulo: lista_cidadaos.sh
## Descrição/Explicação do Módulo:
## Este script primeiramente verifica se o ficheiro "cidadaos.txt" existe e, se for o caso, elimina-o. Seguidamente existe a contagem das linhas do ficheiro 
## "listagem.txt" para a realização do ciclo até à variável i, criada anteriormente, ter um valor igual ao número de linhas. São criadas todas as variáveis        
## necessárias para a realização deste script colocando-as, por fim, no ficheiro "cidadaos.txt" pela ordem pedida.
###############################################################################

#Apaga o ficheiro 'cidadaos.txt' caso este exista  
  if [ -f cidadaos.txt ]; then
  rm cidadaos.txt
                                    
  fi
  
  i=1
  
  #Conta as linhas do ficheiro listagem.txt
  linhas=$(cat listagem.txt | wc -l)
   
  #Criação do ciclo que é executado até a variável i ser igual ao número de linhas   
  while [ $i -le $linhas ]; do
  
  #Criação das variáveis
  
  #Calcula o número de cidadãos
  numero_do_cidadao=$((i+10000))
  #Encontra o nome do cidadão
  nome=$(cat listagem.txt | cut -d ':' -f2 | cut -d '|' -f1 | cut -d ' ' -f1,2 | head -$i | tail -1)
  #Encontra o ano em que o cidadão nasceu 
  idade_do_cidadao=$(cat listagem.txt | cut -d '-' -f3 | cut -d '|' -f1 | head -$i | tail -1)
  #Calcula a idade do cidadão utilizando a idade_do_cidadao
  idade=$((2021-$idade_do_cidadao))
  #Encontra a localidade do cidadão
  localidade=$(cat listagem.txt | cut -d ':' -f4 | cut -d '|' -f1 | cut -d ' ' -f1 | head -$i | tail -1)
  #Encontra o número de telemóvel do cidadão
  num_telemovel=$(cat listagem.txt | cut -d ':' -f5 | cut -d '' -f1 | head -$i | tail -1)
  #Estado de vacinação do cidadão
  estado_vacinacao=$(echo 0)
  
  #Coloca no ficheiro cidadaos.txt as variáveis todas que criámos pela ordem pedida
  echo "$numero_do_cidadao:$nome:$idade:$localidade:$num_telemovel:$estado_vacinacao" >> cidadaos.txt
  
  i=$(($i+1))
  
  done
  clear
	echo "------------------Lista de cidadãos do sistema Covid-IUL------------------"
  echo
	cat cidadaos.txt

