package com.ezgroceries.shoppinglist.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ezgroceries.shoppinglist.database.CocktailDBClient;
import com.ezgroceries.shoppinglist.database.CocktailDBResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ComponentScan("com.ezgroceries.shoppinglist.controller")
public class CocktailControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CocktailDBClient cocktailDBClient;


    @Test
    public void getAccountsTest() throws Exception {

        given(cocktailDBClient.searchCocktails("TEST")).willReturn(giveMockedCocktails());

        mockMvc
                .perform(get("/cocktails")
                        .param("search", "TEST")
                        .accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Margerita"))
                .andExpect(jsonPath("$[0].glass").value("Cocktail glass"))
                .andExpect(jsonPath("$[0].image").value("https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg"))
                .andExpect(jsonPath("$[0].ingredients.length()").value(4))
                .andExpect(jsonPath("$[0].ingredients[0]").value("Tequila"))
                .andExpect(jsonPath("$[0].ingredients[1]").value("Triple sec"))
                .andExpect(jsonPath("$[0].ingredients[2]").value("Lime juice"))
                .andExpect(jsonPath("$[0].ingredients[3]").value("Salt"))
                .andExpect(jsonPath("$[1].name").value("Blue Margerita"))
                .andExpect(jsonPath("$[1].glass").value("Cocktail glass"))
                .andExpect(jsonPath("$[1].image").value("https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg"))
                .andExpect(jsonPath("$[1].ingredients.length()").value(4))
                .andExpect(jsonPath("$[1].ingredients[0]").value("Tequila"))
                .andExpect(jsonPath("$[1].ingredients[1]").value("Blue Curacao"))
                .andExpect(jsonPath("$[1].ingredients[2]").value("Lime juice"))
                .andExpect(jsonPath("$[1].ingredients[3]").value("Salt"));

        verify(cocktailDBClient).searchCocktails("TEST");


    }


    private CocktailDBResponse giveMockedCocktails() {
        List<CocktailDBResponse.DrinkResource> drinks = new ArrayList<>();
        CocktailDBResponse.DrinkResource drinkResource = new CocktailDBResponse.DrinkResource();
        drinkResource.setStrDrink("Margerita");
        drinkResource.setStrGlass("Cocktail glass");
        drinkResource.setStrInstructions("Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..");
        drinkResource.setStrDrinkThumb("https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg");
        drinkResource.setStrIngredient1("Tequila");
        drinkResource.setStrIngredient2("Triple sec");
        drinkResource.setStrIngredient3("Lime juice");
        drinkResource.setStrIngredient4("Salt");
        drinks.add(drinkResource);
        drinkResource = new CocktailDBResponse.DrinkResource();
        drinkResource.setStrDrink("Blue Margerita");
        drinkResource.setStrGlass("Cocktail glass");
        drinkResource.setStrInstructions("Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..");
        drinkResource.setStrDrinkThumb("https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg");
        drinkResource.setStrIngredient1("Tequila");
        drinkResource.setStrIngredient2("Blue Curacao");
        drinkResource.setStrIngredient3("Lime juice");
        drinkResource.setStrIngredient4("Salt");
        drinks.add(drinkResource);
        CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
        cocktailDBResponse.setDrinks(drinks);
        return cocktailDBResponse;
    }


}
