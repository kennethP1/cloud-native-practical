package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.model.CocktailResource;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ezgroceries.shoppinglist.services.CocktailService;



@RestController
@RequestMapping(value = "/cocktails", produces = "application/json")
public class CocktailController {
    private final CocktailService cocktailService;

    public CocktailController(CocktailService cocktailDBClient) {
        this.cocktailService = cocktailDBClient;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CocktailResource>  get(@RequestParam String search) {
        return (cocktailService.searchCocktails(search));


    }



}