package org.studyclub.java8.collections.fruits.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.paint.Color;

import org.studyclub.java8.collections.fruits.Fruit;

public class Mango implements Fruit {

	private Set<Vitamin> vitamins = new HashSet<Vitamin>(Arrays.asList(Vitamin.E));
	private boolean rotten = false;
	
	public Mango(int ageInDays) {
		if(ageInDays > 5) {
			rotten = true;
		}
	}
	
	@Override
	public String name() {
		return "Mango";
	}
	
	@Override
	public Taste taste() {
		return Taste.NEUTRAL;
	}

	@Override
	public Color color() {
		return Color.FORESTGREEN;
	}

	@Override
	public Set<Vitamin> vitamins() {
		return vitamins;
	}

	@Override
	public boolean isRotten() {
		return rotten;
	}

	@Override
	public String toString() {
		return name();
	}

}
