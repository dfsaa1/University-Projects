package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Bulldozer extends Veiculo{

	private String nome="bulldozer";

	public Bulldozer( Point2D position){
		super(position);
	}

	@Override
	public String getName() {
		return nome;
	}

	public void setName(String nome){
		this.nome=nome;
	}

	@Override
	public int getLayer() {
		return 5;
	}

	public void eliminarTerreno(Point2D pos) {
		ImageTile b = GameEngine.getInstance().elementPosition(pos);
		if(b!=null  && b instanceof BurnableElement) {
			GameEngine.getInstance().removeElement(b);
			Land land = new Land(getPosition());
			GameEngine.getInstance().addElement(land);
		}
	}

	public String changePos(int key) {
		if((key == KeyEvent.VK_DOWN)){
			return "bulldozer_down";
		}
		if((key == KeyEvent.VK_UP)){
			return "bulldozer_up";
		}
		if((key == KeyEvent.VK_LEFT)) {
			return "bulldozer_left";
		}
		if((key == KeyEvent.VK_RIGHT)) {
			return "bulldozer_right";
		}
		return "";
	}

	@Override
	public void move(int key) {
		Point2D newPosition= null;
		if(key == KeyEvent.VK_DOWN) {
			Direction dir = Direction.DOWN;
			newPosition = getPosition().plus(dir.asVector());
		}
		if(key == KeyEvent.VK_UP) {
			Direction dir = Direction.UP;
			newPosition = getPosition().plus(dir.asVector());
		}
		if(key == KeyEvent.VK_LEFT) {
			Direction dir = Direction.LEFT;
			newPosition = getPosition().plus(dir.asVector());	
		}
		if( key == KeyEvent.VK_RIGHT) {
			Direction dir = Direction.RIGHT;
			newPosition = getPosition().plus(dir.asVector());	
		}
		if (canMoveTo(newPosition) && newPosition!=null && !GameEngine.getInstance().existsFire(newPosition))
		{
			setName(changePos(key));
			setPosition(newPosition);
			eliminarTerreno(newPosition);
		}
		if(GameEngine.getInstance().existeAviao()){
			GameEngine.getInstance().getPlane().move();
			GameEngine.getInstance().getPlane().move();
		}
	}




}















