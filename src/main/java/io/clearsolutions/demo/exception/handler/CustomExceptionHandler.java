package io.clearsolutions.demo.exception.handler;

import io.clearsolutions.demo.exception.exception.CreationException;
import io.clearsolutions.demo.exception.exception.ExceptionResponse;
import io.clearsolutions.demo.exception.exception.NotFoundException;
import io.clearsolutions.demo.exception.exception.PatchException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorAttributes errorAttributes;

    /**
     * Exception handler method for handling {@link NotFoundException}.
     *
     * @param e          The {@link NotFoundException} instance that occurred.
     * @param webRequest The {@link WebRequest} associated with the request.
     * @return A {@link ResponseEntity} containing an {@link ExceptionResponse} with {@link HttpStatus#NOT_FOUND}.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(webRequest));
        log.trace(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(exceptionResponse);
    }

    /**
     * Exception handler method for handling {@link CreationException}.
     *
     * @param e          The {@link CreationException} instance that occurred.
     * @param webRequest The {@link WebRequest} associated with the request.
     * @return A {@link ResponseEntity} containing an {@link ExceptionResponse} with {@link HttpStatus#BAD_REQUEST}.
     */
    @ExceptionHandler(CreationException.class)
    public ResponseEntity<ExceptionResponse> handleCreationException(CreationException e, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(webRequest));
        log.trace(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(exceptionResponse);
    }

    /**
     * Exception handler method for handling {@link PatchException}.
     *
     * @param e          The {@link PatchException} instance that occurred.
     * @param webRequest The {@link WebRequest} associated with the request.
     * @return A {@link ResponseEntity} containing an {@link ExceptionResponse} with {@link HttpStatus#BAD_REQUEST}.
     */
    @ExceptionHandler(PatchException.class)
    public ResponseEntity<ExceptionResponse> handlePatchException(PatchException e, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(webRequest));
        log.trace(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(exceptionResponse);
    }

    /**
     * Override method to handle validation errors in method arguments.
     *
     * @param ex      The MethodArgumentNotValidException instance that occurred.
     * @param headers The HttpHeaders associated with the response.
     * @param status  The HttpStatusCode representing the status of the response.
     * @param request The WebRequest associated with the request.
     * @return A ResponseEntity containing a HashMap of field names and error messages with HttpStatus.BAD_REQUEST.
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                               HttpStatusCode status, WebRequest request) {
        HashMap<String, String> map = new HashMap<>();
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        errors.forEach((error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            map.put(fieldName, message);
        }));

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to intercept {@link ConstraintViolationException}.
     *
     * @param ex      The {@link ConstraintViolationException} to be intercepted.
     * @param request The {@link WebRequest} associated with the request.
     * @return A {@link ResponseEntity} containing the {@link HttpStatus} and a body with the exception message.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
                                                                           WebRequest request) {
        log.warn(ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        String detailedMessage = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(" "));
        exceptionResponse.setMessage(detailedMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    private Map<String, Object> getErrorAttributes(WebRequest webRequest) {
        return new HashMap<>(errorAttributes.getErrorAttributes(webRequest,
            ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE)));
    }
}
