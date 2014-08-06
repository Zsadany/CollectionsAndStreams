package org.studyclub.java8.collections.fruits.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.studyclub.java8.collections.fruits.Fruit;

import javafx.scene.paint.Color;

public class Citron implements Fruit {

	private Set<Vitamin> vitamins = new HashSet<Vitamin>(Arrays.asList(Vitamin.C));
	private boolean rotten = false;
	
	public Citron(int ageInDays) {
		if(ageInDays > 5) {
			rotten = true;
		}
	}
	
	@Override
	public Taste taste() {
		return Fruit.Taste.SOUR;
	}

	@Override
	public Color color() {
		return Color.YELLOW;
	}

	@Override
	public Set<Vitamin> vitamins() {
		return vitamins;
	}

	@Override
	public boolean isRotten() {
		return rotten;
	}

}
