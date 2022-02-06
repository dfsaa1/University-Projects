package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public abstract class Veiculo extends GameElement implements Movable{

	private Point2D pos;

	public Veiculo(Point2D posVeiculo){
		super(posVeiculo);
	}

	public void setPosicao(Point2D pos){
		this.pos=pos;
	}

	public Point2D getPosicao(){
		return pos;
	}

	public abstract void move(int key);	

	public  boolean canMoveTo(Point2D pos) {
		if (pos.getX() < 0) return false;
		if (pos.getY() < 0) return false;
		if (pos.getX() >= GameEngine.GRID_WIDTH) return false;
		if (pos.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}



}



