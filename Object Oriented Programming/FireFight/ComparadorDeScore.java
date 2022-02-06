package pt.iul.poo.firefight.starterpack;

import java.util.Comparator;

public class ComparadorDeScore implements Comparator<Score> {

	@Override
	public int compare(Score s1, Score s2) {
		return s2.getPontos()-s1.getPontos();
	}

}
