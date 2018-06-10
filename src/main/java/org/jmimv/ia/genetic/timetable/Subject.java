package org.jmimv.ia.genetic.timetable;

import org.jmimv.ia.genetic.Gene;

public class Subject implements Gene {
	
	SubjectEnum type;
	
	@Override
	public String toString() {
		return type.toString();
	}

	@Override
	public void mutate() {
		// TODO Auto-generated method stub
		
	}
	
	

}
