#!/bin/bash

###############################################################################
## ISCTE-IUL: Trabalho prático de Sistemas Operativos
##
## Aluno: Nº:98591       Nome:Diogo Filipe Sádio Azeitona
## Nome do Módulo: stats.sh
## Descrição/Explicação do Módulo: 
## Este script primeiramente coloca o segundo argumento dado como input na variável "localidade" para que seguidamente conte o número de cidadãos dessa mesma    
## localidade. Depois conta o número de linhas dos ficheiros "cidadaos.txt" e "enfermeiros.txt" e procede à utilização do "case" para escolher um caso dependendo
## do que for escrito como input pelo utilizador. Caso este primeiro input seja "cidadaos", coloca na tela o número de cidadãos registados na cidade dada como   
## input, caso seja "registados" coloca na tela o nome e o número dos cidadãos com mais de 60 anos, caso seja "enfermeiros", depois da criação do ciclo
## e das variáveis necessárias, coloca na tela o nome dos enfermeiros disponíveis e, por fim, caso seja outro argumento diferente dos esperados, 
## coloca na tela um erro.
###############################################################################

#Coloca o segundo argumento na variável localidade
localidade=$2

#Conta o número de cidadãos da mesma localidade
num_cidadaos_localidade=$(cat cidadaos.txt | cut -d ':' -f4 | grep -x "$localidade" | wc -l) 

#Conta as linhas do ficheiro enfermeiros.txt
linhas_enfermeiros=$(cat enfermeiros.txt | wc -l)

#Conta as linhas do ficheiro cidadaos.txt
linhas_cidadaos=$(cat cidadaos.txt | wc -l)

i=1

#Utilização do "case" para escolher um caso dependendo do que for escrito como input pelo utilizador
case $1 in
    
"cidadaos")
    
    #Coloca na tela o número de cidadãos registados na cidade dada como input
    echo "O número de cidadãos registados em "$2" é $num_cidadaos_localidade"  
    
    ;;
      
"registados")
  #Coloca na tela o nome e o número dos cidadãos com mais de 60 anos e ordena-os do mais velho para o mais novo
  awk -F':' '$3>60' cidadaos.txt | sort -k 3 -t":" -r -n | cut -d ':' -f2,1  ;;
       
"enfermeiros")
    
  #Percorre as linhas enquanto o i é menor ou igual ao número de linhas
  while [ $i -le $linhas_enfermeiros ]; do
    
    #Corta a disponibilidade de cada enfermeiro no ficheiro enfermeiros.txt
    disponibilidade=$(cat enfermeiros.txt | cut -d ':' -f5 | head -$i | tail -1)
    
  #Se a disponibilidade for =1 continua o script
  if [ $disponibilidade -eq 1 ]; then
    
    #Corta o nome de cada enfermeiro disponivel
    nome_enfermeiro_disponivel=$(cat enfermeiros.txt | cut -d ':' -f2 | head -$i | tail -1)
     
    #Coloca na tela o nome dos enfermeiros disponíveis
    echo "$nome_enfermeiro_disponivel"  
    
  fi
    
    i=$(($i+1))
     
  done 
    
  ;;
      
*) #Caso seja escrito pelo utilizador um input diferente do esperado:  
   
   #Coloca na tela um erro
   echo "Opção Inválida!!"   
   
   exit 1   
   
   ;;   
   
   esac 

