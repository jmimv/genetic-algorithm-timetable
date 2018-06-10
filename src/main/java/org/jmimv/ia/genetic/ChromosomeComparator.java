package org.jmimv.ia.genetic;

import java.util.Comparator;

public class ChromosomeComparator implements Comparator<IChromosome>{

	@Override
	public int compare(IChromosome o1, IChromosome o2) {
		return o1.compareTo(o2);
	}
	
	

}
