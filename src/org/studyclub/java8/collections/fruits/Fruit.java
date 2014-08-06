package org.studyclub.java8.collections.fruits;

import java.util.Set;

import javafx.scene.paint.Color;

public interface Fruit {
	
	default String name() {
		return this.getClass().getCanonicalName();
	}
	
	Taste taste();
	
	Color color();
	
	Set<Vitamin> vitamins();
	
	boolean isRotten();
	
	enum Vitamin {
		A,B6,B12,C,D,E;
	}
	
	enum Taste {
		SOUR,SWEET_AND_SOUR,NEUTRAL,SWEET,ROTTEN;
	}
}
