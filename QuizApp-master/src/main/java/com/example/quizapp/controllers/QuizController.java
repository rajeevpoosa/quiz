package com.example.quizapp.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.model.QuizResult;
import com.example.quizapp.service.QuizService;

@RestController
public class QuizController {

	private Logger logger = LoggerFactory.getLogger(QuizController.class);

	@Autowired
	private QuizService quizService;

	@GetMapping(value = "coding/exercise/quiz")
	@ResponseStatus(HttpStatus.OK)
	public QuizResult get() throws IOException {
		logger.info("API invoked : coding/exercise/quiz");
		return quizService.getQuizResult();
	}
}