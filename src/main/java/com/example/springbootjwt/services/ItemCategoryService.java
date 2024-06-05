package com.example.springbootjwt.services;

import com.example.springbootjwt.entities.ItemCategory;
import com.example.springbootjwt.repo.ItemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;


    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }


    public ItemCategory saveItemCategory(ItemCategory itemCategory) {
        return itemCategoryRepository.save(itemCategory);
    }


    public ItemCategory updateItemCategory(Long id, ItemCategory itemCategory) {
        Optional<ItemCategory> existingItemCategory = itemCategoryRepository.findById(id);
        if (existingItemCategory.isPresent()) {
            ItemCategory updatedItemCategory = existingItemCategory.get();
            updatedItemCategory.setName(itemCategory.getName());
            return itemCategoryRepository.save(updatedItemCategory);
        } else {
            // Handle case where item category is not found
            return null;
        }
    }


    public void deleteItemCategory(Long id) {
        itemCategoryRepository.deleteById(id);
    }


    public Optional<ItemCategory> getItemCategoryById(Long id) {
        return itemCategoryRepository.findById(id);
    }


    public List<ItemCategory> getAllItemCategories() {
        return itemCategoryRepository.findAll();
    }
}
