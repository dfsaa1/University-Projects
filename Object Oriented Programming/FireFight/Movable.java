package pt.iul.poo.firefight.starterpack;
import pt.iul.ista.poo.utils.Point2D;

public interface Movable {

	void move(int key);
	boolean canMoveTo(Point2D pos);

}

