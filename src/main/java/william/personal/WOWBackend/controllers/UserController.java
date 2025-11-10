package william.personal.WOWBackend.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import william.personal.WOWBackend.models.data.UserData;
import william.personal.WOWBackend.models.dto.CreateUserDTO;
import william.personal.WOWBackend.models.dto.LoginUserDTO;
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

    @PostMapping("/login")
    public UserData loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        UserData data = this.userService.loginUser(loginUserDTO);
        System.out.println(data.email());
        return data;
    }
}
