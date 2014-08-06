package org.studyclub.java8.collections.animals;

import org.studyclub.java8.collections.fruits.Fruit;

public interface Animal {

	default String name() {
		return this.getClass().getCanonicalName();
	}

	default void eat(Fruit fruit) {
		System.out.println(name() + " ate a " + fruit.taste() + " " + fruit.name() + " rich in  vitamin " + fruit.vitamins());
		sound();
	}
	
	void sound();

}
