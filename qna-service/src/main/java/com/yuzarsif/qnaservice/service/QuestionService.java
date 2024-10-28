package com.yuzarsif.qnaservice.service;

import com.yuzarsif.qnaservice.client.ProductClient;
import com.yuzarsif.qnaservice.client.UserClient;
import com.yuzarsif.qnaservice.dto.CreateQuestionRequest;
import com.yuzarsif.qnaservice.dto.QuestionDto;
import com.yuzarsif.qnaservice.exception.EntityNotFoundException;
import com.yuzarsif.qnaservice.model.Question;
import com.yuzarsif.qnaservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserClient userClient;
    private final ProductClient productClient;

    public QuestionDto createQuestion(CreateQuestionRequest request) {
        Boolean checkUserExists = userClient.checkUserExists(request.customerId());

        if (!checkUserExists) {
            throw new EntityNotFoundException("Customer not found by id: " + request.customerId());
        }

        Boolean checkProductExists = productClient.checkProductExists(request.productId());

        if (!checkProductExists) {
            throw new EntityNotFoundException("Product not found by id: " + request.productId());
        }

        Question question = Question
                .builder()
                .customerId(request.customerId())
                .productId(request.productId())
                .question(request.question())
                .createdAt(LocalDateTime.now())
                .build();

        return QuestionDto.convert(questionRepository.save(question));
    }

    public List<QuestionDto> findQuestionsByProductId(Long productId) {
        return questionRepository
                .findByProductId(productId)
                .stream()
                .map(QuestionDto::convert)
                .toList();
    }

    public List<QuestionDto> findQuestionsByCustomerId(String customerId) {
        return questionRepository
                .findByCustomerId(customerId)
                .stream()
                .map(QuestionDto::convert)
                .toList();
    }
}
