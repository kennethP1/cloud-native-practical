package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.model.CocktailResource;
import java.util.List;

public interface CocktailService {

    List<CocktailResource> searchCocktails(String search);

    List<CocktailEntity> getAllById(List<String> cocktails);
}
