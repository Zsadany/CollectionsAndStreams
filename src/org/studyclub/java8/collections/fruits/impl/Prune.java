package org.studyclub.java8.collections.fruits.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.studyclub.java8.collections.fruits.Fruit;

import javafx.scene.paint.Color;

public class Prune implements Fruit {

	private Set<Vitamin> vitamins = new HashSet<Vitamin>(Arrays.asList(Vitamin.B6));
	private boolean rotten = false;
	
	public Prune(int ageInDays) {
		if(ageInDays > 5) {
			rotten = true;
		}
	}
	
	@Override
	public Taste taste() {
		return Taste.NEUTRAL;
	}

	@Override
	public Color color() {
		return Color.DARKVIOLET;
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
