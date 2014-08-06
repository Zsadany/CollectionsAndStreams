package org.studyclub.java8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.studyclub.java8.collections.CollectionDemonstration;
import org.studyclub.java8.collections.animals.Animal;
import org.studyclub.java8.collections.animals.impl.Chicken;
import org.studyclub.java8.collections.animals.impl.Swine;
import org.studyclub.java8.collections.fruits.Fruit;
import org.studyclub.java8.collections.fruits.impl.Banana;
import org.studyclub.java8.collections.fruits.impl.Citron;
import org.studyclub.java8.collections.fruits.impl.Mango;
import org.studyclub.java8.collections.fruits.impl.Orange;
import org.studyclub.java8.collections.fruits.impl.Papaya;
import org.studyclub.java8.collections.fruits.impl.Pineapple;
import org.studyclub.java8.collections.fruits.impl.Prune;

public class DemonstrationMain {
	
	public static void main(String[] args) {
		
		Collection<Fruit> fruits = new ArrayList<>(21);
		Collections.addAll(fruits, 
				new Orange(1), new Orange(4), new Orange(100), 
				new Banana(2), new Banana(3), new Banana(30), 
				new Citron(3), new Citron(0), new Citron(7),
				new Mango(4), new Mango(4), new Mango(4), 
				new Papaya(5), new Papaya(1), new Papaya(8), 
				new Pineapple(6), new Pineapple(2), new Pineapple(5), 
				new Prune(3), new Prune(1), new Prune(7)
		);
		
		Collection<Animal> animals = new HashSet<>();
		Collections.addAll(animals,
				new Swine(), new Chicken()
		);
		
		
		CollectionDemonstration.demonstrate(fruits, animals);
	}
	
}
