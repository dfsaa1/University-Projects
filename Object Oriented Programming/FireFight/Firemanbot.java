package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;

import pt.iul.ista.poo.utils.Point2D;

public class Firemanbot extends GameElement implements Movable{

	String name = "firemanbot";

	public Firemanbot(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "firemanbot";
	}

	@Override
	public int getLayer() {
		return 3;
	}

	@Override
	public void move(int key) {

	}

	public void setName(String name) {
		this.name=name;
	}

	@Override
	public boolean canMoveTo(Point2D pos) {
		if (pos.getX() < 0) return false;
		if (pos.getY() < 0) return false;
		if (pos.getX() >= GameEngine.GRID_WIDTH) return false;
		if (pos.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}

	public String changePos(int key) {
		if((key == KeyEvent.VK_DOWN)){
			return "firemanbot";
		}
		if((key == KeyEvent.VK_UP)){
			return "firemanbot";
		}
		if((key == KeyEvent.VK_LEFT)) {
			return "firemabotn_left";
		}
		if((key == KeyEvent.VK_RIGHT)) {
			return "firemanbot_right";
		}
		return "";
	}
}
