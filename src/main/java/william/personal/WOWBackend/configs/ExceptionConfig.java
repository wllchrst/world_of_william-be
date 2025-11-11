package william.personal.WOWBackend.configs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import william.personal.WOWBackend.models.data.BaseResponse;
import william.personal.WOWBackend.models.data.UserData;
import william.personal.WOWBackend.models.entity.Log;
import william.personal.WOWBackend.services.LogService;
import william.personal.WOWBackend.services.UserService;

@RestControllerAdvice
public class ExceptionConfig {
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;

    private String getUsername(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String username = "Username Unavailable";

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            UserData userData = userService.retrieveUserByToken(token);
            username = userData.email();
        }

        return username;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse<Object>> handleRuntimeException(RuntimeException ex,
                                                                       HttpServletRequest request) {
        logService.createLog(this.getUsername(request), ex.getMessage(), Log.EXCEPTION_TYPE);
        return BaseResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleException(Exception ex,
                                                                HttpServletRequest request) {
        logService.createLog(this.getUsername(request), ex.getMessage(), Log.EXCEPTION_TYPE);
        return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error, please contact the developers for help!");
    }
}
