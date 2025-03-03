package com.exam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long>{
	
	public List<Quiz> findBycategory(Category category);
	
	
	// for active quiz only
	public List<Quiz> findByActive(Boolean b);
	
	// i want active quiz along with category
	public List<Quiz> findByCategoryAndActive(Category c,Boolean b);

}
