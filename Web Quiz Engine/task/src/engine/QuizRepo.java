package engine;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepo extends PagingAndSortingRepository<Quiz, Integer> {
}
