package org.jmimv.ia.genetic.timetable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jmimv.ia.genetic.Chromosome;
import org.jmimv.ia.genetic.Gene;
import org.jmimv.ia.genetic.IChromosome;

public class Timetable extends Chromosome{
	
	private int subjectsPerDay = 6;


	@Override
	public int evaluate() {
		quality = -countSubjectsInADay();
		
		return quality;
	}
	
	
	public IChromosome create() {		
		Timetable h = (Timetable) createSimple();
		List<Gene> list = this.createAndShuffleGenes();
		if (list != null) {
			h.genes = list; 
		}
		return h;
	}
	
	@Override
	public IChromosome createSimple() {
		return new Timetable();
	}
	
	private int countSubjectInADay(SubjectEnum tipo) throws Exception {
		int previousDay = -1;
		int i = 0;
		int count = 0;
		for (Gene gen : genes) {
			Subject subject = (Subject)gen;
			if (subject.type == tipo) {
				int dayOfweek = i/this.subjectsPerDay;
				if (previousDay < 0) {
					previousDay = dayOfweek;
				}
				else if (dayOfweek != previousDay) {
					previousDay = dayOfweek;
				}
				else {
					count++;
				}
			}
			i++;
		}
		return count;
	}
	private int countSubjectsInADay() {
		List subjects = genes;		 
		Set<SubjectEnum> tiposAsignaturas = ((List<Subject>)subjects).stream()
				.map(f -> f.type)
		        .collect(Collectors.toSet());
		int count = 0;
		try {
			for (SubjectEnum subjectType : tiposAsignaturas) {
				count += countSubjectInADay(subjectType);
			}
		}catch(Exception e) {
		}
		return count;
	}
	
	public String toString() {
		return quality + " "+genes.toString();
	}
	
	public String print() {
		String str = quality +"\n";
		int i = 0;
		for (Gene gen : genes) {
			if (i%this.subjectsPerDay == 0 ) {
				str += "\n";
			}
			str += gen + ",\t";
			i++;
		}
		return str;
	}


	
	
}
