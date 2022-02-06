package objects;

import java.util.List;

import engine.StoneBreaker;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

//TODO
//Pode ser necessario mudar o construtor
//
public class Bulldozer extends GameElement implements Movable{

	public static final int BULLDOZER_LAYER = 2;
	public static int pontosBulldozer=0;

	public Bulldozer(Point2D position) {
		super("bulldozer_U", position, BULLDOZER_LAYER);
	}

	@Override
	public void move(Direction d) {	
		Point2D newPosition= null;
		if(d != null) {
			newPosition = getPosition().plus(d.asVector());
		}
		if (StoneBreaker.inBounds(newPosition) && newPosition!=null && StoneBreaker.getInstance().isPassable(newPosition))
		{
			super.setName(changePos(d));
			setPosition(newPosition);
			eliminarPedra(newPosition);
		}
	}

	public String changePos(Direction d) {
		if((d == Direction.UP)){
			return "bulldozer_U";
		}
		if((d == Direction.DOWN)){
			return "bulldozer_D";
		}
		if((d == Direction.LEFT)) {
			return "bulldozer_L";
		}
		if((d == Direction.RIGHT)) {
			return "bulldozer_R";
		}
		return "";
	}

	public void eliminarPedra(Point2D pos) {
		List<Breakable> lista = StoneBreaker.getInstance().breakablesAt(pos);
		for(Breakable b : lista){
			if(b!=null  && b instanceof PedraPequena) {
				StoneBreaker.getInstance().removeObject((GameElement)b);
				b.brokenBy(this);
				Chao chao = new Chao(getPosition());
				StoneBreaker.getInstance().addObject(chao);
			}
			if(b!=null  && b instanceof PedraGrande) {
				StoneBreaker.getInstance().removeObject((GameElement)b);
				b.brokenBy(this);
				PedraPequena pedrapequena = new PedraPequena(getPosition());
				StoneBreaker.getInstance().addObject(pedrapequena);
			}

		}	
		
	}
	
	@Override
	public boolean isTransposable() {
		return false;
	}
}
