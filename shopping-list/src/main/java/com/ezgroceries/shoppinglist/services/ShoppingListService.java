package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.model.ShoppingList;
import java.util.List;

public interface ShoppingListService {

    ShoppingList createShoppingList(String name);

    ShoppingList get(String id);

    ShoppingList addCocktails(String shoppingListId, List<String> cocktails);

    List<ShoppingList> getAllShoppingLists();
}
