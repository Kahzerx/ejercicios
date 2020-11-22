package XMLManagement;

import application.MainWindow;
import fileManagement.FileManagement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DOM {
    private static Document doc;

    // Se ejecuta al clickar el botón de abrir.
    public static void onOpenDOM() {
        doc = null;
        File f = FileManagement.chooseXMLFile();
        if (f == null) {
            MainWindow.noFile();
            return;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(f);  // Parsear el archivo.
            updateWindow();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    // Se ejecuta al clickar el botón de añadir.
    public static void onAdd(String published, String title, String author) {
        if (doc != null) {
            if (!title.equals("") && !published.equals("") && !author.equals("")) {
                if (isInt(published)) {
                    tryAppend(published, title, author);
                } else
                    JOptionPane.showMessageDialog(null, "La fecha de publicación tiene que ser un número", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "Completa todos los fields antes de añadir", "Incompleto", JOptionPane.ERROR_MESSAGE);
        } else JOptionPane.showMessageDialog(null, "Debes abrir un archivo antes", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    // Se ejecuta al clickar el botón de editar un título.
    public static void onTitleUpdate(String selectedItem, String text) {
        if (doc != null) {
            if (!selectedItem.equals("") && !text.equals("")) {
                tryUpdate(selectedItem, text);
            } else
                JOptionPane.showMessageDialog(null, "Completa todos los campos antes de continuar", "Incompleto", JOptionPane.ERROR_MESSAGE);
        } else JOptionPane.showMessageDialog(null, "Debes abrir un archivo antes", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    // Intentar actualizar el título.
    private static void tryUpdate(String item, String text) {
        Node root = doc.getDocumentElement();
        Node temp;
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList subNodes = nodeList.item(i).getChildNodes();
            for (int j = 0; j < subNodes.getLength(); j++) {
                temp = subNodes.item(j);
                if (temp.getNodeType() == Node.ELEMENT_NODE) {
                    if (temp.getNodeName().equals("Titulo") && temp.getFirstChild().getNodeValue().equals(item)) {
                        temp.setTextContent(text);
                    }
                }
            }
        }
        updateWindow();
    }

    // Intentar añadir el nuevo libro.
    private static void tryAppend(String published, String title, String author) {
        Element root = doc.getDocumentElement();

        Element newBook = doc.createElement("Libro");
        newBook.setAttribute("publicado_en", published);

        Element newTitle = doc.createElement("Titulo");
        newTitle.appendChild(doc.createTextNode(title));
        newBook.appendChild(newTitle);

        Element newAuthor = doc.createElement("Autor");
        newAuthor.appendChild(doc.createTextNode(author));
        newBook.appendChild(newAuthor);

        root.appendChild(newBook);

        updateWindow();
    }

    // Sacar el contenido del doc para el text area.
    private static ArrayList<String[]> getContent(Document doc) {
        Node node;
        String[] data;
        Node root = doc.getFirstChild();
        NodeList nodeList = root.getChildNodes();
        ArrayList<String[]> values = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                data = process(node);
                values.add(data);
            }
        }
        return values;
    }

    // Procesar libro a libro en array para el text area.
    private static String[] process(Node node) {
        String[] data = new String[4];
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

    // Guardar el doc actual en un archivo que se puede seleccionar.
    public static void writeAndClose() {
        if (doc != null) {
            try {
                File newFile = FileManagement.createAndSave();
                if (newFile != null) {
                    DOMSource src = new DOMSource(doc);
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                    StreamResult result = new StreamResult(newFile.getAbsolutePath());
                    transformer.transform(src, result);
                }
                else JOptionPane.showMessageDialog(null, "No ha sido posible guardar el archivo", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            catch (TransformerException e) {
                JOptionPane.showMessageDialog(null, "No ha sido posible guardar el archivo", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        else JOptionPane.showMessageDialog(null, "Debes abrir un archivo antes", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    private static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }

    // Actualizar el text area, los fields y comboBox.
    private static void updateWindow() {
        MainWindow.update(getContent(doc), "DOM");
    }
}
