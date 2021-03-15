class ImageManipulation {

	//Parte 1


	//1. copiar a parte não transparente de uma imagem para cima de outra imagem.

	static ColorImage copiarnaotransparente (ColorImage imgFundo, ColorImage imgColada, int x0, int y0){

		Color transparente = Color.WHITE;

		for (int x=0; x<imgFundo.getWidth(); x++){
			for (int y=0; y<imgFundo.getHeight(); y++){

				if (x>=x0 && x<x0 + imgColada.getWidth() && y>=y0 && y<y0 + imgColada.getHeight()){

					Color c = imgColada.getColor (x-x0,y-y0);

					if(c.getR() != transparente.getR() || c.getG() != transparente.getG() ||
							c.getB() != transparente.getB()){


						imgFundo.setColor (x,y,c);


					}
				}	
			}
		}

		return imgFundo;
	}

	static void testecopiarnaotransparente (){

		ColorImage img = new ColorImage("objc1.png");
		ColorImage img1 = new ColorImage("fr.jpg");
		//ColorImage img1 = new ColorImage("BRANCO.png");

		copiarnaotransparente (img,img1,0,0);

	}


	//2. criar uma imagem de fundo do poster com base numa imagem padrão replicada.

	static ColorImage ImagemReplicada (ColorImage img, int size1, int size2){ 

		int altura  =  img.getHeight();
		int largura =  img.getWidth();


		ColorImage nova = new ColorImage(size1,size2);
		//Fundo do poster de largura size1 e altura size2



		for (int x=0; x< nova.getWidth(); x+=largura){
			for (int y=0; y< nova.getHeight(); y+=altura){
				//Permite que a imagem seja replicada uma seguida da outra

				nova.paste(x,y,img);
				//Colamos a imagem img as vezes possíveis de acordo com o tamanho dado(size1,size2)


			}
		}

		return nova;
	}

	static ColorImage testeImagemReplicada (){

		ColorImage img = new ColorImage("objc1.png");
		ColorImage img1 = ImagemReplicada (img,2*191,2*284);

		return img1;
	}



	//3. criar, uma cópia escalada da imagem original a partir de um factor dado.

	static ColorImage escaladaporFator(ColorImage img, double fator){

		ColorImage nova = new ColorImage ((int)(img.getWidth()*fator), (int)(img.getHeight()*fator));
		//Cópia da imagem dada como argumento escalada

		if(fator<0){
			throw new IllegalArgumentException("Valor Inválido");
		}

		for( int x=0; x< nova.getWidth(); x++){
			for( int y=0; y< nova.getHeight(); y++){

				Color c = img.getColor((int)(x/fator),(int)(y/fator));

				nova.setColor (x,y,c);

			}
		}

		return nova;
	}

	static ColorImage testeescaladaporFator (){

		ColorImage img = new ColorImage("objc1.png");
		ColorImage img1 = escaladaporFator (img,1.5);

		return img1;
	}

	//4. criar uma imagem quadrada correspondente o uma seleção circular de uma imagem original.

	static ColorImage criarCirculo(ColorImage img, int xdoCentro, int ydoCentro, int raio) { 

		ColorImage nova = new ColorImage (raio*2,raio*2);

		if(xdoCentro < 0 || ydoCentro < 0 || raio < 0){

			throw new IllegalArgumentException("Valores têm de ser positivos");
		}

		if(xdoCentro!=ydoCentro){
			throw new IllegalArgumentException("Valores inválidos");
		}

		int xInicial = xdoCentro - raio;
		int yInicial = ydoCentro - raio;

		for(int x = 0; x < nova.getWidth(); x++) {
			for(int y = 0; y < nova.getHeight(); y++) {

				double distancia = Math.sqrt( Math.pow(x+raio - xdoCentro,2) + Math.pow(y+raio - ydoCentro, 2) );

				if(distancia > raio) {
					//Coloca a branco todos os pixeis a uma distância do centro superior ao raio

					nova.setColor(x, y,Color.WHITE);

				}else{

					Color color = img.getColor (x+xInicial,y+yInicial);

					nova.setColor(x,y,color);

				}
			}
		}

		return nova;
	}

	static ColorImage testecriarCirculo (){

		ColorImage img = new ColorImage("objc1.png");
		ColorImage img1 = criarCirculo (img,100,100,50);

		return img1;
	}



	//5. criar uma cópia em tons de cinzento de uma imagem dada.

	static ColorImage copiaCinzento (ColorImage img){ 

		ColorImage nova = new ColorImage (img.getWidth(),img.getHeight());
		//Cria uma cópia com o mesmo tamanho da original


		for( int x=0; x< img.getWidth(); x++){
			for( int y=0; y< img.getHeight(); y++){

				Color c = img.getColor(x,y);

				int r =  c.getR();
				int g =  c.getG();
				int b =  c.getB();

				double greytone =  ((0.3*r)+(0.59*g)+(0.11*b));
				//Cria o tom de cinzento 

				Color Grey = new Color ((int)greytone,(int)greytone,(int)greytone);
				//Cria a cor cinzento


				nova.setColor (x,y,Grey);

			}
		}

		return nova;
	}


	static ColorImage testecopiaCinzento (){

		ColorImage img = new ColorImage("objc1.png");
		ColorImage img1 = copiaCinzento (img);

		return img1;

	}





}