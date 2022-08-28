package de.tilmanschweitzer.adventofcode.aoc2015;

import de.tilmanschweitzer.adventofcode.aoc2015.Day15PerfectRecipe.Ingredient;
import de.tilmanschweitzer.adventofcode.aoc2015.Day15PerfectRecipe.Recipe;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day15PerfectRecipeTest {

    @Test
    public void getResultOfFirstPuzzle() {
        assertEquals(13882464, new Day15PerfectRecipe().getResultOfFirstPuzzle());
    }

    @Test
    public void getResultOfSecondPuzzle() {
        assertEquals(11171160, new Day15PerfectRecipe().getResultOfSecondPuzzle());
    }

    @Test
    public void parseIngredient() {
        assertEquals(new Ingredient("Butterscotch", -1, -2, 6, 3, 8), Ingredient.parse("Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8"));
        assertEquals(new Ingredient("Cinnamon", 2, 3, -2, -1, 3), Ingredient.parse("Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"));
    }

    @Test
    public void recipeGetScore() {
        final Ingredient butterscotch = Ingredient.parse("Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8");
        final Ingredient cinnamon = Ingredient.parse("Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3");

        final Recipe recipe = Recipe.of(of(butterscotch, cinnamon), of(44, 56));

        assertEquals(62842880, recipe.getScore());
    }

    @Test
    public void findRecipeWithHighestScoreForTeaspoonsAndIngredients() {
        final Ingredient butterscotch = Ingredient.parse("Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8");
        final Ingredient cinnamon = Ingredient.parse("Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3");

        final Optional<Recipe> recipeOptional = Day15PerfectRecipe.findRecipeWithHighestScoreForTeaspoonsAndIngredients(100, List.of(butterscotch, cinnamon));

        assertTrue(recipeOptional.isPresent());

        final Recipe recipe = recipeOptional.get();

        assertEquals(44, recipe.getTeaspoonForIngredient(butterscotch));
        assertEquals(56, recipe.getTeaspoonForIngredient(cinnamon));
    }

    @Test
    public void findRecipeMatchingCaloriesForTeaspoonsAndIngredients() {
        final Ingredient butterscotch = Ingredient.parse("Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8");
        final Ingredient cinnamon = Ingredient.parse("Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3");

        final Optional<Recipe> recipeOptional = Day15PerfectRecipe.findRecipeMatchingCaloriesForTeaspoonsAndIngredients(100, 500, List.of(butterscotch, cinnamon));

        assertTrue(recipeOptional.isPresent());

        final Recipe recipe = recipeOptional.get();
        assertEquals(40, recipe.getTeaspoonForIngredient(butterscotch));
        assertEquals(60, recipe.getTeaspoonForIngredient(cinnamon));
        assertEquals(57600000, recipe.getScore());
    }

    @Test
    public void combinations() {
        assertEquals(Set.of(of(0, 2), of(1, 1), of(2, 0)), Day15PerfectRecipe.combinations(2, 2));
        assertEquals(Set.of(of(0, 0, 2), of(0, 1, 1), of(0, 2, 0), of(1, 0, 1), of(1, 1, 0), of(2, 0, 0)), Day15PerfectRecipe.combinations(3, 2));

        assertEquals(101, Day15PerfectRecipe.combinations(2, 100).size());
        assertEquals(176851, Day15PerfectRecipe.combinations(4, 100).size());
//        assertEquals(4598126, Day15PerfectRecipe.combinations(5, 100).size());
    }

}
