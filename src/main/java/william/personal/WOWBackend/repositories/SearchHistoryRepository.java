package william.personal.WOWBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import william.personal.WOWBackend.models.entity.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, String> {
}
