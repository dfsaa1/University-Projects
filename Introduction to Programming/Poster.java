class Poster {

	
	Poster (int largura, int altura){
		this.largura = largura;
		this.altura = altura;

	}

	boolean cheio (){
		for(int i=vectorLayer.length-1; i>=0;i--){

			if(vectorLayer[i] == null){
				cheio=false;
				return cheio;
			}
		}

		cheio=true;

		return cheio;
	}

	
	private int altura;
	private int largura;
	Layer [] vectorLayer = new Layer [10];
	private boolean cheio;

	

	//1. criar a layer[0] dada a imagem com o padrão de preenchimento.

	void CreateorSubstitute (Layer layer){

		vectorLayer [0] = layer;
	}

	//2. adicionar (no fim da colecção) uma layer nova.

	void Add (Layer layer){

		if(cheio())
			throw new IllegalStateException("Poster está cheio");


		for(int x=vectorLayer.length-1; x>=0; x--){

			if(vectorLayer[x] != null || x==0 ){

				vectorLayer[x+1] = layer;

				return;
			}
		}
	}

	//3. remover uma layer da colecção numa posição dada, deslocando as restantes.


	void Remove (int posicao){


		if (vectorLayer[posicao] == null){
			throw new NullPointerException("Posição Inválida");

		}else{
			
			vectorLayer[posicao] = null;

			for (int x=posicao; x<vectorLayer.length-1;x++){

				vectorLayer[x]=vectorLayer[x+1];


			}

		}
	}	

	//4. inserir uma layer na coleção numa dada posição, deslocando as restantes.

	void Insert (Layer layer, int posicao){

		if(cheio())
			throw new IllegalStateException("Poster está cheio");

		for (int x=vectorLayer.length-1; x>posicao;x--){

			vectorLayer[x]=vectorLayer[x-1];
		}

		vectorLayer[posicao] = layer;

	}

	//5. trocar as posições de duas layers na colecção.

	void Swap (int posicaoA, int posicaoB){

		if (vectorLayer[posicaoA] == null || vectorLayer[posicaoB] == null){
			throw new NullPointerException("Posição Inválida");

		}

		if(posicaoA<0 || posicaoB<0 || posicaoA>vectorLayer.length || posicaoB>vectorLayer.length){
			throw new IllegalArgumentException("Valor Inválido");
		}

		Layer aux = vectorLayer [posicaoA];

		vectorLayer[posicaoA]=vectorLayer[posicaoB];

		vectorLayer[posicaoB]=aux;


	}

	//6. obter a imagem final do poster, com a sobreposição de todas as layers activas obedecendo à transparência.


	ColorImage finalPoster (){

		ColorImage img = new ColorImage (largura,altura) ; 

		for(int x=0; x<vectorLayer.length;x++){

			if(vectorLayer[x] != null && vectorLayer[x].ativacaodaImagem()){

				ColorImage image = vectorLayer[x].devolverCamada();

				img=ImageManipulation.copiarnaotransparente(img,image,0,0);

			}
		}

		return img;
	}


}