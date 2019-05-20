package com.ezgroceries.shoppinglist.entities;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SHOPPING_LIST")
public class ShoppingListEntity {

    @Id
    private final UUID id;
    private final String name;

    @ManyToMany
    @JoinTable( name="COCKTAIL_SHOPPING_LIST",
            joinColumns = @JoinColumn(name="SHOPPING_LIST_ID"),
            inverseJoinColumns = @JoinColumn(name = "COCKTAIL_ID"))
    private List<CocktailEntity> cocktails;


    public ShoppingListEntity(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCocktails(List<CocktailEntity> cocktails) {
        this.cocktails = cocktails;
    }

    public void addCocktails(CocktailEntity... cocktails) {
        this.cocktails.addAll(Arrays.asList(cocktails));
    }

    public void addCocktails(List<CocktailEntity> cocktails) {
        if (this.cocktails == null) {
            setCocktails(cocktails);
        } else {
            this.cocktails.addAll(cocktails);
        }
    }

    public List<CocktailEntity> getCocktails() {
        return cocktails;
    }
}
