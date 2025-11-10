package william.personal.WOWBackend.models.dto;

import jakarta.validation.constraints.NotNull;

public record LoginUserDTO(
        @NotNull(message = "")
        String email,
        @NotNull(message = "")
        String password
) {
}
