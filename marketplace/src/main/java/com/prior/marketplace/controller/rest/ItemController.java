package com.prior.marketplace.controller.rest;

import com.prior.marketplace.model.ItemModel;
import com.prior.marketplace.model.ResponseModel;
import org.springframework.web.bind.annotation.*;
import com.prior.marketplace.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/insertItem")
    public ResponseModel<Integer> insertItem(@RequestBody ItemModel itemModel){
        return this.itemService.insertItem(itemModel);
    }

    @GetMapping("/getItem/{itemId}")
    public ResponseModel<ItemModel> getItem(@PathVariable int itemId){
        return this.itemService.getItem(itemId);
    }

    @GetMapping("/getAllItem")
    public ResponseModel<List<ItemModel>> getAllItem(){
        return this.itemService.getItemList();
    }
}
