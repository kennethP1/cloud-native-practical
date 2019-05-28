package com.ezgroceries.shoppinglist.database;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.repositories.CocktailRepository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@FeignClient(name = "cocktailDBClient", url = "https://www.thecocktaildb.com/api/json/v1/1", fallback = CocktailDBClient.CocktailDBClientFallback.class)
public interface CocktailDBClient {

    @GetMapping(value = "search.php")
    CocktailDBResponse searchCocktails(@RequestParam("s") String search);


    @Component
    class CocktailDBClientFallback implements CocktailDBClient {

        private final CocktailRepository cocktailRepository;

        public CocktailDBClientFallback(CocktailRepository cocktailRepository) {
            this.cocktailRepository = cocktailRepository;
        }

        @Override
        public CocktailDBResponse searchCocktails(String search) {
            CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
            CocktailDBResponse.DrinkResource drink = new CocktailDBResponse.DrinkResource();
            drink.setStrDrink("fallbacktestDrink");
            List<CocktailDBResponse.DrinkResource> list = new ArrayList<>();
            list.add(drink);
            cocktailDBResponse.setDrinks(list);
            return cocktailDBResponse;
        }
    }
}
