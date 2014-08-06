package org.studyclub.java8.collections.animals.impl;

import org.studyclub.java8.collections.animals.Animal;

public class Swine implements Animal {

	@Override
	public String name() {
		return "Swine";
	}

	@Override
	public void sound() {
		System.out.println("Oink Oink!");
	}

	@Override
	public String toString() {
		return name();
	}

}
