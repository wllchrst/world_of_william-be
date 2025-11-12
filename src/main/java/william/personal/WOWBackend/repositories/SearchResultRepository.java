package william.personal.WOWBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import william.personal.WOWBackend.models.entity.SearchResult;

public interface SearchResultRepository extends JpaRepository<SearchResult, String> {
}
