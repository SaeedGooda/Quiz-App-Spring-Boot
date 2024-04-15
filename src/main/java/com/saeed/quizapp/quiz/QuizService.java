package com.saeed.quizapp.quiz;

import com.saeed.quizapp.question.Question;
import com.saeed.quizapp.question.QuestionRepository;
import com.saeed.quizapp.question.QuestionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    final QuizRepository quizRepository;
    final QuestionRepository questionRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    // CREATE Quiz
    public ResponseEntity<Quiz> createQuiz(String category, int numberOfQuestions, String title) {
        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numberOfQuestions);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }

    // GET Quiz Questions
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if(quiz.isPresent()){
            List<Question> questions = quiz.get().getQuestions();
            List<QuestionWrapper> questionsWrappers = new ArrayList<>();
            for(Question q: questions){
                questionsWrappers.add(new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(),
                        q.getOption2(), q.getOption3(), q.getOption4()));
            }
            return new ResponseEntity<>(questionsWrappers, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    // SUBMIT Quiz Results and GET the Score
    public ResponseEntity<Integer> submitQuiz(int id, List<Response> responses) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if(quiz.isPresent()){
            List<Question> questions = quiz.get().getQuestions();
            int result = 0;
            for(int i = 0 ; i < responses.size(); i++){
                if(responses.get(i).getResponse().equals(questions.get(i).getRightAnswer())){
                    result++;
                }
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
    }
}
