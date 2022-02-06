package pt.iul.poo.firefight.starterpack;

//Cria uma instancia de GameEngine e depois inicia o jogo
// Podera' vir a ficar diferente caso defina GameEngine como solitao
public class Main {
	public static void main(String[] args) {
		GameEngine game =  GameEngine.getInstance();
		game.start();
	}
}
