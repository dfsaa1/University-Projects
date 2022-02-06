package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {

	private Point2D point;

	public GameElement(Point2D position) {
		this.point=position;
	}

	public Point2D getPosition() {
		return point;
	}
	public void setPosition(Point2D position) {
		this.point=position;
	}

	public static GameElement criar (String tipo , Point2D position)  {

		switch(tipo) {
		case "p" : return new Pine(position);
		case "m" : return new Grass(position);
		case "_" : return new Land(position);
		case "e" : return new Eucaliptus(position);
		case "a" : return new Abies(position);
		case "b" : return new FuelBarrel(position);

		case "Fireman" : return new Fireman(position);
		case "FireTruck" : return new FireTruck(position);
		case "Firemanbot" : return new Firemanbot(position);

		case "Bulldozer" : return new Bulldozer(position);
		case "Fire" : return new Fire(position);

		default : return new Land(position);


		}
	}




}








