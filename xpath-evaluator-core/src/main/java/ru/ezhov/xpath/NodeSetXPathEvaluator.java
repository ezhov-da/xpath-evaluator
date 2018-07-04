package ru.ezhov.xpath;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import java.io.StringReader;

class NodeSetXPathEvaluator extends AbstractXPathEvaluator {

    @Override
    public String evaluate(String xpathExpression, String xml) throws XPathEvaluatorException {
        try {
            XPathExpression compile = xPath.compile(xpathExpression);
            NodeList nodeList = (NodeList)
                    compile.evaluate(new InputSource(new StringReader(xml)), XPathConstants.NODESET);
            if (nodeList != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    result = nodeToString(nodeList.item(i));
                    stringBuilder.append(result + "\n");
                }
                result = stringBuilder.toString();
            } else {
                result = "";
            }
            return result;
        } catch (Exception e) {
            throw new XPathEvaluatorException(e);
        }
    }
}
