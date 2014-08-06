package org.studyclub.java8.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
	private static final Consumer<Fruit> printNewline = fruit -> println();
	
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
//		if(!fruitStream.allMatch(ROTTEN.negate()))
		// is the same as
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
		sweetFruits.forEach(
				printFruitName
				.andThen(printFruitTaste)
				.andThen(printNewline)
		);
		
		println("}");
		println();
	}
	
	private static void printHealthyFruitsSortedByName(Collection<Fruit> fruits) {
		println("printHealthyFruitsSortedByName() {");
		
		Collection<Fruit> healthyFruits = fruits.stream().filter(SWEET_FRUIT).collect(Collectors.toList());
		Comparator<Fruit> fruitByName = (f1,f2) -> f1.name().compareTo(f2.name());
		healthyFruits.stream().filter(HEALTHY_FRUIT).sorted(fruitByName).forEachOrdered(
				printFruitName
				.andThen(printFruitTaste)
				.andThen(printNewline)
		);
		
		println("}");
		println();
	}
	
	private static void makeFruitJuice(Collection<Fruit> fruits) {
		println("makeFruitJuice() {");
//		
//		Collector<Fruit,FruitSalad,FruitSalad> fruitSaladCollector = new Collector<Fruit, FruitSalad, FruitSalad>() {
//
//			@Override
//			public Supplier<FruitSalad> supplier() {
//				return FruitSalad::new;
//			}
//
//			@Override
//			public BiConsumer<FruitSalad, Fruit> accumulator() {
//				return FruitSalad::addFruit;
//			}
//
//			@Override
//			public BinaryOperator<FruitSalad> combiner() {
//				return FruitSalad::mixWith;
//			}
//
//			@Override
//			public Function<FruitSalad, FruitSalad> finisher() {
//				return f -> f;
//			}
//
//			@Override
//			public Set<java.util.stream.Collector.Characteristics> characteristics() {
//				Set<Characteristics> characteristics =new HashSet<>();
//				characteristics.add(Characteristics.UNORDERED);
//				return characteristics;
//			}
//		};
		
		Stream<Fruit> fruitStream = fruits.stream();
//		FruitSalad fruitSalad = fruitStream.collect(fruitSaladCollector);
		
		// or the much simpler
		FruitSalad fruitSalad = fruitStream.collect(FruitSalad::new, FruitSalad::addFruit, FruitSalad::mixWith);
		FruitJuiceMakingStrategy fruitJuiceReceipt = new FruitJuiceMakingStrategy();
		// TODO add receipt steps with addFunctionToProcessingChain
		FruitJuice juice = fruitJuiceReceipt.executeOn(fruitSalad);
		juice.drink();
		
		println("}");
		println();
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
