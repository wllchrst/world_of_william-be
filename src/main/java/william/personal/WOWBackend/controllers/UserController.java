package william.personal.WOWBackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.*;
import william.personal.WOWBackend.models.data.BaseResponse;
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
    public ResponseEntity<BaseResponse<Boolean>> createUser(@RequestBody CreateUserDTO createUserDTO) {
        User user = this.userService.saveUser(createUserDTO);
        return BaseResponse.success("Login success", user != null);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<String>> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        String token = this.userService.loginUser(loginUserDTO);
        return BaseResponse.success("Login success", token);
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserData>> me(@RequestHeader("Authorization") String authHeader) {
        if (authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            UserData user = this.userService.retrieveUserByToken(token);
            return BaseResponse.success("Login success", user);
        } else throw new AuthorizationDeniedException("You are not authorized");
    }
}
