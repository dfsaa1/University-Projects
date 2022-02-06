package pt.iul.poo.firefight.starterpack;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;

import pt.iul.ista.poo.utils.Point2D;



//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Fire extends GameElement{

	public Fire(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "fire";
	}

	@Override
	public int getLayer() {
		return 2;
	}
	public boolean Arder() {
		return true;
	}

	public void propagar() {
		List <Point2D> pontosProximos = getPosition().getNeighbourhoodPoints();
		for(Point2D j : pontosProximos) {
			ImageTile ge = GameEngine.getInstance().elementPosition(j);
			if(ge instanceof BurnableElement && ge != null) {
				((BurnableElement)ge).pegarFogo();
			}
		}
	}
}







