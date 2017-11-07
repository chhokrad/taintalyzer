package edu.vanderbilt.taintalyzer.utility;

import java.util.Comparator;

public class LevelObjPairComparator implements Comparator<LevelObjPair> {

	@Override
	public int compare(LevelObjPair o1, LevelObjPair o2) {
		// TODO Auto-generated method stub
		return o1.getLevel() - o2.getLevel();
	}
}
