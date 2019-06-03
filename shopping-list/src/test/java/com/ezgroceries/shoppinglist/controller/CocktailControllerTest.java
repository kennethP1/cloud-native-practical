package com.ezgroceries.shoppinglist.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ezgroceries.shoppinglist.services.CocktailService;
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


import java.util.List;
import java.util.Arrays;
import java.util.UUID;
import com.ezgroceries.shoppinglist.model.CocktailResource;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ComponentScan("com.ezgroceries.shoppinglist.controller")
public class CocktailControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CocktailService cocktailService;


    @Test
    public void getAccountsTest() throws Exception {

        given(cocktailService.searchCocktails("TEST")).willReturn(giveCocktails());

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

        verify(cocktailService).searchCocktails("TEST");


    }


    private List<CocktailResource> giveCocktails() {
               return Arrays.asList(
                new CocktailResource(UUID.randomUUID(), "Margerita", "Cocktail glass", "",
                        "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg", Arrays.asList(
                        "Tequila", "Triple sec", "Lime juice", "Salt"
                )),
                new CocktailResource(UUID.randomUUID(), "Blue Margerita", "Cocktail glass", "",
                        "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg", Arrays.asList(
                        "Tequila", "Blue Curacao", "Lime juice", "Salt"
                )));

    }


}
