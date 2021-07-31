package engine.repos;

import engine.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    Repo repo;

    public List<Quiz> getAllQuiz(){
        List<Quiz> quizList = new ArrayList<>();
        repo.findAll().forEach(quiz -> quizList.add(quiz));
        return quizList;
    }

    public void saveNewQuiz(Quiz quiz){
        repo.save(quiz);
    }

    public void deleteQuiz(Quiz quiz){
        repo.delete(quiz);
    }
}
