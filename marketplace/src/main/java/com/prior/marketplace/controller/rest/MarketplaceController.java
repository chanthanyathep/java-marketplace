package com.prior.marketplace.controller.rest;

import com.prior.marketplace.model.MarketplaceModel;
import com.prior.marketplace.model.ResponseModel;
import com.prior.marketplace.model.insert.MarketplaceInsertModel;
import com.prior.marketplace.service.MarketplaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marketplace")
public class MarketplaceController {
    private MarketplaceService marketplaceService;

    public MarketplaceController(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    @PostMapping("")
    public ResponseModel<String> insertItemToMarketplace(@RequestBody MarketplaceInsertModel marketplaceInsertModel){
        return this.marketplaceService.insertItemToMarketplace(marketplaceInsertModel);
    }

    @GetMapping("")
    public ResponseModel<List<MarketplaceModel>> getItemFromMarketplace() {
        return this.marketplaceService.getItemsFromMarketPlace();
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseModel<List<MarketplaceModel>> getItemFromMarketplaceBySellerId(@PathVariable int sellerId) {
        return this.marketplaceService.getItemsFromMarketPlaceBySellId(sellerId);
    }


}
