package com.saeed.quizapp.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // GET All Questions
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    // GET Questions By Category
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    // ADD New Question
    @PostMapping
    public ResponseEntity<Question> addNewQuestion(@RequestBody Question question){
        return questionService.addNewQuestion(question);
    }

    // UPDATE Question
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateStudent(
            @PathVariable int id,
            @RequestParam(required = false) String rightAnswer,
            @RequestParam(required = false) String difficultyLevel,
            @RequestParam(required = false) String category
    ){
        return questionService.updateQuestion(id, rightAnswer, difficultyLevel, category);
    }

    // DELETE Question
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id){
        return questionService.deleteQuestion(id);
    }
}
