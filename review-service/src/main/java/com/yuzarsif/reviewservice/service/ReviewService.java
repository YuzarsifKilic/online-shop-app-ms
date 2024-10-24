package com.yuzarsif.reviewservice.service;

import com.yuzarsif.reviewservice.client.CustomerResponse;
import com.yuzarsif.reviewservice.client.OrderClient;
import com.yuzarsif.reviewservice.client.UserClient;
import com.yuzarsif.reviewservice.dto.CreateReviewRequest;
import com.yuzarsif.reviewservice.dto.ReviewDto;
import com.yuzarsif.reviewservice.dto.ReviewResponse;
import com.yuzarsif.reviewservice.exception.EntityNotFoundException;
import com.yuzarsif.reviewservice.model.Review;
import com.yuzarsif.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderClient orderClient;
    private final UserClient userClient;

    public ReviewDto createReview(CreateReviewRequest request) {
        Boolean checkOrderExists = orderClient.checkOrderExists(request.customerId(), request.productId());

        if (!checkOrderExists) {
            throw new EntityNotFoundException("Order not found");
        }

        Review review = Review
                .builder()
                .productId(request.productId())
                .customerId(request.customerId())
                .comment(request.comment())
                .rating(request.rating())
                .build();

        return ReviewDto.convert(reviewRepository.save(review));
    }

    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        List<String> customerIds = reviews
                .stream()
                .map(Review::getCustomerId)
                .toList();

        List<CustomerResponse> customers = userClient.getCustomerList(customerIds);

        List<ReviewResponse> response = new ArrayList<>();

        IntStream.range(0, reviews.size()).forEach(i -> {
            ReviewResponse reviewResponse = new ReviewResponse(
                    reviews.get(i).getId(),
                    reviews.get(i).getCustomerId(),
                    customers.get(i).firstName() + " " + customers.get(i).lastName(),
                    reviews.get(i).getProductId(),
                    reviews.get(i).getComment(),
                    reviews.get(i).getRating());

            response.add(reviewResponse);
        });

        return response;
    }
}
