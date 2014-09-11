package org.cauli.mock.exception;

/**
 * @auther sky
 */
public class MockServerConstructError extends Exception {

    public MockServerConstructError() {
        super();
    }

    public MockServerConstructError(String message) {
        super(message);
    }

    public MockServerConstructError(String message, Throwable cause) {
        super(message, cause);
    }

    public MockServerConstructError(Throwable cause) {
        super(cause);
    }
}
