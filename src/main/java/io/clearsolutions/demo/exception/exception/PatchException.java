package io.clearsolutions.demo.exception.exception;

public class PatchException extends RuntimeException {
    /**
     * Constructs a new {@link PatchException} with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public PatchException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link PatchException} with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     *               (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public PatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
