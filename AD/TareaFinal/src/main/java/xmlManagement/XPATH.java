package xmlManagement;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.UpdateText;

import javax.xml.xpath.*;
import java.util.HashMap;

public class XPATH {
    public static void processQuery(String query) {
        Document doc = DOM.doc;
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression expression = xPath.compile(query);
            if (query.startsWith("count")) {
                Double result = (Double) expression.evaluate(doc, XPathConstants.NUMBER);
                UpdateText.updateSideTextArea(result.intValue());
            }
            else {
                Object result = expression.evaluate(doc, XPathConstants.NODESET);
                NodeList nodeList = (NodeList) result;
                getContent(nodeList);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private static void getContent(NodeList list) {
        int counter = 0;
        HashMap<String, Integer> content = new HashMap<>();
        for (int i = 0; i < list.getLength(); i++) {
            Node temp = list.item(i);
            if (temp.getNodeType() == Node.ELEMENT_NODE || temp.getNodeType() == Node.ATTRIBUTE_NODE) {
                if (content.containsKey(temp.getFirstChild().getNodeValue())) {
                    content.put(temp.getFirstChild().getNodeValue(), content.get(temp.getFirstChild().getNodeValue()) + 1);
                }
                else content.put(temp.getFirstChild().getNodeValue(), 1);
                counter++;
            }
        }
        UpdateText.updateSideTextArea(counter, content);
    }
}
