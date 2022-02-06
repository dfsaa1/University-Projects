package pt.iul.poo.firefight.starterpack;


import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

public class Fireman extends GameElement implements Movable{

	private String nome="fireman";
	private Veiculo veiculo=null;
	private Fireman fireman;

	public Fireman(Point2D position) {
		super(position);
	}

	// Metodos de ImageTile
	@Override
	public String getName() {
		return nome;
	}

	@Override
	public int getLayer() {
		return 4;
	}

	public void setName(String nome){
		this.nome=nome;
	}

	public void moveMovable(int key){
		Point2D newPosition=nextPosition(key);
		fireman=GameEngine.getInstance().getFireman();

		if(key==KeyEvent.VK_ENTER){
			fireman.setPosition(veiculo.getPosition());
			GameEngine.getInstance().addElement(fireman);
			veiculo=null;

		}else if(veiculo==null){
			move(key);
			veiculo=GameEngine.getInstance().getVeiculo(newPosition);
		}else if(veiculo!=null) {
			GameEngine.getInstance().removeElement(fireman);
			((Movable)veiculo).move(key);

		}


	}

	public Point2D nextPosition(int key){
		Point2D newPosition= null;
		if(key == KeyEvent.VK_DOWN) {
			Direction dir = Direction.DOWN;
			newPosition = getPosition().plus(dir.asVector());
		} else if(key == KeyEvent.VK_UP) {
			Direction dir = Direction.UP;
			newPosition = getPosition().plus(dir.asVector());
		}else if(key == KeyEvent.VK_LEFT) {
			Direction dir = Direction.LEFT;
			newPosition = getPosition().plus(dir.asVector());	
		}else if( key == KeyEvent.VK_RIGHT) {
			Direction dir = Direction.RIGHT;
			newPosition = getPosition().plus(dir.asVector());	
		}
		return newPosition;
	}


	// Move numa direcao escolhida
	@Override
	public void move(int key) {
		Point2D newPosition=nextPosition(key);
		if (canMoveTo(newPosition) && newPosition!=null && !GameEngine.getInstance().existsFire(newPosition)) {
			setName(changePos(key));
			setPosition(newPosition);

			if(GameEngine.getInstance().existeAviao()){
				GameEngine.getInstance().getPlane().move();
				GameEngine.getInstance().getPlane().move();
			}
		}
		else if(canMoveTo(newPosition) && newPosition!=null && GameEngine.getInstance().existsFire(newPosition)){
			removeFire(newPosition);
		}
	}

	// Verifica se a posicao p esta' dentro da grelha de jogo
	@Override
	public boolean canMoveTo(Point2D p) {

		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}


	//o bombeiro apaga o fogo 
	public void removeFire(Point2D pos) {
		List<ImageTile> aux = new ArrayList<>();
		int key = GameEngine.getInstance().guiKey();         
		Water water = new Water(pos , key);
		ImageTile i2 = null;
		ImageTile f = GameEngine.getInstance().firePosition(pos);
		if(f instanceof Fire ) {
			i2=f;
			aux.add(i2);
		}
		for(ImageTile i3 : aux) {
			GameEngine.getInstance().removeElement(i3);
			if(GameEngine.getInstance().elementPosition(pos) instanceof Abies){
				GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral+10;
			}
			if(GameEngine.getInstance().elementPosition(pos) instanceof Pine){
				GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral+20;
			}
			if(GameEngine.getInstance().elementPosition(pos) instanceof Eucaliptus){
				GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral+40;
			}
			if(GameEngine.getInstance().elementPosition(pos) instanceof Grass){
				GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral+60;
			}
			if(GameEngine.getInstance().elementPosition(pos) instanceof FuelBarrel){
				GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral+100;
			}
			GameEngine.getInstance().addElement(water);
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GameEngine.getInstance().removeElement(water);
	}


	public String changePos(int key) {
		if((key == KeyEvent.VK_DOWN)){
			return "fireman";
		}
		if((key == KeyEvent.VK_UP)){
			return "fireman";
		}
		if((key == KeyEvent.VK_LEFT)) {
			return "fireman_left";
		}
		if((key == KeyEvent.VK_RIGHT)) {
			return "fireman_right";
		}
		return "";
	}
}
