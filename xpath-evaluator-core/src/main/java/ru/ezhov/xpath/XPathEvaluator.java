package ru.ezhov.xpath;

public interface XPathEvaluator {
    String evaluate(String xpathExpression, String xml) throws XPathEvaluatorException;
}
