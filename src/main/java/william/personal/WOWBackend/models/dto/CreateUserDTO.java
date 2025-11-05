package william.personal.WOWBackend.models.dto;

import jakarta.validation.constraints.NotNull;

public record CreateUserDTO(
        @NotNull(message = "Email is required")
        String email,
        @NotNull(message = "Name is required")
        String name,
        @NotNull(message = "Password is required")
        String password
) {
}
