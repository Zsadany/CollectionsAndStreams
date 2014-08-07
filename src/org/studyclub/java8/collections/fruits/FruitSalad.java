package org.studyclub.java8.collections.fruits;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

import org.studyclub.java8.collections.fruits.Fruit.Taste;

public class FruitSalad {

	private Collection<Fruit> fruits;

	public FruitSalad() {
		fruits = new HashSet<Fruit>();
	}

	public FruitSalad addFruit(Fruit fruit) {
		fruits.add(fruit);
		return this;
	}

	public FruitSalad addAllFruits(Collection<Fruit> fruits) {
		this.fruits.addAll(fruits);
		return this;
	}
	
	public FruitSalad removeFruitsWith(Taste taste) {
		fruits.removeIf(f -> f.taste() == taste);
		return this;
	}
	
	public FruitSalad removeRottenFruits() {
		fruits.removeIf(f -> f.isRotten());
		return this;
	}
	
	public FruitSalad keepFruitsThatAre(Predicate<Fruit> fruitFilter) {
		fruits.removeIf(fruitFilter.negate());
		return this;
	}

	public FruitSalad mixWith(FruitSalad fruitSalad) {
		this.addAllFruits(fruitSalad.getFruits());
		return this;
	}

	public Collection<Fruit> getFruits() {
		return fruits;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Fruit salad of ");
		builder.append(fruits);
		builder.append(" fruits!");
		return builder.toString();
	}

}
