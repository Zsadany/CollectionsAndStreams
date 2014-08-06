package org.studyclub.java8.collections.animals.impl;

import org.studyclub.java8.collections.animals.Animal;

public class Chicken implements Animal {

	@Override
	public String name() {
		return "Chicken";
	}
	
	@Override
	public void sound() {
		System.out.println("Pock Pock Pock!");
	}

	@Override
	public String toString() {
		return name();
	}
}
