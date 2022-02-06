package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Grass extends GameElement implements BurnableElement {

	public int contador=3;
	private boolean queimado=false;

	public Grass (Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		if(contador<=0 && queimado==true) {
			return "burntgrass";
		}
		return "grass";
	}

	@Override
	public int getLayer() {
		return 1;
	}


	@Override
	public final double probability() {
		return 0.15;
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
				Fire fireToRemove = GameEngine.getInstance().getFire(getPosition());
				Grass g = (Grass) GameEngine.getInstance().getBurnable(getPosition());
				GameEngine.getInstance().removeElement(fireToRemove);
				GameEngine.getInstance().removeElement(g);
				GameEngine.getInstance().addElement(g);
				GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral-2;
				queimado=true;
			}
		}
	}
	@Override
	public int contador() {
		return contador;
	}




}
