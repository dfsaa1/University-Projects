#!/bin/bash

###############################################################################
## ISCTE-IUL: Trabalho prático de Sistemas Operativos
##
## Aluno: Nº:98591      Nome:Diogo Filipe Sádio Azeitona 
## Nome do Módulo: agendamento.sh
## Descrição/Explicação do Módulo: 
## Este script primeiramente verifica se o ficheiro "agenda.txt" existe e, se for o caso, elimina-o. Depois de contar as linhas dos ficheiros "enfermeiros.txt" e
## "cidadaos.txt" cria um ciclo que é executado até a variável i ser igual ao número de linhas do ficheiro "enfermeiros.txt", verificando depois a disponibilidade de
## cada enfermeiro. Se esta for =1, cria todas as variáveis necessárias relacionadas com o enfermeiro. Seguidamente cria outro ciclo, que neste caso é executado  
## até a variável j ser igual ao número de linhas do ficheiro "cidadaos.txt", criando depois todas as variáveis necessárias relacionadas com o cidadão. Se a  
## localidade do enfermeiro for igual à localidade do cidadão, cria o ficheiro agenda.txt e coloca no mesmo as variáveis todas que criámos pela ordem pedida.
###############################################################################

  #Apaga o ficheiro 'agenda.txt' caso este exista 
  if [ -f agenda.txt ]; then 
    rm agenda.txt 
  fi

    i=1
    
    #Conta as linhas do ficheiro enfermeiros.txt
    linhas_enfermeiros=$(cat enfermeiros.txt | wc -l)
    #Conta as linhas do ficheiro cidadaos.txt
    linhas_cidadaos=$(cat cidadaos.txt | wc -l)
    
    
  #Criação do ciclo que é executado até a variável i ser igual ao número de linhas
  while [ $i -le $linhas_enfermeiros ]; do
  
  #Corta a disponibilidade de cada enfermeiro
  disponivel=$(cat enfermeiros.txt | cut -d ':' -f5 | head -$i | tail -1)
  
  #Se o enfermeiro estiver disponivel (disponivel=1) continua o script
  if [ $disponivel -eq 1 ]; then
  
  #Corta a localidade do Centro de Saúde
  localidade_CS=$(cat enfermeiros.txt | cut -d 'S' -f2 | cut -d ':' -f1 | head -$i | tail -1 ) 
  #Corta o nome do enfermeiro
  nome_enfermeiro=$(cat enfermeiros.txt | cut -d ':' -f2 | head -$i | tail -1)
  #Corta o número de cédula do enfermeiro
  num_cedula_enfermeiro=$(cat enfermeiros.txt | cut -d ':' -f1 | head -$i | tail -1) 
  
    j=1
  
  #Criação do ciclo que é executado até a variável i ser igual ao número de linhas
  while [ $j -le $linhas_cidadaos ]; do 
    
    #Cria uma variável com a data do dia em que a execução deste script é feita 
    data=$(date "+%Y-%m-%d") 
    
    #Corta o nome do cidadão
    nome_cidadao=$(cat cidadaos.txt | cut -d ':' -f2 | head -$j | tail -1)
    #Corta o número do cidadão 
    num_cidadao=$(cat cidadaos.txt | cut -d ':' -f1 | head -$j | tail -1)
    #Corta a localidade do cidadão
    localidade_cidadaos=$(cat cidadaos.txt | cut -d ':' -f4 | head -$j | tail -1)

  #Se a localidade do enfermeiro for igual à localidade do cidadão continua o script
  if [ $localidade_CS = $localidade_cidadaos ]; then 
  
  #Coloca no ficheiro agenda.txt as variáveis todas que criámos pela ordem pedida 
  echo "$nome_enfermeiro:$num_cedula_enfermeiro:$nome_cidadao:$num_cidadao:$data" >> agenda.txt 
  
    
  fi
    j=$(($j+1))  
  done
  fi
    i=$(($i+1))
  done
  cat agenda.txt

