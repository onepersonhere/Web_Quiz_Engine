package engine.completed;

import engine.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompletedService {
    @Autowired
    CompletedRepo completedRepo;

    public List<CompletedQuiz> getAllQuiz(){
        List<CompletedQuiz> completedQuizList = new ArrayList<>();
        completedRepo.findAll().forEach(completedQuiz -> completedQuizList.add(completedQuiz));
        return completedQuizList;
    }

    public void saveNewQuiz(CompletedQuiz quiz){
        completedRepo.save(quiz);
    }
}
