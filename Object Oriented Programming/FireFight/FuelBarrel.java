package pt.iul.poo.firefight.starterpack;

import java.util.List;

import pt.iul.ista.poo.utils.Point2D;

public class FuelBarrel extends GameElement implements BurnableElement{

	public int contador=3;
	private boolean queimado=false;


	public FuelBarrel(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		if(contador<=0 && queimado==true) {
			return "burntfuelbarrel";
		}
		return "fuelbarrel";
	}

	@Override
	public double probability() {
		return 0.90;
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void pegarFogo() {
		double r = Math.random();
		if(r <=  probability() && !GameEngine.getInstance().existsFire(getPosition()) && queimado==false) {
			GameEngine.getInstance().addElement(new Fire(getPosition()));
		}
	}


	@Override
	public void incendiado() {
		if(GameEngine.getInstance().existsFire(getPosition())){
			contador--;
			if(contador()==0 && queimado==false){
				List<Point2D> pontosProximos= getPosition().getWideNeighbourhoodPoints();
				for(Point2D i : pontosProximos) {
					if(!GameEngine.getInstance().existsFire(i))
						GameEngine.getInstance().addElement(new Fire(i));
				}
				Fire fireToRemove = GameEngine.getInstance().getFire(getPosition());
				FuelBarrel p = (FuelBarrel) GameEngine.getInstance().getBurnable(getPosition());
				GameEngine.getInstance().removeElement(p);
				GameEngine.getInstance().removeElement(fireToRemove);
				GameEngine.getInstance().addElement(p);
				GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral-1;
				queimado=true;
			}
		}
	}


	@Override
	public int contador() {
		return contador;
	}

}
