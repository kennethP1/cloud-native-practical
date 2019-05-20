package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.entities.ShoppingListEntity;
import com.ezgroceries.shoppinglist.model.ShoppingList;
import com.ezgroceries.shoppinglist.repositories.ShoppingListRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ShoppingListServiceImpl implements ShoppingListService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ShoppingListRepository shoppingListRepository;
    private final CocktailService cocktailService;

    public ShoppingListServiceImpl(ShoppingListRepository shoppingListRepository, CocktailService cocktailService) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailService = cocktailService;
    }

    @Override
    public ShoppingList createShoppingList(String name) {
        ShoppingListEntity shoppingListEntity = new ShoppingListEntity(name);
        ShoppingListEntity saved = shoppingListRepository.save(shoppingListEntity);
        logger.info("Created shopping list '" + saved.getName() + "' with id '" + saved.getId() + "'");
        return fromShoppingListEntity(saved);
    }

    private ShoppingList fromShoppingListEntity(ShoppingListEntity shoppingListEntity) {
        return new ShoppingList(shoppingListEntity.getId(), shoppingListEntity.getName());
    }
    @Override
    public ShoppingList get(String shoppingListId) {
        Optional<ShoppingListEntity> shoppingList = shoppingListRepository.findById(UUID.fromString(shoppingListId));
        return shoppingList.map(this::fromShoppingListEntity)
                .orElseThrow(() -> new ShoppingListException("Shopping list with id '" + shoppingListId + "' not found"));
    }



    @Override
    public ShoppingList addCocktails(String shoppingListId, List<String> cocktails) {
        List<CocktailEntity> cocktailEntities = cocktailService.getAllById(cocktails);
        return shoppingListRepository.findById(UUID.fromString(shoppingListId)).map(shoppingList -> {
            shoppingList.addCocktails(cocktailEntities);
            shoppingListRepository.save(shoppingList);
            return fromShoppingListEntity(shoppingList);
        }).orElseThrow(() -> new ShoppingListException("Shopping list with id '" + shoppingListId + "' not found"));
    }

    @Override
    public List<ShoppingList> getAllShoppingLists() {
        List<ShoppingListEntity> entity = shoppingListRepository.findAll();
        return entity.stream().map(this::fromShoppingListEntityWithIngredients).collect(Collectors.toList());
    }



    private ShoppingList fromShoppingListEntityWithIngredients(ShoppingListEntity entity){
        ShoppingList shoppingList = fromShoppingListEntity(entity);
        List<CocktailEntity> entities = (entity.getCocktails() != null) ? entity.getCocktails() : new ArrayList<>();
        List<String> ids = entities.stream().map(CocktailEntity::getId).map(UUID::toString).collect(Collectors.toList());
        List<String> ingredients = cocktailService.getAllById(ids).stream().map(CocktailEntity::getIngredients).flatMap(Set::stream).distinct().collect(Collectors.toList());
        shoppingList.addIngredients(ingredients);
        return shoppingList;
    }
}
