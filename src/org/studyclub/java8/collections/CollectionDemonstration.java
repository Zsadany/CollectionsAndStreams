package org.studyclub.java8.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.studyclub.java8.collections.animals.Animal;
import org.studyclub.java8.collections.fruits.Fruit.Vitamin;
import org.studyclub.java8.collections.fruits.Fruit.Taste;
import org.studyclub.java8.collections.fruits.Fruit;
import org.studyclub.java8.collections.fruits.FruitJuice;
import org.studyclub.java8.collections.fruits.FruitJuiceMakingStrategy;
import org.studyclub.java8.collections.fruits.FruitSalad;
import org.studyclub.java8.collections.fruits.impl.Orange;
import org.studyclub.java8.collections.fruits.impl.Citron;
import org.studyclub.java8.collections.fruits.impl.Banana;

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

	private static final Consumer<Fruit> PRINT_FRUIT_NAME = fruit -> print(fruit.name() + " is:");
	private static final Consumer<Fruit> PRINT_FRUIT_TASTE = fruit -> print(fruit.taste());
	private static final Consumer<Fruit> PRINT_NEW_LINE = fruit -> println();
	
	public static void demonstrate(Collection<Fruit> fruits, Collection<Animal> animals) {
		feedAnimalsWithRottenFruit(fruits, animals);
		removeRotten(fruits);
		printSourFruitDetailsAsOneString(fruits);
		printSweetFruits(fruits);
		printHealthyFruitsSortedByName(fruits);
		makeFruitJuice(fruits);
	}

	private static void feedAnimalsWithRottenFruit(Collection<Fruit> fruits, Collection<Animal> animals) {
		println("feedAnimalsWithRottenFruit() {");
		
		Iterator<Fruit> fruitIterator = fruits.iterator();
		while (fruitIterator.hasNext()) {
			Fruit fruit = fruitIterator.next();
			Optional<Animal> anAnimal = animals.parallelStream().findAny();
			if (fruit.isRotten())
				anAnimal.ifPresent(animal -> animal.eat(fruit));
		}
		
		println("}");
		println();
	}

	private static void removeRotten(Collection<Fruit> fruits) {
		println("removeRotten() {");
		
		print("Current fruits: ");
		println(fruits);
		Stream<Fruit> fruitStream = fruits.stream();
		if (fruitStream.anyMatch(ROTTEN)) {
			println("Removing rotten fruits!");
			fruits.removeIf(ROTTEN);
		}
		print("Fruits that are not rotten: ");
		println(fruits);
		
		println("}");
		println();
	}
	
	private static void printSourFruitDetailsAsOneString(Collection<Fruit> fruits) {
		println("printSourFruitDetailsAsOneString() {");
		
		Function<Fruit,String> fruitToString = f -> f.name() + " is " + f.taste() + " but has " + f.vitamins() + " vitamin! ";
		
		Stream<Fruit> sourFruitStream = fruits.parallelStream().filter(SOUR_FRUIT);
		Stream<String> sourFruitStringStream = sourFruitStream.map(fruitToString);
		StringBuilder sourFruitDetailsCollector = sourFruitStringStream.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
		String sourFruitDetails = sourFruitDetailsCollector.toString();
		
//		List<Fruit> fruitList = sourFruitStream.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
//		Set<Fruit> fruitSet = sourFruitStream.collect(HashSet::new, HashSet::add, HashSet::addAll);
		
		print(sourFruitDetails);
		
		println("}");
		println();
	}


	private static void printSweetFruits(Collection<Fruit> fruits) {
		println("printSweetFruits() {");
		
		List<Fruit> sweetFruits = fruits.stream().filter(SWEET_FRUIT).collect(Collectors.toList());
		sweetFruits.forEach(PRINT_FRUIT_NAME
				.andThen(PRINT_FRUIT_TASTE)
				.andThen(PRINT_NEW_LINE)
		);
		
		println("}");
		println();
	}
	
	private static void printHealthyFruitsSortedByName(Collection<Fruit> fruits) {
		println("printHealthyFruitsSortedByName() {");
		
		Collection<Fruit> healthyFruits = fruits.stream().filter(SWEET_FRUIT).collect(Collectors.toList());
		Comparator<Fruit> fruitByName = (f1,f2) -> f1.name().compareTo(f2.name());
		healthyFruits.stream().filter(HEALTHY_FRUIT).sorted(fruitByName).forEachOrdered(
				PRINT_FRUIT_NAME
				.andThen(PRINT_FRUIT_TASTE)
				.andThen(PRINT_NEW_LINE)
		);
		
		println("}");
		println();
	}
	
	private static void makeFruitJuice(Collection<Fruit> fruits) {
		println("makeFruitJuice() {");
		
		FruitSalad fruitSalad = fruits.stream().collect(FruitSalad::new, FruitSalad::addFruit, FruitSalad::mixWith);
		
		makeHealthyFruitJuice(fruitSalad);
		makeSweetFruitJuice(fruitSalad);
		makeSourFruitJuice(fruitSalad);
		
		println("}");
		println();
	}

	private static void makeHealthyFruitJuice(FruitSalad fruitSalad) {
		FruitJuiceMakingStrategy healthyFruitJuiceReceipt = new FruitJuiceMakingStrategy();
		healthyFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.addFruit(new Orange(2)));
		healthyFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.addFruit(new Citron(2)));
		healthyFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.addFruit(new Banana(2)));
		healthyFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.keepFruitsThatAre(HEALTHY_FRUIT));
		healthyFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.removeRottenFruits());
		
		FruitJuice healthyFruitJuice = healthyFruitJuiceReceipt.executeOn(fruitSalad);
		healthyFruitJuice.drinkSome();
	}
	
	private static void makeSweetFruitJuice(FruitSalad fruitSalad) {
		FruitJuiceMakingStrategy sweetFruitJuiceReceipt = new FruitJuiceMakingStrategy();
		sweetFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.addFruit(new Orange(2)));
		sweetFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.removeFruitsWith(Taste.SOUR));
		sweetFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.removeRottenFruits());
		
		FruitJuice sweetFruitJuice = sweetFruitJuiceReceipt.executeOn(fruitSalad);
		sweetFruitJuice.drinkSome();
	}
	
	private static void makeSourFruitJuice(FruitSalad fruitSalad) {
		FruitJuiceMakingStrategy sourFruitJuiceReceipt = new FruitJuiceMakingStrategy();
		sourFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.addFruit(new Citron(2)));
		sourFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.addFruit(new Banana(2)));
		sourFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.removeFruitsWith(Taste.SWEET));
		sourFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.removeFruitsWith(Taste.NEUTRAL));
		sourFruitJuiceReceipt.addFunctionToProcessingChain(fs -> fs.removeRottenFruits());
		
		FruitJuice sourFruitJuice = sourFruitJuiceReceipt.executeOn(fruitSalad);
		sourFruitJuice.drinkSome();
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
