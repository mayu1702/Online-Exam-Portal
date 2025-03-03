package com.exam.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;

import io.jsonwebtoken.lang.Collections;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuizService quizService;

	// add question
	@PostMapping("/")
	public ResponseEntity<Question> add(@RequestBody Question question) {
		return ResponseEntity.ok(this.questionService.addQuestion(question));
	}

	// update the question
	@PutMapping("/")
	public ResponseEntity<Question> update(@RequestBody Question question) {
		return ResponseEntity.ok(this.questionService.updateQuestion(question));
	}

	// get all questions of any quiz
	@GetMapping("/quiz/{qId}")
	public ResponseEntity<?> getQuestionOfQuiz(@PathVariable("qId") Long qId) {
//		Quiz quiz = new Quiz();
//		quiz.setqId(qId);
//		Set<Question> questionOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
//		return ResponseEntity.ok(questionOfQuiz);

		Quiz quiz = this.quizService.getQuiz(qId);
		Set<Question> questions = quiz.getQuestions();
		List <Question> list = new ArrayList(questions);
		if (list.size() > Integer.parseInt(quiz.getNoOfQuestions())) {
			list = list.subList(0, Integer.parseInt(quiz.getNoOfQuestions() + 1));
		}
		
		list.forEach((q)->{
			q.setAnswer("");
		});
		
		
		java.util.Collections.shuffle(list); // to shuffle the elements
		return ResponseEntity.ok(list);

	}

	@GetMapping("/quiz/all/{qId}")
	public ResponseEntity<?> getQuestionOfQuizAdmin(@PathVariable("qId") Long qId) {
		Quiz quiz = new Quiz();
		quiz.setqId(qId);
		Set<Question> questionOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
		return ResponseEntity.ok(questionOfQuiz);

//		return ResponseEntity.ok(list);

	}

	// get single question
	@GetMapping("/{quesId}")
	public Question get(@PathVariable("quesId") Long quesId) {
		return this.questionService.getQuestion(quesId);
	}

	// delete question
	@DeleteMapping("/{quesId}")
	public void delete(@PathVariable("quesId") Long quesId) {
		this.questionService.deleteQuestion(quesId);
	}

	// evaluating the quiz questions
	@PostMapping("/eval-quiz")
	public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions) {
		
		    double marksGot = 0;
		    int correctAnswers = 0;
		    int attempted = 0;

		for(Question q : questions) {
			// single questions
			Question question = this.questionService.get(q.getQuesId());
			if(question.getAnswer().equals(q.getGivenAnswer()))
			{	
				//correct
				correctAnswers++;
				
				double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) /questions.size();		  
				//  this.questions[0].quiz.maxMarks / this.questions.length;
			    marksGot += marksSingle;
			}
			if(q.getGivenAnswer() != null) {
				attempted++;
			}
		};
		
		Map<String, Object> map = Map.of("marksGot",marksGot,"correctAnswers",correctAnswers,"attempted",attempted);
		return ResponseEntity.ok(map);
	}

}
