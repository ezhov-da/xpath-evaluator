package ru.ezhov.xpath.evaluator.gui.info;

/**
 * Created by ezhov on 05.07.2018.
 */
public class InfoException extends Exception {
    public InfoException() {
    }

    public InfoException(String message) {
        super(message);
    }

    public InfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfoException(Throwable cause) {
        super(cause);
    }

    public InfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
