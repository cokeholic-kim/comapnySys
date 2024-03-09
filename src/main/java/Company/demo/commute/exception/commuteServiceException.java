package Company.demo.commute.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class commuteServiceException {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentExceptionHandle(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("error : " + e.getMessage());
    }

}
