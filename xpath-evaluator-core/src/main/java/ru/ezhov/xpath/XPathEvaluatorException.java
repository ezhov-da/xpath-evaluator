package ru.ezhov.xpath;

/**
 * Created by ezhov on 29.06.2018.
 */
public class XPathEvaluatorException extends Exception {
    public XPathEvaluatorException() {
    }

    public XPathEvaluatorException(String message) {
        super(message);
    }

    public XPathEvaluatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public XPathEvaluatorException(Throwable cause) {
        super(cause);
    }

    public XPathEvaluatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
