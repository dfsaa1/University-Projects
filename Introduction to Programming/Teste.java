class Teste {

	static Layer testeParte2 (){

		ColorImage boneco = new ColorImage ("objc1.png");

		Layer layer= new Layer (boneco,40,40);

		layer.changeName("Anastácio");
		layer.changeEscalaePosição(1.4,60,100);
		layer.ativacaodaImagem();
		layer.devolverCamada();

		return layer;
	}
	
	static void devolvercamadaTeste(){
		ColorImage img = new ColorImage ("objc1.png");
		ColorImage img2 = ImageManipulation.criarCirculo(img,100,100,50);
		
		Layer layer = new Layer(img2,100,100,"imagem",1.5);
		
		layer.devolverCamada();
	}
		
		

	static void CreateorSubstituteTeste(){
		Poster p = new Poster(800, 500);
		ColorImage imagem = new ColorImage ("objc1.png");
		Layer layer= new Layer (imagem,40,40);
		p.CreateorSubstitute(layer);
	}

	static void AddTeste(){
		Poster p = new Poster(800, 500);
		Layer l = new Layer(new ColorImage("objc1.png"), 100, 50);
		Layer layer = new Layer (new ColorImage("fr.jpg"),40,70);
		p.CreateorSubstitute(layer);
		p.Add(l);
	}

	static void RemoveTeste(){
		Poster p = new Poster(800, 500);
		Layer layer = new Layer(new ColorImage("fr.jpg"), 200, 100);
		Layer l1 = new Layer(new ColorImage("objc1.png"), 300, 150);
		Layer l2 = new Layer(new ColorImage("BRANCO.png"), 400, 200);
		p.Insert(l1,0);
		p.Insert(l2,1);
		p.Insert(layer,2);
		p.Remove(1);
	}

	static void InsertTeste(){
		Poster p = new Poster(800, 500);
		Layer layer = new Layer(new ColorImage("fr.jpg"), 200, 100);
		Layer l1 = new Layer(new ColorImage("objc1.png"), 300, 150);
		Layer l2 = new Layer(new ColorImage("BRANCO.png"), 400, 200);
		p.Insert(layer,0);
		p.Insert(l1,1);
		p.Insert(l2,2);
	}

	static void SwapTeste(){
		Poster p = new Poster(800, 500);
		Layer layer = new Layer(new ColorImage("fr.jpg"), 200, 100);
		Layer l1 = new Layer(new ColorImage("objc1.png"), 300, 150);
		Layer l2 = new Layer(new ColorImage("BRANCO.png"), 400, 200);
		p.Insert(layer,0);
		p.Insert(l1,1);
		p.Insert(l2,2);
		p.Swap(0,2);
	}


	static ColorImage testefinalParte3 (){


		ColorImage img1 = new ColorImage ("fr.jpg");
		ColorImage img2 = new ColorImage ("objc1.png");
		ColorImage img3 = ImageManipulation.criarCirculo(img2,100,100,50);
		Layer l1= new Layer (img1,40,60,"Éder",1.2);
		Layer l2= new Layer (img3,100,100,"Boneco",1.5);
		Poster p= new Poster (500,500);
		p.Add(l1);
		p.Add(l2);
		ColorImage Final= p.finalPoster();

		return Final;

	}


}