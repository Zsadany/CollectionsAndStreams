package org.studyclub.java8.collections.fruits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class FruitJuiceMakingStrategy {
	
	private Collection<Function<FruitSalad,FruitSalad>> fruitSaladProcessingChain;
	private Function<FruitSalad,FruitJuice> finishJuice;
	
	public FruitJuiceMakingStrategy() {
		fruitSaladProcessingChain = new ArrayList<>();
		finishJuice =  new Function<FruitSalad, FruitJuice>() {
			@Override
			public FruitJuice apply(FruitSalad fs) {
				Collection<Fruit> fruits = fs.getFruits();
				String name = fruits.stream()
						.map(f -> f.name())
						.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
						.toString() + " juice";
				return new FruitJuice(name);
			}
		};
	}
	
	//TODO static factory methods with different strategies ex.: sequentialFruitJuiceMakingStrategy (init with a list),
	// parallelFruitJuiceMakingStrategy (convert the fruits into a parallelStream in the juice finisher)
	
	public void addFunctionToProcessingChain (Function<FruitSalad,FruitSalad> fruitSaladPreparationStep) {
		fruitSaladProcessingChain.add(fruitSaladPreparationStep);
	}

	public FruitJuice executeOn(FruitSalad fruitSalad) {
		fruitSaladProcessingChain.forEach(callback -> callback.apply(fruitSalad));
		return finishJuice.apply(fruitSalad);
	}

}
