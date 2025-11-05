package william.personal.WOWBackend.services;

import org.springframework.stereotype.Service;
import william.personal.WOWBackend.models.dto.CreateUserDTO;
import william.personal.WOWBackend.models.entity.User;
import william.personal.WOWBackend.repositories.UserRepository;

import javax.management.openmbean.KeyAlreadyExistsException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        return user;
    }

    public User saveUser(CreateUserDTO createUserDTO) {
        User existingUser = this.findUserByEmail(createUserDTO.email());
        if (existingUser != null)
            throw new KeyAlreadyExistsException(String.format("Email %s already exists", createUserDTO.email()));

        User user = User.builder()
                .email(createUserDTO.email())
                .name(createUserDTO.name())
                .password(createUserDTO.password())
                .build();

        return this.userRepository.save(user);
    }
}
