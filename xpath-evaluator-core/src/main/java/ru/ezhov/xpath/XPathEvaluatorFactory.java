package ru.ezhov.xpath;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;

public final class XPathEvaluatorFactory {

    private XPathEvaluatorFactory() {
    }

    public static XPathEvaluator newInstance(QName qName) {
        XPathEvaluator xPathEvaluator;
        if (qName == XPathConstants.NODESET) {
            xPathEvaluator = new NodeSetXPathEvaluator();
        } else if (qName == XPathConstants.NODE) {
            xPathEvaluator = new NodeXPathEvaluator();
        } else if (qName == XPathConstants.BOOLEAN) {
            xPathEvaluator = new BooleanNodeXPathEvaluator();
        } else if (qName == XPathConstants.STRING) {
            xPathEvaluator = new StringNodeXPathEvaluator();
        } else if (qName == XPathConstants.NUMBER) {
            xPathEvaluator = new NumberNodeXPathEvaluator();
        } else {
            throw new IllegalArgumentException("Неподдерживаемый тип QName");
        }
        return xPathEvaluator;
    }
}
