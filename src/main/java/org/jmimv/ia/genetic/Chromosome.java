package org.jmimv.ia.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Chromosome implements IChromosome {
	
	protected int quality = 0;
	
	protected List<Gene> genes = new ArrayList<>(); 
	
	public abstract IChromosome create();
	public abstract IChromosome createSimple();
	
	protected boolean isElite = false;
	
	
	
	@Override
	public int compareTo(IChromosome o) {
		return - (quality - ((Chromosome)o).quality);
	}
	
	protected List<Gene> createAndShuffleGenes() {
		if (!genes.isEmpty()) {
			List<Gene> copyOfGenes = new ArrayList<>();
			copyOfGenes.addAll(genes);
			Collections.shuffle(copyOfGenes);
			return copyOfGenes;
		}
		return null;
	}
	
	private int ramdomSelectGene() {
		return ThreadLocalRandom.current().nextInt(0, genes.size() );
	}
	
	public List<Gene> getGenes(){
		return genes;
	}

	@Override
	public void mutate() {
		int uno = ramdomSelectGene();
		int otro = ramdomSelectGene();
		if (uno == otro) {
			otro = ramdomSelectGene();
		}
		Gene guno = this.genes.get(uno);
		Gene gotro = this.genes.get(otro);
		this.genes.set(uno, gotro);
		this.genes.set(otro, guno);
		
		this.evaluate();
	}
	
	@Override
	public void addGene(Gene g) {
		this.genes.add(g);
	}
	
	@Override
	public int getQuality() {
		return quality;
	}
	

	@Override
	public IChromosome create(IChromosome progenitor) {
		IChromosome son = createSimple();
		for (int i = 0; i<genes.size(); i++) {
			Gene guno = Math.random() < 0.5d ? this.genes.get(i) : ((Chromosome)progenitor).genes.get(i);
			((Chromosome)son).addGene(guno);
		}
		son.evaluate();
		return son;
	}
	

//	@Override
//	public boolean isElite() {
//		return esElite;
//	}
	
}
