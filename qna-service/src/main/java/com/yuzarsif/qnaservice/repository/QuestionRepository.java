package com.yuzarsif.qnaservice.repository;

import com.yuzarsif.qnaservice.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {

    List<Question> findByProductId(Long productId);

    List<Question> findByCustomerId(String customerId);
}
