package com.example.quizapp.model;

import java.util.List;

import lombok.Data;

@Data
public class Quiz {
	private String category;
	private List<Result> results;
	
}
