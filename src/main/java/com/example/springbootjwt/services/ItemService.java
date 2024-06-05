package com.example.springbootjwt.services;

import com.example.springbootjwt.entities.Item;
import com.example.springbootjwt.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item item) {
        Optional<Item> existingItem = itemRepository.findById(id);
        if (existingItem.isPresent()) {
            Item updatedItem = existingItem.get();
            updatedItem.setName(item.getName());

            updatedItem.setPrice(item.getPrice());
            return itemRepository.save(updatedItem);
        } else {
            // Handle case where item is not found
            return null;
        }
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
