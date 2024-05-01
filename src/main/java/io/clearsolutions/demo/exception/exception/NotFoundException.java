package io.clearsolutions.demo.exception.exception;

public class NotFoundException extends RuntimeException {
    /**
     * Constructs a new {@link NotFoundException} with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link NotFoundException} with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     *               (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
