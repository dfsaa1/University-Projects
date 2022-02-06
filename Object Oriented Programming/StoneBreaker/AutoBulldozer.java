package objects;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

//Se necessario, mudar o construtor
//
public class AutoBulldozer extends Bulldozer {
	
	public static int pontosAutoBulldozer=0;

	public AutoBulldozer(Point2D position) {
		super(position);
		this.setName("autobulldozer_U");
	}
	
	@Override
	public void move(Direction d){
		super.move(Direction.random());
	}
	
	@Override
	public String changePos(Direction d) {
		if((d == Direction.UP)){
			return "autobulldozer_U";
		}
		if((d == Direction.DOWN)){
			return "autobulldozer_D";
		}
		if((d == Direction.LEFT)) {
			return "autobulldozer_L";
		}
		if((d == Direction.RIGHT)) {
			return "autobulldozer_R";
		}
		return "";
	}		
}
