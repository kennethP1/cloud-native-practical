package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.model.CocktailResource;

import java.util.List;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ezgroceries.shoppinglist.services.CocktailService;



@RestController
@RequestMapping(value = "/cocktails", produces = "application/json")
public class CocktailController {
    private final CocktailService cocktailService;

    public CocktailController(CocktailService cocktailDBClient) {
        this.cocktailService = cocktailDBClient;
    }


    @GetMapping
    public ResponseEntity<List<CocktailResource>>  get(@RequestParam String search) {
        return ResponseEntity.ok(cocktailService.searchCocktails(search));


    }



}