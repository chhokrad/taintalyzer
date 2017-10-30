package classes;

import java.util.Comparator;

public class PairComparator implements Comparator<Pair>{

	@Override
	public int compare(Pair o1, Pair o2) {
		// TODO Auto-generated method stub
		return o1.getLevel() - o2.getLevel();
	}

}
