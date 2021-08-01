package engine.completed;

import engine.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompletedPageService {
    @Autowired
    CompletedPageRepo completedPageRepo;

    public List<CompletedQuiz> getAllCompletedQuiz(Integer pageNo, String author){
        Pageable paging = PageRequest.of(pageNo, 10, Sort.by("completedAt").descending());
        List<CompletedQuiz> pagedResult = completedPageRepo.findAllByAuthor(author, paging);

        if(!pagedResult.isEmpty()) {
            return pagedResult;
        } else {
            return new ArrayList<CompletedQuiz>();
        }
    }
}
