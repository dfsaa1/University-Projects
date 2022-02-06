package pt.iul.poo.firefight.starterpack;
import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Plane extends GameElement{

	public Plane(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "plane";
	}

	@Override
	public int getLayer() {
		return 6;
	}

	public void move() {
		Direction dir = Direction.UP;
		Point2D newPosition=getPosition().plus(dir.asVector());
		setPosition(newPosition);
		for(Fire f : GameEngine.getInstance().getFires()){
			if(f.getPosition().equals(newPosition)){
				removeFire(newPosition);
			}
		}
	}

	private void removeFire(Point2D pos) {
		List<ImageTile> aux = new ArrayList<>();       
		ImageTile i2 = null;
		ImageTile f = GameEngine.getInstance().firePosition(pos);
		if(f instanceof Fire ) {
			i2=f;
			aux.add(i2);
		}
		for(ImageTile i3 : aux) {
			GameEngine.getInstance().removeElement(i3);
			GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral+1;
		}
	}

}
