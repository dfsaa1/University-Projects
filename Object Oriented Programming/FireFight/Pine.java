package pt.iul.poo.firefight.starterpack;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Pine extends GameElement implements BurnableElement {

	public String burntPine= "burntpine";
	public int contador=10;
	private boolean queimado=false;

	public Pine(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		if(contador<=0 && queimado==true) {
			return "burntpine";
		}
		return "pine";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public final double probability() {
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
				Pine p = (Pine) GameEngine.getInstance().getBurnable(getPosition());
				GameEngine.getInstance().removeElement(fireToRemove);
				GameEngine.getInstance().removeElement(p);
				GameEngine.getInstance().addElement(p);
				GameEngine.getInstance().contadorGeral = GameEngine.getInstance().contadorGeral-5;
				queimado=true;
			}
		}
	}

	public int contador() {
		return contador;
	}

}

