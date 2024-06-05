package com.example.springbootjwt;
import com.example.springbootjwt.controllers.ItemCategoryController;
import com.example.springbootjwt.entities.ItemCategory;
import com.example.springbootjwt.services.ItemCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ItemCategoryControllerTest {

    @Mock
    private ItemCategoryService itemCategoryService;

    @InjectMocks
    private ItemCategoryController itemCategoryController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(itemCategoryController).build();
    }

    @Test
    public void testCreateItemCategory() {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setName("Test Category");

        when(itemCategoryService.saveItemCategory(any(ItemCategory.class))).thenReturn(itemCategory);

        ResponseEntity<ItemCategory> response = itemCategoryController.createItemCategory(itemCategory);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(itemCategory, response.getBody());
        verify(itemCategoryService, times(1)).saveItemCategory(itemCategory);
    }

    @Test
    public void testUpdateItemCategory() {
        Long id = 1L;
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setId(id);
        itemCategory.setName("Updated Category");

        when(itemCategoryService.updateItemCategory(eq(id), any(ItemCategory.class))).thenReturn(itemCategory);

        ResponseEntity<ItemCategory> response = itemCategoryController.updateItemCategory(id, itemCategory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemCategory, response.getBody());
        verify(itemCategoryService, times(1)).updateItemCategory(eq(id), any(ItemCategory.class));
    }

    @Test
    public void testUpdateItemCategoryNotFound() {
        Long id = 1L;
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setId(id);
        itemCategory.setName("Updated Category");

        when(itemCategoryService.updateItemCategory(eq(id), any(ItemCategory.class))).thenReturn(null);

        ResponseEntity<ItemCategory> response = itemCategoryController.updateItemCategory(id, itemCategory);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(itemCategoryService, times(1)).updateItemCategory(eq(id), any(ItemCategory.class));
    }

    @Test
    public void testDeleteItemCategory() {
        Long id = 1L;

        doNothing().when(itemCategoryService).deleteItemCategory(id);

        ResponseEntity<Void> response = itemCategoryController.deleteItemCategory(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(itemCategoryService, times(1)).deleteItemCategory(id);
    }

    @Test
    public void testGetItemCategoryById() {
        Long id = 1L;
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setId(id);
        itemCategory.setName("Test Category");

        when(itemCategoryService.getItemCategoryById(id)).thenReturn(Optional.of(itemCategory));

        ResponseEntity<ItemCategory> response = itemCategoryController.getItemCategoryById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemCategory, response.getBody());
        verify(itemCategoryService, times(1)).getItemCategoryById(id);
    }

    @Test
    public void testGetItemCategoryByIdNotFound() {
        Long id = 1L;

        when(itemCategoryService.getItemCategoryById(id)).thenReturn(Optional.empty());

        ResponseEntity<ItemCategory> response = itemCategoryController.getItemCategoryById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(itemCategoryService, times(1)).getItemCategoryById(id);
    }

    @Test
    public void testGetAllItemCategories() {
        List<ItemCategory> itemCategories = Arrays.asList(
                new ItemCategory(), new ItemCategory()
        );

        when(itemCategoryService.getAllItemCategories()).thenReturn(itemCategories);

        ResponseEntity<List<ItemCategory>> response = itemCategoryController.getAllItemCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemCategories, response.getBody());
        verify(itemCategoryService, times(1)).getAllItemCategories();
    }
}
