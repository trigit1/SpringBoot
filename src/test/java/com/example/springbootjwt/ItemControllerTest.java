package com.example.springbootjwt;

import com.example.springbootjwt.controllers.ItemController;
import com.example.springbootjwt.entities.Item;
import com.example.springbootjwt.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    public void testCreateItem() {
        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(100));
        item.setCategoryId(1L);

        when(itemService.saveItem(any(Item.class))).thenReturn(item);

        ResponseEntity<Item> response = itemController.createItem(item);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(item, response.getBody());
        verify(itemService, times(1)).saveItem(item);
    }

    @Test
    public void testUpdateItem() {
        Long id = 1L;
        Item item = new Item();
        item.setId(id);
        item.setName("Updated Item");
        item.setPrice(BigDecimal.valueOf(150));
        item.setCategoryId(1L);

        when(itemService.updateItem(eq(id), any(Item.class))).thenReturn(item);

        ResponseEntity<Item> response = itemController.updateItem(id, item);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(item, response.getBody());
        verify(itemService, times(1)).updateItem(eq(id), any(Item.class));
    }

    @Test
    public void testUpdateItemNotFound() {
        Long id = 1L;
        Item item = new Item();
        item.setId(id);
        item.setName("Updated Item");
        item.setPrice(BigDecimal.valueOf(150));
        item.setCategoryId(1L);

        when(itemService.updateItem(eq(id), any(Item.class))).thenReturn(null);

        ResponseEntity<Item> response = itemController.updateItem(id, item);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(itemService, times(1)).updateItem(eq(id), any(Item.class));
    }

    @Test
    public void testDeleteItem() {
        Long id = 1L;

        doNothing().when(itemService).deleteItem(id);

        ResponseEntity<Void> response = itemController.deleteItem(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(itemService, times(1)).deleteItem(id);
    }

    @Test
    public void testGetItemById() {
        Long id = 1L;
        Item item = new Item();
        item.setId(id);
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(100));
        item.setCategoryId(1L);

        when(itemService.getItemById(id)).thenReturn(Optional.of(item));

        ResponseEntity<Item> response = itemController.getItemById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(item, response.getBody());
        verify(itemService, times(1)).getItemById(id);
    }

    @Test
    public void testGetItemByIdNotFound() {
        Long id = 1L;

        when(itemService.getItemById(id)).thenReturn(Optional.empty());

        ResponseEntity<Item> response = itemController.getItemById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(itemService, times(1)).getItemById(id);
    }

    @Test
    public void testGetAllItems() {
        List<Item> items = Arrays.asList(
                new Item(), new Item()
        );

        when(itemService.getAllItems()).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getAllItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(items, response.getBody());
        verify(itemService, times(1)).getAllItems();
    }
}
