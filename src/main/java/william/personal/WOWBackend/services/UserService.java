package william.personal.WOWBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import william.personal.WOWBackend.mappers.UserMapper;
import william.personal.WOWBackend.models.data.UserData;
import william.personal.WOWBackend.models.dto.CreateUserDTO;
import william.personal.WOWBackend.models.dto.LoginUserDTO;
import william.personal.WOWBackend.models.entity.Log;
import william.personal.WOWBackend.models.entity.User;
import william.personal.WOWBackend.repositories.UserRepository;

import javax.management.openmbean.KeyAlreadyExistsException;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private LogService logService;
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(CreateUserDTO createUserDTO) {
        User existingUser = userRepository
                .findByEmail(createUserDTO.email()).orElse(null);

        if (existingUser != null)
            throw new KeyAlreadyExistsException(String.format("Email %s already exists", createUserDTO.email()));

        String encodedPassword = passwordEncoder.encode(createUserDTO.password());

        User user = User.builder()
                .email(createUserDTO.email())
                .name(createUserDTO.name())
                .password(encodedPassword)
                .build();

        return this.userRepository.save(user);
    }

    public String loginUser(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.email())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        boolean password_matches = passwordEncoder.matches(loginUserDTO.password(), user.getPassword());

        if (password_matches) {
            String token = jwtService.generateToken(user.getEmail());
            logService.createLog(user.getEmail(),
                    "Login from the function loginUser", Log.LOGIN_TYPE);

            return token;
        } else
            throw new UsernameNotFoundException("Invalid username or password");
    }

    public UserData retrieveUserByToken(String token) {
        boolean valid = jwtService.validateToken(token);

        if (!valid)
            throw new AuthorizationDeniedException("User is not authorized!");

        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        return userMapper.toUserData(user);
    }
}
