package com.example.quizapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.QuizResult;
import com.example.quizapp.model.Result;
import com.example.quizapp.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

	@Value("${film.url}")
	private String filmUrl;
	@Value("${book.url}")
	private String bookUrl;

	@Autowired
	RestTemplate restTemp;

	public QuizResult getQuizResult() {
		QuizResult quiz = new QuizResult();
		List<Quiz> result = new ArrayList<>();
		ResponseEntity<String> respMusic = restTemp.exchange(filmUrl, HttpMethod.GET, null, String.class);
		Quiz musicResponse = parseResponse(respMusic);
		result.add(musicResponse);
		ResponseEntity<String> respBook = restTemp.exchange(bookUrl, HttpMethod.GET, null, String.class);
		Quiz bookResponse = parseResponse(respBook);
		result.add(bookResponse);
		quiz.setQuiz(result);
		return quiz;
	}

	private Quiz parseResponse(ResponseEntity<String> resp) {
		if (resp.getStatusCode() == HttpStatus.OK) {
			JSONObject jsonObject = new JSONObject(resp.getBody());
			Integer responseCode = jsonObject.getInt("response_code");
			if (responseCode == 0) {
				return getResponse(jsonObject);
			} else if (responseCode == 1) {
				return null;
			}
		}
		return null;
	}

	private Quiz getResponse(JSONObject jsonObjectServiceResponse) {

		JSONArray jsonArrayResult = jsonObjectServiceResponse.getJSONArray("results");
		Quiz quiz = new Quiz();
		List<Result> resultsList = new ArrayList<>();
		for (int i = 0; i < jsonArrayResult.length(); i++) {
			JSONObject singleQuizJSON = jsonArrayResult.getJSONObject(i);
			Result result = new Result();
			quiz.setCategory(singleQuizJSON.optString("category"));
			result.setType(singleQuizJSON.optString("type"));
			result.setCorrect_answer(singleQuizJSON.optString("type"));
			JSONArray array = singleQuizJSON.getJSONArray("incorrect_answers");
			List<String> incorrect_answers = new ArrayList<>();
			for (int j = 0; j < array.length(); j++) {
				incorrect_answers.add(array.getString(j));
			}
			result.setAll_answers(incorrect_answers);
			result.setDifficulty(singleQuizJSON.optString("difficulty"));
			result.setQuestion(singleQuizJSON.optString("question"));

			resultsList.add(result);
			quiz.setResults(resultsList);
		}

		return quiz;

	}
}
