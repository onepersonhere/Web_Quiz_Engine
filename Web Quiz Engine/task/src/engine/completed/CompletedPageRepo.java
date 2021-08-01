package engine.completed;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompletedPageRepo extends PagingAndSortingRepository<CompletedQuiz,String> {
    List<CompletedQuiz> findAllByAuthor(String author, Pageable pageable);
}
