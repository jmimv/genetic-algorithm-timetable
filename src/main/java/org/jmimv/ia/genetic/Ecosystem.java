package org.jmimv.ia.genetic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.jmimv.ia.genetic.timetable.Timetable;

public abstract class Ecosystem implements IEcosystem {
	
	protected List<IChromosome> population = new ArrayList<>();
	
	protected int DISCARD_MIN = 12;
	
	protected float elite = 1f;
	
	protected float discardOffset = 0.3f;
	
	protected int mutations = 6;
	
	protected int generations = 30;
	
	protected ChromosomeComparator comparator = new ChromosomeComparator();
	
	

	public Collection<IChromosome> getPopulation() {
		return population;
	}

	@Override
	public Map<IChromosome, IChromosome> getCouples() {
		Collections.shuffle(population);
		Map<IChromosome, IChromosome> couples = new HashMap<>();
		try {
			Iterator<IChromosome> it = population.iterator();
			while (it.hasNext()) {
				IChromosome item = it.next();
				IChromosome next = it.next();
				couples.put(item, next);
			}
		}
		catch(Exception e) {
			
		}
		return couples;
	}
	
//	private int ramdomSelectGene() {
//		return ThreadLocalRandom.current().nextInt(0, population.size() );
//	}
	
	private int ramdomSelectGeneEliteSafe() {
		int elit = calculaOffset (this.elite);
		return ThreadLocalRandom.current().nextInt(elit, population.size() );
	}

	@Override
	public IChromosome mutate() {
		int pos = this.ramdomSelectGeneEliteSafe();
		IChromosome cr =  population.get(pos);
		cr.mutate();
		return cr;
	}

	@Override
	public void addToPopulation(IChromosome c) {
		population.add(c);
	}
	
	@Override
    public List<IChromosome> getElite(){
		return population.subList(0, this.calculaOffset(this.elite));
	}
	
	protected int calculaOffset (float valor) {
		if (valor < 1f) {
			int size = population.size();
			return (int)(size*valor);
		}
		else {
			return (int)valor;				
		}
	}
	
    @Override
	public void discard(){
    	if (discardOffset > 0f && population.size() > DISCARD_MIN) {
    		int size = population.size();
    		int theWorst = this.calculaOffset(this.discardOffset);
			population = population.subList(0, size - theWorst);
		}
	}

	
	@Override
	public void procreate(){
		
		
		initPopulation();
		evaluarPoblacionInicial();
		System.out.println("poblacion inicial: "+population.size());
		IChromosome theBest = null;
		int i = 0;
		boolean continuee = i < generations;
		for (; continuee; i++) {
			Map<IChromosome, IChromosome> couples = getCouples();
			for (Map.Entry<IChromosome, IChromosome> couple : couples.entrySet()) {
				IChromosome son = couple.getKey().create(couple.getValue());
				this.addToPopulation(son);				
			}
			Collections.sort(population, comparator);
			
			this.discard();
			
			mutaciones();

			theBest = getElite().get(0);
			continuee = theBest.getQuality() != 0 && i < generations;
		}
		System.out.println(i);
		System.out.println("the Best:");
		System.out.println(((Timetable)theBest).print());
	}
	
	protected void mutaciones() {
		for (int i = 0 ; i< mutations; i++) {
			this.mutate();
		}
		if (mutations > 0) {
			Collections.sort(population, comparator);
		}
	}
	
	//protected abstract void poblacionInicial();

	protected void evaluarPoblacionInicial() {
		for (IChromosome cr : this.population) {
			cr.evaluate();
		}
	}

}

