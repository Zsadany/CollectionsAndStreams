package org.studyclub.java8.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.studyclub.java8.collections.animals.Animal;
import org.studyclub.java8.collections.fruits.Fruit.Vitamin;
import org.studyclub.java8.collections.fruits.Fruit.Taste;
import org.studyclub.java8.collections.fruits.Fruit;
import org.studyclub.java8.collections.fruits.FruitSalad;

public class CollectionDemonstration {

	private static final Predicate<Fruit> HAS_B6_VITAMIN = new Predicate<Fruit>() {
		@Override
		public boolean test(Fruit fruit) {
			return fruit.vitamins().contains(Vitamin.B6);
		}
	};
	private static final Predicate<Fruit> HAS_C_VITAMIN = fruit -> fruit.vitamins().contains(Vitamin.C);
	private static final Predicate<Fruit> HAS_B12_VITAMIN = fruit -> fruit.vitamins().contains(Vitamin.B12);
	private static final Predicate<Fruit> SOUR_FRUIT = new Predicate<Fruit>() {
		@Override
		public boolean test(Fruit f) {
			return f.taste().equals(Taste.SOUR);
		}
	};
	private static final Predicate<Fruit> SWEET_FRUIT = fruit -> fruit.taste().equals(Taste.SWEET);
	private static final Predicate<Fruit> ROTTEN = fruit -> fruit.isRotten();
	private static final Predicate<Fruit> HEALTHY_FRUIT = HAS_C_VITAMIN.or(HAS_B6_VITAMIN).or(HAS_B12_VITAMIN).and(ROTTEN.negate());

	private static final Consumer<Fruit> printFruitName = fruit -> print(fruit.name() + " is:");
	private static final Consumer<Fruit> printFruitTaste = fruit -> print(fruit.taste());
	private static final Consumer<Fruit> printFruitColor = fruit -> print(fruit.color());
	private static final Consumer<Fruit> printNewline = fruit -> println();
	
	public static void demonstrate(Collection<Fruit> fruits, Collection<Animal> animals) {
		feedAnimalsWithRottenFruit(fruits, animals);
		removeRotten(fruits);
		printSourFruitDetailsAsOneString(fruits);
		printSweetFruits(fruits);
		printHealthyFruitsSortedByName(fruits);
	}

	private static void printSourFruitDetailsAsOneString(Collection<Fruit> fruits) {
		Function<Fruit,String> fruitToString = f -> f.name() + " is " + f.taste() + " but has " + f.vitamins() + " vitamin! ";
		
		Stream<Fruit> sourFruitStream = fruits.parallelStream().filter(SOUR_FRUIT);
		Stream<String> sourFruitStringStream = sourFruitStream.map(fruitToString);
		StringBuilder sourFruitDetailsCollector = sourFruitStringStream.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
		String sourFruitDetails = sourFruitDetailsCollector.toString();
		
		List<Fruit> fruitList = sourFruitStream.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		Set<Fruit> fruitSet = sourFruitStream.collect(HashSet::new, HashSet::add, HashSet::addAll);
		Collector<Fruit,?,FruitSalad> fruitSaladCollector;
//		sourFruitStream.collect(fruitSaladCollector);
		
		print(sourFruitDetails);
	}

	private static void feedAnimalsWithRottenFruit(Collection<Fruit> fruits, Collection<Animal> animals) {
		Iterator<Fruit> fruitIterator = fruits.iterator();
		while (fruitIterator.hasNext()) {
			Fruit fruit = fruitIterator.next();
			Optional<Animal> anAnimal = animals.parallelStream().findAny();
			if (fruit.isRotten())
				anAnimal.ifPresent(animal -> animal.eat(fruit));
		}
	}

	private static void removeRotten(Collection<Fruit> fruits) {
		println("Current fruits:");
		println(fruits);
		Stream<Fruit> fruitStream = fruits.stream();
		boolean hasRottenFruits = !fruitStream.allMatch(ROTTEN.negate());
		// is the same as
		hasRottenFruits = fruitStream.anyMatch(ROTTEN);
		if (hasRottenFruits) {
			println("Removing rotten fruits!");
			fruits.removeIf(ROTTEN);
		}
		println("Fruits that are not rotten:");
		println(fruits);
	}

	private static void printSweetFruits(Collection<Fruit> fruits) {
		List<Fruit> sweetFruits = fruits.stream().filter(SWEET_FRUIT).collect(Collectors.toList());
		sweetFruits.forEach(
				printFruitName.andThen(printFruitTaste).andThen(printFruitColor).andThen(printNewline)
		);
	}
	
	private static void printHealthyFruitsSortedByName(Collection<Fruit> fruits) {
		Collection<Fruit> healthyFruits = fruits.stream().filter(SWEET_FRUIT).collect(Collectors.toList());
		Comparator<Fruit> fruitByName = (f1,f2) -> f1.name().compareTo(f2.name());
		healthyFruits.stream().filter(HEALTHY_FRUIT).sorted(fruitByName).forEachOrdered(
				printFruitName.andThen(printFruitColor).andThen(printFruitTaste).andThen(printNewline)
		);
	}

	public static void println() {
		System.out.println();
	}

	public static void println(Object line) {
		System.out.println(line);
	}

	public static void print(Object object) {
		System.out.print(object + " ");
	}

}
