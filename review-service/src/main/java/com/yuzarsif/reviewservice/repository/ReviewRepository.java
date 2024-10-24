package com.yuzarsif.reviewservice.repository;

import com.yuzarsif.reviewservice.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByProductId(Long productId);
}
