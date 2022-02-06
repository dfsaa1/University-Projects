package objects;
import engine.StoneBreaker;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;


public abstract class GameElement implements ImageTile {

	private String name;
	private Point2D position;
	private int layer;
	
	public GameElement(String name, Point2D position, int layer) {
		this.name = name;
		this.position = position;
		this.layer = layer;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String nome){
		this.name=nome;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	protected void setPosition(Point2D newPosition) {
		this.position = newPosition; 
	}
	
	// Indica se o objeto e' ou nao transponivel - por defeito devolve true 
	public boolean isTransposable() {
		return true;
	}

	// Metodo fabrica
	public static GameElement create(char c, int x, int y) {
		switch (c) {
		case StoneBreaker.PEDRA_GRANDE: return new PedraGrande(new Point2D(x, y));
		case StoneBreaker.PEDRA_PEQUENA: return new PedraPequena(new Point2D(x, y));
		case StoneBreaker.BULLDOZER: return new Bulldozer(new Point2D(x, y));
		case StoneBreaker.ARVORE: return new Arvore(new Point2D(x, y));
		case StoneBreaker.AUTOBULLDOZER: return new AutoBulldozer(new Point2D(x, y));
		case StoneBreaker.CHAO:	return new Chao(new Point2D(x, y));
		default:
			throw new IllegalStateException();
		}
	}

	@Override
	public String toString() {
		return "GameElement: name=" + name + ", position=" + position + ", layer=" + layer;
	}
}
