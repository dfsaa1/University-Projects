package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Point2D;

// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!


public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private String username;
	private Plane plane;
	private Veiculo veiculo;
	private ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<ImageTile> tileList;	// Lista de imagens
	private Fireman fireman;			// Referencia para o bombeiro
	private List<Score> lista= new ArrayList<>();;
	private  int mudarMapa=1;
	public int contadorGeral=0;

	private static GameEngine INSTANCE;

	// Neste exemplo o setup inicial da janela que faz a interface com o utilizador e' feito no construtor 
	// Tambem poderia ser feito no main - estes passos tem sempre que ser feitos!
	private GameEngine() {

		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI

		tileList = new ArrayList<>();  
	}

	public static GameEngine getInstance () {
		if(INSTANCE == null)
			INSTANCE= new GameEngine();
		return INSTANCE;
	}

	// O metodo update() e' invocado sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada um referencia para o objeto observado (neste caso seria a GUI)

	@Override
	public void update(Observed source) {
		int key = gui.keyPressed();							// obtem o codigo da tecla pressionada


		if(key!= KeyEvent.VK_P){
			fireman.moveMovable(key);
		}

		if(key == KeyEvent.VK_P ){
			colocaAviao();
		}

		propagarFogos();
		incendiar();
		gui.update();
		gui.setStatusMessage("Jogador: " + username + " Nível: " + mudarMapa + " Pontos : " + contadorGeral); // redesenha as imagens na GUI, tendo em conta as novas posicoes
		terminarJogo();
	}

	// Criacao dos objetos e envio das imagens para GUI
	public void start() {	
		String LEVELS = "levels/level" + mudarMapa + ".txt";
		contadorGeral=0;
		try {
			if(mudarMapa==1){
				readFile(LEVELS);
				gui.setMessage("PARA INICIAR O JOGO INSIRA O USERNAME");
				System.out.println("Username: ");
				Scanner scanner = new Scanner(System.in);
				username = scanner.nextLine();
				criarFicheiro("Score" + mudarMapa + ".txt");	
			}
			else if(mudarMapa>1 && mudarMapa<7) {
				readFile(LEVELS);
				criarFicheiro("Score" + mudarMapa + ".txt");
			}
			else if(mudarMapa==7) {
				ImageMatrixGUI.getInstance().dispose();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		sendImagesToGUI(); // enviar as imagens para a GUI
		gui.update();

	}

	public void criarFicheiro(String s){
		if(!(new File(s)).exists()){
			try {
				PrintWriter pw = new PrintWriter(new File(s));
				pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

	public void updateFicheiro(String s) {
		leFicheiro(s);
		lista.add(new Score(username,contadorGeral));
		lista.sort(new ComparadorDeScore());
		int maxPontuacoes=5;
		try{
			PrintWriter pw=new PrintWriter(new File(s));
			for(Score score : lista){
				if(maxPontuacoes>0){
					pw.println(score);
					maxPontuacoes--;
				}
			}
			pw.close();
			lista.clear();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void leFicheiro(String s){//guarda todos os nomes e valores do ficheiro para depois os comparar e escrever
		try {
			Scanner sc = new Scanner(new File(s));
			while(sc.hasNextLine()){	
				String[] linha = sc.nextLine().split(" ");
				String nome = linha[0];
				int pontos =  Integer.parseInt(linha[1]);
				lista.add(new Score(nome,pontos));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void terminarJogo() {
		int cont=0;
		for(ImageTile i : tileList)
			if(existsFire(i.getPosition())) {
				cont++;
			}

		if(cont==0) {
			if(veiculo!=null)
				removeElement(veiculo);
			removeElement(fireman);
			gui.setMessage("JOGO TERMINADO\n " + "Pontos: " + contadorGeral);
			updateFicheiro("Score" + mudarMapa + ".txt");
			gui.clearImages();
			tileList.clear();
			mudarMapa++;
			start();
		}
	}

	public Fireman getFireman() {
		return fireman;
	}


	public void readFile(String name) throws FileNotFoundException {
		int linha = 0;
		Scanner sc = new Scanner (new File (name));

		while(sc.hasNextLine() && linha!=GRID_HEIGHT ) {
			String letra = sc.nextLine();
			for (int x=0; x!=GRID_WIDTH; x++) {
				GameElement gLetras=GameElement.criar(String.valueOf(letra.charAt(x)), new Point2D(x, linha));
				tileList.add(gLetras);
			}
			linha++;
		}
		while(sc.hasNextLine()) {
			String[] linha1 = sc.nextLine().split(" ");
			String nome = linha1[0];
			int x=Integer.parseInt(linha1[1]);
			int y=Integer.parseInt(linha1[2]);
			GameElement g= GameElement.criar(nome,new Point2D(x,y));
			tileList.add(g);
			if(nome.equals("Fireman")) {
				fireman=(Fireman)g;
			}
		}
		sc.close();
	}

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes  
	private void sendImagesToGUI() {
		gui.addImages(tileList);
	}

	public List<Fire> getFires(){
		ArrayList<Fire> aux = new ArrayList<>();
		for (ImageTile f : tileList)
			if (f instanceof Fire )
				aux.add((Fire)f);
		return aux;
	}

	public Fire getFire(Point2D pos){
		for(ImageTile i : tileList){
			if(i.getPosition().equals(pos) && i instanceof Fire){
				return (Fire)i;
			}
		}
		return null;
	}

	public Veiculo getVeiculo(Point2D pos) {
		for(ImageTile i: tileList)
			if(i instanceof Veiculo) {
				if(i.getPosition().equals(pos)){
					return (Veiculo)i;
				}
			}
		return null;
	}


	public BurnableElement getBurnable(Point2D pos){
		for(ImageTile i : tileList){
			if(i instanceof BurnableElement && i.getPosition().equals(pos)){ 
				return (BurnableElement) i;
			}
		}
		return null;
	}


	public GameElement elementPosition(Point2D pos) {
		ImageTile i = null;
		for(ImageTile i2 : tileList)
			if(i2.getPosition().equals(pos) && i2.getLayer()==1) {
				i=i2;
			}
		return (GameElement) i;
	}


	public GameElement firePosition(Point2D pos) {
		ImageTile i = null;
		for(ImageTile i2 : tileList)
			if(i2.getPosition().equals(pos) && i2.getLayer()==2) {
				i=i2;
			}
		return (GameElement) i;
	}

	public boolean existsFire(Point2D newPosition){//Verifica se existe fogo no ponto dado
		for(ImageTile i: tileList){
			if(i.getPosition().equals(newPosition)){
				if(i instanceof Fire){
					return true;
				}
			}
		}
		return false;
	}


	public void addElement(ImageTile g){//Adiciona o elemento a' lista e a' gui
		tileList.add(g);
		gui.addImage(g); 
	}

	public void removeElement(ImageTile g) {
		tileList.remove(g);
		gui.removeImage(g); 
	}

	public int guiKey() {
		return gui.keyPressed();
	}

	public void propagarFogos(){//propaga os fogos
		List<ImageTile> aux = new ArrayList<>();
		for(ImageTile i : tileList ) {
			if(i instanceof Fire)
				aux.add(i);
		}
		for(ImageTile i: aux) {
			((Fire)i).propagar();
		}
	}

	private void colocaAviao(){//coloca o aviao no mapa
		if(existeAviao()) {
			removeElement(plane);
		}
		plane = new Plane (new Point2D(xInicialAviao(),10));
		addElement(plane);
	}

	public boolean existeAviao(){ //verifica se existe aviao no mapa
		for(ImageTile i : tileList){
			if(i instanceof Plane){
				return true;
			}
		}
		return false;
	}

	private ArrayList<Fire> getColunaFogo(int x){
		ArrayList<Fire> coluna = new ArrayList<>();
		for(Fire f: getFires()){
			if(f.getPosition().getX()==x){
				coluna.add(f);
			}
		}
		return coluna;
	}

	private int xInicialAviao(){
		int xInicial=0;
		int maximo=0;	
		for(int x=0; x<GRID_WIDTH; x++){
			int contador=0;
			for(Fire f: getColunaFogo(x)){
				if(existsFire(f.getPosition())){
					contador++;
				}
			}
			if(maximo<contador){
				maximo=contador;
				xInicial=x;
			}
		}	
		return xInicial;
	}

	public Plane getPlane(){
		return plane;
	}

	public void incendiar(){
		List<ImageTile> aux = new ArrayList<>();
		for(ImageTile i : tileList ) {
			if(i instanceof BurnableElement)
				aux.add(i);
		}
		for(ImageTile i: aux) {
			((BurnableElement)i).incendiado();
		}
	}
}

