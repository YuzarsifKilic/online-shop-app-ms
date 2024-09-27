package com.yuzarsif.contextshare.productservice.repository;

import com.yuzarsif.contextshare.productservice.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
}
