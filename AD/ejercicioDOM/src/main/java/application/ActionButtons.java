package application;

import fileManagement.FileManagement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ActionButtons {
    public static void onOpenDOM() {
        Document doc = null;
        File file = FileManagement.chooseXMLFile();
        if (file == null) {
            return;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(file);
            MainWindow.setText(getContent(doc));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String[]> getContent(Document doc) {
        Node node;
        String[] data;
        Node root = doc.getFirstChild();
        NodeList nodeList = root.getChildNodes();
        ArrayList<String[]> values = new ArrayList<String[]>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                data = process(node);
                values.add(data);
            }
        }
        return values;
    }

    private static String[] process(Node node) {
        String[] data = new String[3];
        Node temp;
        int acc = 1;
        data[0] = node.getAttributes().item(0).getNodeValue();
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            temp = list.item(i);
            if (temp.getNodeType() == Node.ELEMENT_NODE) {
                data[acc] = temp.getFirstChild().getNodeValue();
                acc++;
            }
        }
        return data;
    }
}
