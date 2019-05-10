package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.model.CocktailResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ezgroceries.shoppinglist.database.CocktailDBClient;
import com.ezgroceries.shoppinglist.database.CocktailDBResponse;


@RestController
@RequestMapping(value = "/cocktails", produces = "application/json")
public class CocktailController {
    private final CocktailDBClient cocktailDBClient;

    public CocktailController(CocktailDBClient cocktailDBClient) {
        this.cocktailDBClient = cocktailDBClient;
    }


    @GetMapping
    public List<CocktailResource> get(@RequestParam String search) {

        //return new ArrayList<>(getDummyResources());
        //return ResponseEntity.ok(generateList(cocktailDBClient.searchCocktails(search)));
        return  generateList(cocktailDBClient.searchCocktails(search));

    }

    /*private List<CocktailResource> getDummyResources() {
        return Arrays.asList(
                new CocktailResource(
                        UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"), "Margerita",
                        "Cocktail glass",
                        "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                        "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
                        Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt")),
                new CocktailResource(
                        UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073"), "Blue Margerita",
                        "Cocktail glass",
                        "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                        "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
                        Arrays.asList("Tequila", "Blue Curacao", "Lime juice", "Salt")));
    }*/

    private List<CocktailResource> generateList(CocktailDBResponse response){
        List<CocktailResource> listResponse;

        // map the list<DrinkResource> to the list<CocktailResource> Uses stream instead of for loop
        listResponse = response.getDrinks().stream().map(

                 dpresponce -> new CocktailResource(
                        UUID.randomUUID(),
                         dpresponce.getStrDrink(),
                         dpresponce.getStrGlass(),
                         dpresponce.getStrInstructions(),
                         dpresponce.getStrDrinkThumb(),
                         Stream.of(
                                 dpresponce.getStrIngredient1(),
                                 dpresponce.getStrIngredient2(),
                                 dpresponce.getStrIngredient3(),
                                 dpresponce.getStrIngredient4(),
                                 dpresponce.getStrIngredient5()
                         ).filter(StringUtils::isNotBlank).collect(Collectors.toList())
                )
        ).collect(Collectors.toList());
        return listResponse;
    }




}