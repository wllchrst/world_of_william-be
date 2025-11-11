package william.personal.WOWBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import william.personal.WOWBackend.models.entity.Log;

public interface LogRepository extends JpaRepository<Log, String> {
}
