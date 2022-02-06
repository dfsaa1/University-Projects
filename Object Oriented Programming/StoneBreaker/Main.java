package engine;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

public class Main {

/* 

O objetivo deste jogo e' limpar a terra, destruindo todas as pedras.

- Em cada jogada, todos os bulldozers se tentam mover.
  
- Existem varios bulldozers, mas so' um deles e' controlado pelo jogador;

- As arvores nao se podem tirar, e o bulldozer/autobulldozers nao podem ir para cima delas;

- As pedras pequenas sao destruidas ao passar por cima (e o bulldozer continua);

- As pedras grandes nao sao atravessadas, mas podem-se partir e nesse caso 
  transformam-se em pedras pequenas (e na jogada seguinte ja' podem ser destruidas);
  
- Os autobulldozers tem o mesmo comportamento do bulldozer controlado
  pelo jogador, mas movem-se aleatoriamente;  
  
- O bulldozer e os autobulldozers nao sao atravessaveis (i.e., nao podem ir para cima uns dos outros)

*/

	public static void main(String[] args) {
		try {
			StoneBreaker s = StoneBreaker.getInstance();
			ImageMatrixGUI.getInstance().setSize(StoneBreaker.WIDTH, StoneBreaker.HEIGHT);
			ImageMatrixGUI.getInstance().registerObserver(s);
			ImageMatrixGUI.getInstance().go();
		} catch (IllegalStateException e) {
			System.err.println("Erro na leitura do ficheiro");
		}

	}
}
