package ru.ezhov.xpath;

import org.xml.sax.InputSource;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import java.io.StringReader;

class NumberNodeXPathEvaluator extends AbstractXPathEvaluator {

    @Override
    public String evaluate(String xpathExpression, String xml) throws XPathEvaluatorException {
        try {
            XPathExpression compile = xPath.compile(xpathExpression);
            result = compile.evaluate(new InputSource(new StringReader(xml)), XPathConstants.NUMBER).toString();
            return result;
        } catch (Exception e) {
            throw new XPathEvaluatorException(e);
        }
    }
}
