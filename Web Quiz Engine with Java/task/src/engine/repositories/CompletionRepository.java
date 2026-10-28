package engine.repositories;

import engine.domain.AppUser;
import engine.domain.Completion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletionRepository extends PagingAndSortingRepository<Completion, Integer>, CrudRepository<Completion, Integer> {
    Page<Completion> findAllByUser(AppUser user, Pageable pageable);
}
