package com.poke.boot.services;

import com.poke.boot.dto.GenericTypeDTO;
import com.poke.boot.dto.ItemDTO;
import com.poke.boot.model.Category;
import com.poke.boot.model.Item;
import com.poke.boot.repository.CategoryRepository;
import com.poke.boot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Item> getItems() {
        return new ArrayList<Item>((Collection<? extends Item>) itemRepository.findAll());
    }

    public boolean saveItemFromApi(ItemDTO itemDTO) {
        Category category = categoryRepository.findByName(itemDTO.getCategory().getName());
        if (category == null) {
            category = categoryRepository.save(itemDTO.getCategory());
        }
        Item item = new Item();
        item.setCategory(category);
        item.setCost(itemDTO.getCost());
        if (itemDTO.getEffectEntries().size() > 0) item.setDescription(itemDTO.getEffectEntries().get(0).getEffect());
        item.setNemotecnico(itemDTO.getName());

        if (itemDTO.getNames().size() > 0) {
            for (GenericTypeDTO name: itemDTO.getNames()) {
                if (name.getLanguage().getName().equals("en")) {
                    item.setName(name.getName());
                }
            }
        }
        itemRepository.save(item);

        return true;
    }
}
