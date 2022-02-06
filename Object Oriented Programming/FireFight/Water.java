package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;

import pt.iul.ista.poo.utils.Point2D;

public class Water extends GameElement{

	private int key;

	public Water(Point2D position, int key ) {
		super(position);
		this.key=key;
	}

	@Override
	public String getName() {
		if( key == KeyEvent.VK_LEFT) {
			return "water_left";
		}
		if(key == KeyEvent.VK_RIGHT) {
			return "water_right";
		}
		if(key == KeyEvent.VK_UP) {
			return "water_up";
		}
		if(key == KeyEvent.VK_DOWN) {
			return "water_down";
		}
		return null;
	}

	@Override
	public int getLayer() {
		return 3;
	}

}
