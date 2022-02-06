package objects;

import pt.iul.ista.poo.utils.Point2D;

// TODO
// Pode ser necessario mudar a declaracao da classe e/ou construtor
//
public class PedraGrande extends Pedra {
	
	public static final int PONTOS=2;

	public PedraGrande(Point2D position) {
		super("bigstone", position, 1);
	}


	@Override
	public void givePointsTo(Bulldozer b) {
		if(b instanceof AutoBulldozer){
			((AutoBulldozer)b).pontosAutoBulldozer+=PONTOS;
		}else if(b instanceof Bulldozer){
			b.pontosBulldozer+=PONTOS;
		}	
		
	}
}
