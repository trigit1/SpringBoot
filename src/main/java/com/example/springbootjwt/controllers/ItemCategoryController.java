package com.example.springbootjwt.controllers;

import com.example.springbootjwt.entities.ItemCategory;
import com.example.springbootjwt.services.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item-categories")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    @Autowired
    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @PostMapping
    public ResponseEntity<ItemCategory> createItemCategory(@RequestBody ItemCategory itemCategory) {
        ItemCategory savedItemCategory = itemCategoryService.saveItemCategory(itemCategory);
        return new ResponseEntity<>(savedItemCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCategory> updateItemCategory(@PathVariable Long id, @RequestBody ItemCategory itemCategory) {
        ItemCategory updatedItemCategory = itemCategoryService.updateItemCategory(id, itemCategory);
        if (updatedItemCategory != null) {
            return new ResponseEntity<>(updatedItemCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemCategory(@PathVariable Long id) {
        itemCategoryService.deleteItemCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ItemCategory> getItemCategoryById(@PathVariable Long id) {
        Optional<ItemCategory> itemCategory = itemCategoryService.getItemCategoryById(id);
        return itemCategory.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/view")
    public ResponseEntity<List<ItemCategory>> getAllItemCategories() {
        List<ItemCategory> itemCategories = itemCategoryService.getAllItemCategories();
        return new ResponseEntity<>(itemCategories, HttpStatus.OK);
    }
}
