package objects;

import pt.iul.ista.poo.utils.Point2D;

public abstract class Pedra extends GameElement implements Breakable {

	public Pedra(String name, Point2D position, int layer) {
		super(name, position, layer);
	}
	
	@Override
	public void brokenBy(Bulldozer b) {
		givePointsTo(b);
	}

}
