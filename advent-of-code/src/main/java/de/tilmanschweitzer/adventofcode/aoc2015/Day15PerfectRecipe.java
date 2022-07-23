package de.tilmanschweitzer.adventofcode.aoc2015;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import de.tilmanschweitzer.adventofcode.day.MultiLineAdventOfCodeDay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static de.tilmanschweitzer.adventofcode.common.CollectionFunctions.sum;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class Day15PerfectRecipe extends MultiLineAdventOfCodeDay<Day15PerfectRecipe.Ingredient, Integer> {

    public Day15PerfectRecipe() {
        super(2015, 15);
    }

    @Override
    public Integer getResultOfFirstPuzzle(final List<Ingredient> ingredients) {
        final Optional<Recipe> recipe = findRecipeWithHighestScoreForTeaspoonsAndIngredients(100, ingredients);
        return recipe.get().getScore();
    }

    @Override
    public Integer getResultOfSecondPuzzle(final List<Ingredient> ingredients) {
        final Optional<Recipe> recipe = findRecipeMatchingCaloriesForTeaspoonsAndIngredients(100, 500, ingredients);
        return recipe.get().getScore();
    }

    @Override
    protected InputStream getInputAsStream() {
        return getSystemResourceAsStream("2015/day15-input.txt");
    }

    @Override
    protected Ingredient parseLine(String line) {
        return Ingredient.parse(line);
    }

    public static Set<List<Integer>> combinations(int elements, int sum) {
        return combinations(ImmutableList.of(), elements, sum).collect(Collectors.toSet());
    }

    private static Stream<List<Integer>> combinations(List<Integer> currentCandidate, int elements, int sum) {
        if (currentCandidate.size() == elements) {
            if (sum == sum(currentCandidate)) {
                return Stream.of(currentCandidate);
            }
            return Stream.empty();
        }

        final int remainingSum = sum - sum(currentCandidate);

        return IntStream.range(0, remainingSum + 1).parallel().boxed().map(num -> {
            final List<Integer> nextCandidate = new ImmutableList.Builder<Integer>().addAll(currentCandidate).add(num).build();
            return combinations(nextCandidate, elements, sum);
        }).flatMap(Streams::concat);
    }

    public static Optional<Recipe> findRecipeWithHighestScoreForTeaspoonsAndIngredients(int maxTeaspoons, List<Ingredient> ingredients) {
        return findRecipeWithHighestScoreForTeaspoonsAndIngredients(maxTeaspoons, ingredients, (recipe) -> true);
    }

    public static Optional<Recipe> findRecipeWithHighestScoreForTeaspoonsAndIngredients(int maxTeaspoons, List<Ingredient> ingredients, Predicate<Recipe> additionalCondition) {
        final Set<List<Integer>> teaspoonCombinations = combinations(ingredients.size(), maxTeaspoons);
        int currentHighestScore = 0;
        Optional<Recipe> currentHighestScoredRecipe = Optional.empty();

        for (List<Integer> teaspoons : teaspoonCombinations) {
            final Recipe candidate = Recipe.of(ingredients, teaspoons);
            final int candidateScore = candidate.getScore();
            if (candidateScore > currentHighestScore && additionalCondition.apply(candidate)) {
                currentHighestScore = candidateScore;
                currentHighestScoredRecipe = Optional.of(candidate);
            }
        }

        return currentHighestScoredRecipe;
    }

    public static Optional<Recipe> findRecipeMatchingCaloriesForTeaspoonsAndIngredients(int maxTeaspoons, int wantedCalories, List<Ingredient> ingredients) {
        return findRecipeWithHighestScoreForTeaspoonsAndIngredients(maxTeaspoons, ingredients, (recipe) -> recipe.getCalories() == wantedCalories);
    }

    public static class Recipe {
        final Map<Ingredient, Integer> recipe;

        public Recipe(Map<Ingredient, Integer> recipe) {
            this.recipe = recipe;
        }

        public static Recipe of(List<Ingredient> ingredients, List<Integer> teaspoons) {
            final Map<Ingredient, Integer> recipe = new HashMap<>();
            if (ingredients.size() != teaspoons.size()) {
                throw new RuntimeException("Size of ingredients and teaspoons must match");
            }
            IntStream.range(0, teaspoons.size()).forEach(index -> {
                recipe.put(ingredients.get(index), teaspoons.get(index));
            });

            return new Recipe(recipe);
        }

        public Set<Ingredient> getIngredients() {
            return recipe.keySet();
        }

        public Integer getTeaspoonForIngredient(Ingredient ingredient) {
            return recipe.get(ingredient);
        }

        private Integer score(Function<Ingredient, Integer> getter) {
            return Math.max(0, sum(getIngredients().stream().map(ingredient -> {
                return getter.apply(ingredient) * getTeaspoonForIngredient(ingredient);
            })));
        }

        public int getScore() {
            return score(Ingredient::getCapacity) * score(Ingredient::getDurability) * score(Ingredient::getFlavor) * score(Ingredient::getTexture);
        }

        public int getCalories() {
            return score(Ingredient::getCalories);
        }
    }

    @EqualsAndHashCode
    @ToString
    @Getter
    public static class Ingredient {
        final String name;
        final int capacity;
        final int durability;
        final int flavor;
        final int texture;
        final int calories;

        public Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
            this.name = name;
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }

        public static Ingredient parse(String line) {
            final Pattern parsePattern = Pattern.compile("(\\w+): capacity (-?\\d+), durability (-?\\d+), flavor (-?\\d+), texture (-?\\d+), calories (-?\\d+)");

            final Matcher matcher = parsePattern.matcher(line);
            if (!matcher.find()) {
                throw new RuntimeException("Could not parse line: " + line);
            }

            final String name = matcher.group(1);
            final int capacity = Integer.parseInt(matcher.group(2));
            final int durability = Integer.parseInt(matcher.group(3));
            final int flavor = Integer.parseInt(matcher.group(4));
            final int texture = Integer.parseInt(matcher.group(5));
            final int calories = Integer.parseInt(matcher.group(6));

            return new Ingredient(name, capacity, durability, flavor, texture, calories);
        }
    }
}
