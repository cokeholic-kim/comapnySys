package Company.demo.commute.exception;

import Company.demo.commute.dto.model.Api;
import Company.demo.commute.dto.model.Api.Error;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class commuteServiceException {
    private final String format = "%s : { %s } 은 %s";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Api<? extends Objects>> illegalArgumentExceptionHandle(IllegalArgumentException e) {
        String message = "error :" + e.getMessage();
        Error error = Error.builder()
                .errorMessage(Collections.singletonList(message))
                .build();

        Api errorResponse = Api.builder()
                .resultCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .resultMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .error(error)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}
