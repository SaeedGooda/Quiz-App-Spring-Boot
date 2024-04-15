package com.saeed.quizapp.question;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionService {
    final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // GET All Questions
    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
    }

    // GET Questions By Category
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        return new ResponseEntity<>(questionRepository.findByCategory(category),HttpStatus.OK);
    }

    // ADD New Question
    public ResponseEntity<Question> addNewQuestion(Question question) {
        questionRepository.save(question);
        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }

    // DELETE Question
    public ResponseEntity<Void> deleteQuestion(int id) {
        boolean exists = questionRepository.existsById(id);
        if(!exists){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        questionRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // UPDATE Question
    @Transactional
    public ResponseEntity<Question> updateQuestion(int id, String rightAnswer, String difficultyLevel, String category) {
        Optional<Question> question = questionRepository.findById(id);
        // Throw An Exception if the question doesn't exist
        if(question.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Update Right Answer
        if (rightAnswer != null &&
                !rightAnswer.isEmpty() &&
                !Objects.equals(question.get().getRightAnswer(), rightAnswer)
        ) {
            question.get().setRightAnswer(rightAnswer);
        }
        // Update Difficulty Level
        if (difficultyLevel != null &&
                !difficultyLevel.isEmpty() &&
                !Objects.equals(question.get().getDifficultyLevel(), difficultyLevel)
        ) {
            question.get().setDifficultyLevel(difficultyLevel);
        }
        // Update Category
        if (category != null &&
                !category.isEmpty() &&
                !Objects.equals(question.get().getCategory(), category)
        ) {
            question.get().setCategory(category);
        }
        return new ResponseEntity<>(question.get(), HttpStatus.OK);
    }
}
