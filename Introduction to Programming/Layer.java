class Layer {

	//Parte2

	ColorImage layer;

	Layer(ColorImage img){
		this.layer = img;

	}

	int posicaoY=0;
	int posicaoX=0;
	double fator = 1;
	String nome = "";
	boolean isAtiva = true;

	Layer (ColorImage img, int x, int y){

		layer = img;
		posicaoX = x;
		posicaoY = y;
	}

	Layer (ColorImage img, int x, int y, String name, double factor){

		layer = img;
		posicaoX = x;
		posicaoY = y;
		nome = name;
		fator = factor;

	}

	//1. modifica o nome da imagem.

	void changeName (String Nome){
		nome = Nome;
	}

	//2. altera o fator e a posição.

	void changeEscalaePosição (double factor, int x, int y){ 
		fator = factor;
		posicaoX = x;
		posicaoY = y;
	}


	//3. definir a imagem como activa ou inactiva.

	boolean ativacaodaImagem (){
		if(isAtiva == true){

			return true;

		}else{

			return false;
		}
	}



	//4. devolver a imagem completa da camada.

	ColorImage devolverCamada (){

		ColorImage camada = new ColorImage ((int)(layer.getWidth()*fator+posicaoX),(int)(layer.getHeight()*fator+posicaoY));

		for(int x=0; x< camada.getWidth(); x++){
			for(int y=0; y< camada.getHeight(); y++){

				camada.setColor(x,y,Color.WHITE);
			}
		}

		if(ativacaodaImagem() == true){

			layer = ImageManipulation.escaladaporFator(layer,fator);

			camada.paste(posicaoX,posicaoY,layer);
		}

		return camada;
	}







}