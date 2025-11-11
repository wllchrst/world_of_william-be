package william.personal.WOWBackend.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "logs")
public class Log extends BaseEntity {
    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String message;

    @Column(nullable = false)
    String type;

    public static String LOGIN_TYPE = "login";
    public static String EXCEPTION_TYPE = "exception";
}