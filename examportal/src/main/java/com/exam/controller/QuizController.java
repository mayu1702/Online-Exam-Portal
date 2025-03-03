package com.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.service.QuizService;

@RestController
@CrossOrigin("*")
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	// add quiz
	@PostMapping("/")
	public ResponseEntity<Quiz> add(@RequestBody Quiz quiz) {
		return ResponseEntity.ok(this.quizService.addQuiz(quiz));
	}

	// update quiz
	@PutMapping("/")
	public ResponseEntity<Quiz> update(@RequestBody Quiz quiz) {
		return ResponseEntity.ok(this.quizService.updateQuiz(quiz));
	}

	// get quiz
	@GetMapping("/")
	public ResponseEntity<?> quizes() {
		return ResponseEntity.ok(this.quizService.getQuizes());
	}

	// get single quiz
	@GetMapping("/{qId}")
	public Quiz quiz(@PathVariable("qId") Long qId) {
		return this.quizService.getQuiz(qId);

	}

	// delete quiz
	@DeleteMapping("/{qId}")
	public void delete(@PathVariable("qId") Long qId) {
		this.quizService.deleteQuiz(qId);
	}

	@GetMapping("/category/{cid}")
	public List<Quiz> getQuizesofCategory(@PathVariable("cid") Long cid) {
		Category category = new Category();
		category.setCid(cid);
		return this.quizService.getQuizesOfCategory(category);

	}

	// get active quizes
	@GetMapping("/active")
	public List<Quiz> getActiveQuizes() {
		return this.quizService.getActiveQuizes();
	}

	// get active quizes of category
	@GetMapping("/category/active/{cid}")
	public List<Quiz> getActiveQuizes(@PathVariable("cid") Long cid) {
		Category category = new Category();
		category.setCid(cid);
		return this.quizService.getActiveQuizesOfCategory(category);
	}

}
