package ru.ezhov.xpath;

import org.xml.sax.InputSource;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import java.io.StringReader;

class BooleanNodeXPathEvaluator extends AbstractXPathEvaluator {

    @Override
    public String evaluate(String xpathExpression, String xml) throws XPathEvaluatorException {
        try {
            XPathExpression compile = xPath.compile(xpathExpression);
            boolean b = Boolean.valueOf(
                    compile.evaluate(new InputSource(new StringReader(xml)), XPathConstants.BOOLEAN).toString()
            );
            result = String.valueOf(b);
            return result;
        } catch (Exception e) {
            throw new XPathEvaluatorException(e);
        }
    }
}
