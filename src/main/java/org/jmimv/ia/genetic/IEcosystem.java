package org.jmimv.ia.genetic;

import java.util.Collection;
import java.util.Map;

public interface IEcosystem {
	
	void initPopulation();
	
	Collection<IChromosome> getPopulation();
	
	Map<IChromosome, IChromosome> getCouples();
	
	IChromosome mutate();
	
	void procreate();
	
	void addToPopulation(IChromosome c);
	
	Collection<IChromosome> getElite();	
	
	void discard();		
	
	
	
}
