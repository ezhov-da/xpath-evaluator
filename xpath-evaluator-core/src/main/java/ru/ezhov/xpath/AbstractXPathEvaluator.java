package ru.ezhov.xpath;

import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.StringWriter;

abstract class AbstractXPathEvaluator implements XPathEvaluator {
    protected String result = "Ooops error";
    protected XPath xPath;

    public AbstractXPathEvaluator() {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        this.xPath = xPathFactory.newXPath();
    }

    @Override
    public abstract String evaluate(String xpathExpression, String xml) throws XPathEvaluatorException;

    protected String nodeToString(Node node) throws Exception {
        StringWriter sw = new StringWriter();
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(node), new StreamResult(sw));
        return sw.toString();
    }
}
