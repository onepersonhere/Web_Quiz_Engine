package engine.completed;

import engine.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompletedPageService {
    @Autowired
    CompletedPageRepo completedPageRepo;

    public List<CompletedQuiz> getAllCompletedQuiz(Integer pageNo){
        Pageable paging = PageRequest.of(pageNo, 10);
        Page<CompletedQuiz> pagedResult = completedPageRepo.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<CompletedQuiz>();
        }
    }
}
