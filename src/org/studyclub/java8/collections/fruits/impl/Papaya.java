package org.studyclub.java8.collections.fruits.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.paint.Color;

import org.studyclub.java8.collections.fruits.Fruit;

public class Papaya implements Fruit {

	private Set<Vitamin> vitamins = new HashSet<Vitamin>(Arrays.asList(Vitamin.C));
	private boolean rotten = false;
	
	public Papaya(int ageInDays) {
		if(ageInDays > 5) {
			rotten = true;
		}
	}
	
	@Override
	public Taste taste() {
		return Taste.SWEET;
	}

	@Override
	public Color color() {
		return Color.YELLOWGREEN;
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
