package org.jmimv.ia.genetic;

public interface IChromosome extends Comparable<IChromosome>{
	
	
	IChromosome create (IChromosome progenitor);
	
	void addGene(Gene g);
	
	void mutate();
	
	int evaluate();
	
	int getQuality();
	
}
