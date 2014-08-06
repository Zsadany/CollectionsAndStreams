package org.studyclub.java8.collections.fruits;

import java.util.Set;

import javafx.scene.paint.Color;

public interface Fruit extends Comparable<Fruit> {
	
	default String name() {
		return "AnonimousFruit";
	}
	
	Taste taste();
	
	Color color();
	
	Set<Vitamin> vitamins();
	
	boolean isRotten();
	
	@Override
	default int compareTo(Fruit o) {
		return name().compareTo(o.name());
	}
	
	enum Vitamin {
		A,B6,B12,C,D,E;
	}
	
	enum Taste {
		SOUR,SWEET_AND_SOUR,NEUTRAL,SWEET,ROTTEN;
	}
	
}
