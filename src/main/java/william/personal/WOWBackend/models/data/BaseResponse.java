package william.personal.WOWBackend.models.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private int status;
    private String message;
    private T data;

    public static <T> ResponseEntity<BaseResponse<T>> success(String message, T data) {
        BaseResponse<T> body = new BaseResponse<>(HttpStatus.OK.value(), message, data);
        return ResponseEntity.ok(body);
    }

    public static <T> ResponseEntity<BaseResponse<T>> success(HttpStatus status, String message, T data) {
        BaseResponse<T> body = new BaseResponse<>(status.value(), message, data);
        return ResponseEntity.status(status).body(body);
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(HttpStatus status, String message) {
        BaseResponse<T> body = new BaseResponse<>(status.value(), message, null);
        return ResponseEntity.status(status).body(body);
    }
}