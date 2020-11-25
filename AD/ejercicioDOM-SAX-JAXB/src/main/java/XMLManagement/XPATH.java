package XMLManagement;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.Update;

import javax.swing.*;
import javax.xml.xpath.*;
import java.util.ArrayList;

public class XPATH {
    private static String[] data = new String[4];
    private static final ArrayList<String[]> content = new ArrayList<>();

    // Inicializo lo necesario para procesar la query.
    public static void processQuery(Document doc, String query) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression expression = xPath.compile(query);

            Object result = expression.evaluate(doc, XPathConstants.NODESET);
            NodeList nodeList = (NodeList) result;
            content.clear();
            getContent(nodeList, 0);
            Update.updateQueryTextArea(content);
        } catch (XPathExpressionException e) {
            JOptionPane.showMessageDialog(null, "Error al procesar la consulta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Recursivamente saco el contenido que necesito tanto elementos como atributos.
    private static void getContent(NodeList list, int j) {
        Node temp;
        for (int i = 0; i < list.getLength(); i++) {
            temp = list.item(i);
            if (temp.getNodeType() == Node.ELEMENT_NODE || temp.getNodeType() == Node.ATTRIBUTE_NODE) {
                if (list.item(i).getFirstChild().getNodeValue().trim().equals("")) {
                    NodeList subNodes = list.item(i).getChildNodes();
                    if (temp.hasAttributes()) {
                        data[0] = temp.getAttributes().item(0).getNodeValue();
                    }
                    getContent(subNodes, j);
                }
                else {
                    if (data[0] != null) {
                        j++;
                        data[j] = list.item(i).getFirstChild().getNodeValue();
                    }
                    else {
                        content.add(new String[]{list.item(i).getFirstChild().getNodeValue()});
                    }
                }
            }
        }
        if (data[0] != null) {
            content.add(data);
        }
        data = new String[4];
    }
}
