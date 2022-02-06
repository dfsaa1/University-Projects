package pt.iul.poo.firefight.starterpack;

public class Score {

	private String nome;
	private int pontos;

	public Score(String nome, int pontos) {
		this.nome = nome;
		this.pontos = pontos;
	}

	public String getNome() {
		return nome;
	}

	public int getPontos() {
		return pontos;
	}

	public String toString() {
		return nome + " " + pontos;
	}

}
