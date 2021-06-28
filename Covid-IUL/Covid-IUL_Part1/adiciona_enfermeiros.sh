#!/bin/bash

###############################################################################
## ISCTE-IUL: Trabalho prático de Sistemas Operativos
##
## Aluno: Nº:98591       Nome:Diogo Filipe Sádio Azeitona 
## Nome do Módulo: adiciona_enfermeiros.sh
## Descrição/Explicação do Módulo:
## Este script para começar cria variáveis com cada um dos inputs dados pelo utilizador. Se esse número de inputs for diferente de 4, irá ser transmitido um erro 
## para que o utilizador execute o script com exatamente 4 inputs (como pedido). Se assim for, o script continua e procura o Centro de Saúde e o Número de Cédula 
## dados como inputs no ficheiro "enfermeiros.txt". Seguidamente, se o Número de Cédula e o Centro de Saúde procurados não existirem regista o enfermeiro desejado
## , se o Centro de Saúde procurado existir transmite um erro ou se o Centro de Saúde procurado não existir e o Número de Cédula procurado já existir transmite um 
## erro.
###############################################################################

#Cria variáveis com cada um dos inputs
nome=$1
numero_cedula=$2
centro_saude=$3
disponibilidade=$4

#Variável apenas com dígitos de 0 a 9 
num_0_9="^[0-9]+$"

#Se o número de cédula for criado com um dígito diferente de 0,1,2,3,4,5,6,7,8,9 transmite um erro
if  ! [[ $numero_cedula =~ $num_0_9 ]]; then
  echo "Erro: Insira um número válido em <número_cedula>" >&2; 
  exit 1
fi

#Variável apenas com os dígitos 0 e 1
num_0_1="^[0-1]+$"

#Se a disponibilidade for um número diferente de 0 e 1 transmite um erro
if ! [[ $disponibilidade =~ $num_0_1 ]]; then
  echo "Erro: Insira um número válido em <disponibilidade>" >&2; 
  exit 1
fi

#Se o número de inputs dados for diferente dos necessários (4) transmite um erro 
if [ $# -ne 4 ]; then
echo "Erro: Síntaxe: $0 <número cédula profissional>:<nome>:<centro saúde associado>:<nº de vacinações efetuadas>:<disponibilidade>"

#Se o número de inputs for igual a 4 executa o resto do script
else

#Procura o Centro de Saúde colocado como input no ficheiro enfermeiros.txt
centro_saude_txt=$(cat enfermeiros.txt | cut -d ':' -f3 | grep -x $centro_saude | wc -l)
#Procura o Número de Cédula colocada como input no ficheiro enfermeiros.txt
numero_cedula_txt=$(cat enfermeiros.txt | cut -d ':' -f1 | grep -x $numero_cedula | wc -l)

#Se o Número de Cédula e o Centro de Saúde procurados não existirem regista o enfermeiro
if [ $numero_cedula_txt -eq 0 ] && [ $centro_saude_txt -eq 0 ]; then

#Coloca no ficheiro enfermeiros.txt as variáveis todas que criámos pela ordem pedida
echo "$numero_cedula:"$nome":"$centro_saude":0:$disponibilidade" >> enfermeiros.txt
clear
echo "-------O ENFERMEIRO FOI REGISTADO COM SUCESSO-------" 
cat enfermeiros.txt
fi

#Se o Centro de Saúde procurado existir transmite um erro
if [ $centro_saude_txt -ne 0 ]; then

echo "Erro: O Centro de Saúde introduzido já tem um enfermeiro registado!"

fi

#Se o Centro de Saúde procurado não existir e o Número de Cédula procurado já existir transmite um erro
if [ $numero_cedula_txt -ne 0 ] && [ $centro_saude_txt -eq 0 ]; then 

echo "Erro: O Enfermeiro ja está registado em outro Centro de Saúde!"

fi
fi

