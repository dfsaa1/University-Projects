package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class FireTruck extends Veiculo {

	private String name="firetruck";

	public FireTruck(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

	@Override
	public int getLayer() {
		return 5;
	}

	public String changePos(int key) {

		if((key == KeyEvent.VK_DOWN)){
			return "firetruck";
		}
		if((key == KeyEvent.VK_UP)){
			return "firetruck";
		}
		if((key == KeyEvent.VK_LEFT)) {
			return "firetruck_left";
		}
		if((key == KeyEvent.VK_RIGHT)) {
			return "firetruck_right";
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
		if (canMoveTo(newPosition) && newPosition!=null && !GameEngine.getInstance().existsFire(newPosition)){
			setName(changePos(key));
			setPosition(newPosition);
		}else if(GameEngine.getInstance().existsFire(newPosition)){
			removeFire(newPosition);
		}
		if(GameEngine.getInstance().existeAviao()){
			GameEngine.getInstance().getPlane().move();
			GameEngine.getInstance().getPlane().move();
		}
	}

	public void removeFire(Point2D pos) {
		List<ImageTile> aux = new ArrayList<>();
		int key = GameEngine.getInstance().guiKey();         
		Water water = new Water(pos,key);
		ImageTile i2 = null;
		ImageTile f = GameEngine.getInstance().firePosition(pos);
		if(f instanceof Fire ) {
			i2=f;
			aux.add(i2);
		}
		for(ImageTile i3 : aux) {
			GameEngine.getInstance().removeElement(i3);
			if(GameEngine.getInstance().elementPosition(pos) instanceof Pine){
				GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral+2;
			}
			if(GameEngine.getInstance().elementPosition(pos) instanceof Eucaliptus){
				GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral+4;
			}
			if(GameEngine.getInstance().elementPosition(pos) instanceof Grass){
				GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral+6;
			}
			GameEngine.getInstance().addElement(water);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			GameEngine.getInstance().removeElement(water);
		}
	}
}
