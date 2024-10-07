package com.yuzarsif.contextshare.productservice.service;

import com.yuzarsif.contextshare.productservice.dto.CampaignDto;
import com.yuzarsif.contextshare.productservice.dto.CreateCampaignRequest;
import com.yuzarsif.contextshare.productservice.exception.EntityNotFoundException;
import com.yuzarsif.contextshare.productservice.model.Campaign;
import com.yuzarsif.contextshare.productservice.model.Category;
import com.yuzarsif.contextshare.productservice.model.Photo;
import com.yuzarsif.contextshare.productservice.model.Product;
import com.yuzarsif.contextshare.productservice.repository.CampaignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CampaignServiceTest {

    @InjectMocks
    private CampaignService campaignService;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCampaign_WhenRequestIsValid_ShouldReturnCampaignDto() {

        when(productService.getProductsById(List.of(1L))).thenReturn(List.of(mockProduct()));

        when(campaignRepository.save(any(Campaign.class))).thenReturn(mockCampaign());


        CampaignDto response = campaignService.createCampaign(mockCreateCampaignRequest());


        assertNotNull(response);
        assertEquals("campaign_name", response.name());
        assertEquals("campaign_description", response.description());
        assertEquals(LocalDateTime.parse("2024-10-05T13:48"), response.startDate());
        assertEquals(LocalDateTime.parse("2024-10-05T13:48"), response.endDate());
        assertEquals("product_name", response.products().get(0).name());
        assertEquals("product_description", response.products().get(0).description());
        assertEquals(1000.0, response.products().get(0).price());
        assertEquals("main_image_url", response.products().get(0).mainImageUrl());
        assertEquals("category_name", response.products().get(0).category().name());
    }

    @Test
    public void testGetCampaigns_ShouldReturnCampaignDtoList() {

        when(campaignRepository.findAll()).thenReturn(List.of(mockCampaign()));


        List<CampaignDto> response = campaignService.getCampaigns();


        assertEquals(List.of(CampaignDto.convert(mockCampaign())), response);
    }

    @Test
    public void testFindCampaignById_WhenCampaignExists_ShouldReturnCampaignDto() {

        when(campaignRepository.findById(1)).thenReturn(Optional.of(mockCampaign()));


        CampaignDto response = campaignService.findCampaignById(1);


        assertNotNull(response);
        assertEquals("campaign_name", response.name());
        assertEquals("campaign_description", response.description());
        assertEquals(LocalDateTime.parse("2024-10-05T13:48"), response.startDate());
        assertEquals(LocalDateTime.parse("2024-10-05T13:48"), response.endDate());
        assertEquals("product_name", response.products().get(0).name());
        assertEquals("product_description", response.products().get(0).description());
        assertEquals(1000.0, response.products().get(0).price());
    }

    @Test
    public void testFindCampaignById_WhenCampaignDoesNotExist_ShouldThrowEntityNotFoundException() {

        when(campaignRepository.findById(1)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> campaignService.findCampaignById(1));

        verify(campaignRepository, times(1)).findById(1);

        assertEquals("Campaign not found with id: 1", exception.getMessage());

    }

    private CreateCampaignRequest mockCreateCampaignRequest() {
        return new CreateCampaignRequest("campaign_name", "campaign_description", "2024-10-05 13:48", "2024-10-05 13:48", List.of(1L));
    }

    private Campaign mockCampaign() {
        return Campaign
                .builder()
                .name("campaign_name")
                .description("campaign_description")
                .startDate(LocalDateTime.parse("2024-10-05T13:48"))
                .endDate(LocalDateTime.parse("2024-10-05T13:48"))
                .products(Set.of(mockProduct()))
                .build();
    }

    private Product mockProduct() {
        return Product
                .builder()
                .id(1L)
                .name("product_name")
                .description("product_description")
                .price(1000.0)
                .mainImageUrl("main_image_url")
                .category(mockCategory())
                .photos(Set.of(mockPhoto()))
                .build();
    }

    private Category mockCategory() {
        return Category
                .builder()
                .id(1)
                .name("category_name")
                .build();
    }

    private Photo mockPhoto() {
        return Photo
                .builder()
                .url("photo_url")
                .order(1)
                .build();
    }


}
