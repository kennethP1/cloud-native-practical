package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.services.CocktailDBResponse.DrinkResource;
import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.model.CocktailResource;
import com.ezgroceries.shoppinglist.repositories.CocktailRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CocktailServiceImpl implements CocktailService {

    private final CocktailRepository cocktailRepository;
    private final CocktailDBClient cocktailDBClient;

    public CocktailServiceImpl(CocktailRepository cocktailRepository, CocktailDBClient cocktailDBClient) {
        this.cocktailRepository = cocktailRepository;
        this.cocktailDBClient = cocktailDBClient;
    }

    @Override
    public List<CocktailResource> searchCocktails(String search) {
        CocktailDBResponse response = cocktailDBClient.searchCocktails(search);
        return mergeCocktails(response.getDrinks());
    }

    @Override
    public List<CocktailEntity> getAllById(List<String> cocktails) {
        return cocktailRepository.findAllById(cocktails.stream().map(UUID::fromString).collect(Collectors.toList()));
    }

    private List<CocktailResource> mergeCocktails(List<DrinkResource> drinks) {
        // Get all the idDrink attributes
        List<String> ids = drinks.stream().map(DrinkResource::getIdDrink).collect(Collectors.toList());

        // Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, CocktailEntity> existingEntityMap = cocktailRepository.findByIdDrinkIn(ids).stream()
                .collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        // Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, CocktailEntity> allEntityMap = drinks.stream().map(drinkResource -> {
            CocktailEntity cocktailEntity = existingEntityMap.get(drinkResource.getIdDrink());
            if (cocktailEntity == null) {
                CocktailEntity newCocktailEntity = new CocktailEntity(UUID.randomUUID(), drinkResource.getIdDrink(), drinkResource.getStrDrink());
                cocktailEntity = cocktailRepository.save(newCocktailEntity);
            }
            return cocktailEntity;
        }).collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        // Merge drinks and our entities, transform to CocktailResource instances
        return mergeAndTransform(drinks, allEntityMap);
    }

    private List<CocktailResource> mergeAndTransform(List<DrinkResource> drinks, Map<String, CocktailEntity> allEntityMap) {
        return drinks.stream().map(drinkResource -> new CocktailResource(allEntityMap.get(drinkResource.getIdDrink()).getId(), drinkResource.getStrDrink(),
                drinkResource.getStrGlass(),
                drinkResource.getStrInstructions(), drinkResource.getStrDrinkThumb(), getIngredients(drinkResource))).collect(Collectors.toList());
    }

    private List<String> getIngredients(DrinkResource drinkResource) {
        return Arrays.asList(
                drinkResource.getStrIngredient1(),
                drinkResource.getStrIngredient2(),
                drinkResource.getStrIngredient3(),
                drinkResource.getStrIngredient4(),
                drinkResource.getStrIngredient5()
        );
    }
}
