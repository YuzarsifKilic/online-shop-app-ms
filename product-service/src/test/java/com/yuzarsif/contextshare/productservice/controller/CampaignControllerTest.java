package com.yuzarsif.contextshare.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuzarsif.contextshare.productservice.dto.*;
import com.yuzarsif.contextshare.productservice.exception.EntityNotFoundException;
import com.yuzarsif.contextshare.productservice.service.CampaignService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CampaignService campaignService;

    @Test
    void testCreateCampaign_WhenRequestIsValid_ShouldReturnCampaignDto() throws Exception {

        when(campaignService.createCampaign(any(CreateCampaignRequest.class))).thenReturn(mockCampaignDto());

        mockMvc.perform(post("/api/v1/campaigns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateCampaignRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("campaign_name"))
                .andExpect(jsonPath("$.description").value("campaign_description"))
                .andExpect(jsonPath("$.startDate").value("2024-10-05T13:48:00"))
                .andExpect(jsonPath("$.endDate").value("2024-10-05T13:48:00"));
    }

    @Test
    void testCreateCampaign_WhenRequestIsInvalid_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/campaigns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockInvalidCreateCampaignRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetAllCampaigns_ShouldReturnCampaignDtoList() throws Exception {

        when(campaignService.getCampaigns()).thenReturn(List.of(mockCampaignDto()));

        mockMvc.perform(get("/api/v1/campaigns")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("campaign_name"))
                .andExpect(jsonPath("$[0].description").value("campaign_description"))
                .andExpect(jsonPath("$[0].startDate").value("2024-10-05T13:48:00"))
                .andExpect(jsonPath("$[0].endDate").value("2024-10-05T13:48:00"));
    }

    @Test
    void testGetCampaign_WhenCampaignExists_ShouldReturnCampaignDto() throws Exception {

        when(campaignService.findCampaignById(anyInt())).thenReturn(mockCampaignDto());

        mockMvc.perform(get("/api/v1/campaigns/{campaignId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("campaign_name"))
                .andExpect(jsonPath("$.description").value("campaign_description"))
                .andExpect(jsonPath("$.startDate").value("2024-10-05T13:48:00"))
                .andExpect(jsonPath("$.endDate").value("2024-10-05T13:48:00"));
    }

    @Test
    void testGetCampaign_WhenCampaignDoesNotExist_ShouldReturnEntityNotFoundException() throws Exception {

        when(campaignService.findCampaignById(anyInt())).thenThrow(new EntityNotFoundException("Campaign not found with id"));

        mockMvc.perform(get("/api/v1/campaigns/{campaignId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("NOT_FOUND_ERROR"))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    private CreateCampaignRequest mockCreateCampaignRequest() {
        return new CreateCampaignRequest("campaign_name", "campaign_description", "2024-10-05 13:48", "2024-10-05 13:48", List.of(1L));
    }

    private CampaignDto mockInvalidCreateCampaignRequest() {
        return new CampaignDto(1, "campaign_name", "campaign_description", LocalDateTime.parse("2024-10-05T13:48"), LocalDateTime.parse("2024-10-05T13:48"), List.of());
    }

    private CampaignDto mockCampaignDto() {
        return new CampaignDto(1, "campaign_name", "campaign_description", LocalDateTime.parse("2024-10-05T13:48"), LocalDateTime.parse("2024-10-05T13:48"), List.of(mockProductDto()));
    }

    private ProductDto mockProductDto() {
        return new ProductDto(1L, "product_name", "product_description", 1000.0, "main_image_url", mockCategoryDto(), List.of(mockPhotoDto()));
    }

    private CategoryDto mockCategoryDto() {
        return new CategoryDto(1, "category_name");
    }

    private PhotoDto mockPhotoDto() {
        return new PhotoDto(1L, "photo_url", 1);
    }
}
