package XMLManagement;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;

public class XPATH {
    public static void processQuery(Document doc, String query) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression expression = xPath.compile("Libros/*/Autor");

            Object result = expression.evaluate(doc, XPathConstants.NODESET);
            NodeList nodeList = (NodeList) result;
            for (int i = 0; i < nodeList.getLength(); i++) {
                nodeList.item(i);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }
}
