package ru.ezhov.xpath;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import java.io.StringReader;

class NodeXPathEvaluator extends AbstractXPathEvaluator {


    @Override
    public String evaluate(String xpathExpression, String xml) throws XPathEvaluatorException {
        try {
            XPathExpression compile = xPath.compile(xpathExpression);
            Node node = (Node)
                    compile.evaluate(new InputSource(new StringReader(xml)), XPathConstants.NODE);
            if (node != null) {
                result = nodeToString(node);
            }
            return result;
        } catch (Exception e) {
            throw new XPathEvaluatorException(e);
        }
    }
}
