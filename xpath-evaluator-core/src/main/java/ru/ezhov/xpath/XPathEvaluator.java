package ru.ezhov.xpath;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.io.StringWriter;

public class XPathEvaluator {
    private String xpath;
    private String xml;
    private QName qName;

    public XPathEvaluator(String xpath, String xml, QName qName) {
        this.xpath = xpath;
        this.qName = qName;
    }

    public String evaluate() throws XPathEvaluatorException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        try {
            String result = "";
            XPathExpression compile = xPath.compile(xpath);
            NodeList nodeList = (NodeList)
                    compile.evaluate(new InputSource(new StringReader(xml)), XPathConstants.NODESET);
            if (nodeList != null) {
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < nodeList.getLength(); i++) {
                    result = nodeToString(nodeList.item(i));
                    stringBuilder.append(result + "\n");
                }
                result = stringBuilder.toString();
            }
            return result;
        } catch (Exception e) {
            throw new XPathEvaluatorException();
        }
    }

    private String nodeToString(Node node) throws Exception {
        StringWriter sw = new StringWriter();
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(node), new StreamResult(sw));
        return sw.toString();
    }
}
