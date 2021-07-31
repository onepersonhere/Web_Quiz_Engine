package engine.repos;

import engine.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface Repo extends CrudRepository<Quiz, Integer> {
}
