package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.model.ShoppingList;
import org.springframework.http.ResponseEntity;



import java.net.URI;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/shopping-lists", produces = "application/json")
public class ShoppingListController {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<ShoppingList> createShoppingList(@RequestBody Map<String, String> body) {
        UUID id = UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915");
        ShoppingList shoppingList = new ShoppingList(id, body.get("name"));
        return entityWithLocation(id).body(new ShoppingList(id, body.get("name")));
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ShoppingList>> getList() {

        List<ShoppingList> lists = new ArrayList<>();
        UUID uuid = UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c");
        ShoppingList shoppingList = new ShoppingList(uuid, "Stephanie's birthday");
        Arrays.asList("Tequila",
                "Triple sec",
                "Lime juice",
                "Salt",
                "Blue Curacao").forEach(shoppingList::addIngredient);
        lists.add(shoppingList);
        uuid = UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465");
        shoppingList = new ShoppingList(uuid, "Glenn's birhtday");
        Arrays.asList("Tequila",
                "Triple sec",
                "Lime juice",
                "Salt",
                "Blue Curacao").forEach(shoppingList::addIngredient);
        lists.add(shoppingList);
        return ResponseEntity.ok(lists);
    }

}
