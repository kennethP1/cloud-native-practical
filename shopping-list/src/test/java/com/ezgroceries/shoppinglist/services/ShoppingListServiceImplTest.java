package com.ezgroceries.shoppinglist.services;

import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.entities.ShoppingListEntity;
import com.ezgroceries.shoppinglist.model.ShoppingList;
import com.ezgroceries.shoppinglist.repositories.ShoppingListRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class ShoppingListServiceImplTest {

    private ShoppingListService service;
    private ShoppingListRepository repository = mock(ShoppingListRepository.class);
    private CocktailService cocktailService = mock(CocktailService.class);

    @BeforeEach
    public void init() {
        service = new ShoppingListServiceImpl(repository, cocktailService);
    }

    @Test
    public void testCreateShoppingList() {
        when(repository.save(any(ShoppingListEntity.class))).thenAnswer(a -> a.getArgument(0));
        ArgumentCaptor<ShoppingListEntity> argumentCaptor = ArgumentCaptor.forClass(ShoppingListEntity.class);

        ShoppingList created = service.createShoppingList("cool-list");
        assertThat("Name has been set", created.getName(), equalTo("cool-list"));

        verify(repository).save(argumentCaptor.capture());

        assertThat("Random id is created", created.getShoppingListId(), equalTo(argumentCaptor.getValue().getId()));
    }

    @Test
    public void testGet() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(of(new ShoppingListEntity("cool-list")));
        ShoppingList shoppingList = service.get(id.toString());
        assertThat("Shopping list exists", shoppingList, notNullValue());
        assertThat("Name has been set", shoppingList.getName(), equalTo("cool-list"));
    }

    @Test
    public void testAddCocktails() {
        UUID id = UUID.randomUUID();
        UUID cocktail1 = UUID.randomUUID();
        UUID cocktail2 = UUID.randomUUID();
        List<String> cocktails = Arrays.asList(cocktail1.toString(), cocktail2.toString());
        ShoppingListEntity entity = new ShoppingListEntity("cool-drink");
        when(cocktailService.getAllById(cocktails)).thenReturn(
                Arrays.asList(
                        new CocktailEntity(cocktail1, "id-drink-1", "drink-1"),
                        new CocktailEntity(cocktail2, "id-drink-2", "drink-2")));
        when(repository.findById(id)).thenReturn(of(entity));
        service.addCocktails(id.toString(), cocktails);
        verify(repository).save(entity);
    }

    @Test
    public void testGetShoppingLists() {
        when(repository.findAll()).thenReturn(Arrays.asList(new ShoppingListEntity("cool-drink-1"), new ShoppingListEntity("cool-drink-2")));
        List<ShoppingList> shoppingLists = service.getAllShoppingLists();
        assertThat("Shopping lists length is 2", shoppingLists.size(), equalTo(2));
        assertThat("Shopping list 1 is cool-drink-1", shoppingLists.get(0).getName(), equalTo("cool-drink-1"));
        assertThat("Shopping list 2 is cool-drink-2", shoppingLists.get(1).getName(), equalTo("cool-drink-2"));
    }
}
