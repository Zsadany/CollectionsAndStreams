package org.studyclub.java8.collections.fruits.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.studyclub.java8.collections.fruits.Fruit;

import javafx.scene.paint.Color;

public class Orange implements Fruit {

	private Set<Vitamin> vitamins = new HashSet<Vitamin>(Arrays.asList(Vitamin.C));
	private boolean rotten = false;
	
	public Orange(int ageInDays) {
		if(ageInDays > 5) {
			rotten = true;
		}
	}

	@Override
	public String name() {
		return "Orange";
	}
	
	@Override
	public Taste taste() {
		return Taste.SWEET;
	}

	@Override
	public Color color() {
		return Color.ORANGE;
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
