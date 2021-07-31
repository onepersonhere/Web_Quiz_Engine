package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PageService {
    @Autowired
    QuizRepo quizRepo;

    public List<Quiz> getAllQuiz(Integer pageNo){
        Pageable paging = PageRequest.of(pageNo, 10);
        Page<Quiz> pagedResult = quizRepo.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Quiz>();
        }
    }
}
