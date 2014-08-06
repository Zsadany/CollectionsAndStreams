package org.studyclub.java8.collections.fruits;

public class FruitJuice {
	
	private String name;
	
	public FruitJuice(String name) {
		this.name = name;
	}
	
	public void drink() {
		System.out.println("You just drank a glass of " + name + "!");
	}
	
}
