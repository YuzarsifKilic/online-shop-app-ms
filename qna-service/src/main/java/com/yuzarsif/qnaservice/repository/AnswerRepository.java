package com.yuzarsif.qnaservice.repository;

import com.yuzarsif.qnaservice.model.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<Answer, String> {
}
