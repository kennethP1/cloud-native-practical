package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.model.ShoppingList;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import org.springframework.http.ResponseEntity;



import java.net.URI;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/shopping-lists", produces = "application/json")
public class ShoppingListController {


    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<ShoppingList> createShoppingList(@RequestBody Map<String, String> body) {
        ShoppingList shoppingList = shoppingListService.createShoppingList(body.get("name"));
        return entityWithLocation(shoppingList.getShoppingListId()).body(shoppingList);

    }

    private ResponseEntity.BodyBuilder entityWithLocation(Object resourceId) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{childId}").buildAndExpand(resourceId)
                .toUri();
        return ResponseEntity.created(location);
    }

    @PostMapping(path = "/{id}/cocktails")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Map<String, String>>> addCocktails(@PathVariable String id, @RequestBody List<Map<String, String>> body) {
        return ResponseEntity.ok(body.subList(0,1));
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ShoppingList>  getList(@PathVariable String id) {
        return ResponseEntity.ok(shoppingListService.get(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ShoppingList>> getAllShoppingLists() {
        return ResponseEntity.ok(shoppingListService.getAllShoppingLists());
    }

}
