package org.jmimv.ia.genetic.timetable;

import java.util.HashMap;
import java.util.Map;

import org.jmimv.ia.genetic.Ecosystem;

public class Course extends Ecosystem{
	
	private Map<SubjectEnum, Integer> course = new HashMap<>();
	
	public Course() {
		course.put(SubjectEnum.MATH, 4);
		course.put(SubjectEnum.LITERATURE, 4);
		course.put(SubjectEnum.FOREIGN_LANG1, 4);
		
		course.put(SubjectEnum.FOREIGN_LANG2, 3);
		course.put(SubjectEnum.HISTORY, 3);
		course.put(SubjectEnum.BIOLOGY, 3);
		course.put(SubjectEnum.ART, 3);
		
		course.put(SubjectEnum.ETHICS, 2);
		course.put(SubjectEnum.SPORT, 2);
		course.put(SubjectEnum.MUSIC, 2);
	}

	@Override
	public void initPopulation() {
		Timetable timeTable = (Timetable) new Timetable().create();
		for (Map.Entry<SubjectEnum, Integer> subjectHoursPerWeek : course.entrySet()) {
			SubjectEnum subjectType = subjectHoursPerWeek.getKey();
			int hours = subjectHoursPerWeek.getValue();
			for (int i = 0; i < hours ; i++) {
				Subject subject = new Subject();
				subject.type = subjectType;
				timeTable.addGene(subject);
			}
		}
		//Collections.shuffle(timeTable.getGenes());
		this.addToPopulation(timeTable);
		Timetable otherTimeTable = (Timetable)timeTable.create();
		this.addToPopulation(otherTimeTable);
	}
	
	public static void main(String[] args) {
		new Course().procreate();
		
	}
}
