package william.personal.WOWBackend.controllers;

import org.springframework.web.bind.annotation.*;
import william.personal.WOWBackend.models.dto.CreateUserDTO;
import william.personal.WOWBackend.models.entity.User;
import william.personal.WOWBackend.services.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create_user")
    public boolean createUser(@RequestBody CreateUserDTO createUserDTO) {
        User user = this.userService.saveUser(createUserDTO);

        return user != null;
    }
}
