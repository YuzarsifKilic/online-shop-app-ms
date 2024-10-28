package com.yuzarsif.qnaservice.controller;

import com.yuzarsif.qnaservice.dto.CreateQuestionRequest;
import com.yuzarsif.qnaservice.dto.QuestionDto;
import com.yuzarsif.qnaservice.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody CreateQuestionRequest request) {
        return ResponseEntity.ok(questionService.createQuestion(request));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<QuestionDto>> findQuestionsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(questionService.findQuestionsByProductId(productId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<QuestionDto>> findQuestionsByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(questionService.findQuestionsByCustomerId(customerId));
    }
}
