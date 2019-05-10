package com.ezgroceries.shoppinglist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShoppingList {

    private final UUID shoppingListId;
    private final String name;
    private List<String> ingredients;

    public ShoppingList(UUID shoppingListId, String name) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.ingredients = new ArrayList<>();
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addIngredients(List<String> ingredients) {
        this.ingredients.addAll(ingredients);
    }
}