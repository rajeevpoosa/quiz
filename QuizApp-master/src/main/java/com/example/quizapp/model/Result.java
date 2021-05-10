package com.example.quizapp.model;

import java.util.List;

import lombok.Data;

@Data
public class Result {
	private String type;
	private String difficulty;
	private String question;
	private List<String> all_answers;
	private String correct_answer;
}
