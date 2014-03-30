package org.cauli.server.handler.exceptions;

/**
 * Exception handler that does nothing. Exceptions are silently discarded.
 *
 * @see org.cauli.server.WebServer#connectionExceptionHandler(java.lang.Thread.UncaughtExceptionHandler)
 * @see org.cauli.server.WebServer#uncaughtExceptionHandler(java.lang.Thread.UncaughtExceptionHandler)
 */
public class SilentExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        // Do nothing.
    }

}

