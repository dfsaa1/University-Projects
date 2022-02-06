package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Abies extends GameElement implements BurnableElement{

	public int contador = 20;
	private boolean queimado = false;

	public Abies(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		if(contador<=0 && queimado==true) {
			return "burntabies";
		}
		return "abies";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public double probability() {
		return 0.05;
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
				Abies e = (Abies) GameEngine.getInstance().getBurnable(getPosition());
				GameEngine.getInstance().removeElement(fireToRemove);
				GameEngine.getInstance().removeElement(e);
				GameEngine.getInstance().addElement(e);
				GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral-10;
				queimado=true;
			}
		}
	}

	@Override
	public int contador() {
		return contador;
	}

}
