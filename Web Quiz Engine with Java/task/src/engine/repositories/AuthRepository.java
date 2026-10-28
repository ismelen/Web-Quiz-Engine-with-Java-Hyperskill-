package engine.repositories;

import engine.domain.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends CrudRepository<AppUser, Integer> {
    boolean existsByEmail(String email);
    Optional<AppUser> findByEmail(String email);
}
