package com.yuzarsif.contextshare.productservice.controller;

import com.yuzarsif.contextshare.productservice.dto.CampaignDto;
import com.yuzarsif.contextshare.productservice.dto.CreateCampaignRequest;
import com.yuzarsif.contextshare.productservice.model.Campaign;
import com.yuzarsif.contextshare.productservice.service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping
    public void createCampaign(@RequestBody @Valid CreateCampaignRequest request) {
        campaignService.createCampaign(request);
    }

    @GetMapping
    public ResponseEntity<List<CampaignDto>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.getCampaigns());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignDto> getCampaign(@PathVariable Integer id) {
        return ResponseEntity.ok(campaignService.findCampaignById(id));
    }
}
