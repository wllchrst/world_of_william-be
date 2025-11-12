package william.personal.WOWBackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "search_results")
public class SearchResult extends BaseEntity {
    @Column(nullable = false)
    String link;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    String snippet;

    @ManyToOne
    @JoinColumn(name = "search_history_id")
    @JsonIgnore
    private SearchHistory searchHistory;
}
