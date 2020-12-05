package xmlManagement;

import fileManagement.FileStuffs;
import jaxbGenerated.ComponentType;
import jaxbGenerated.Components;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import utils.UpdateText;

import javax.swing.*;
import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class JAXB {
    private static Components myComponents;
    private static JAXBContext context;

    static {
        try {
            context = JAXBContext.newInstance(Components.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void openJAXB() {
        File f = new File(FileStuffs.fileName);
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            myComponents = (Components) unmarshaller.unmarshal(f);
            UpdateText.updateComboBox1(getBox1Content());
            syncDom();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Integer> getBox1Content() {
        ArrayList<Integer> ids = new ArrayList<>();
        List<ComponentType> componentTypes = myComponents.getComponent();
        for (ComponentType component : componentTypes) {
            ids.add(component.getId());
        }
        return ids;
    }

    public static int getId() {
        List<ComponentType> componentTypes = myComponents.getComponent();
        int id = 0;
        for (ComponentType component : componentTypes) {
            id = component.getId();
        }
        return id + 1;
    }

    public static void updateCat(int id, String newValue) {
        List<ComponentType> componentTypes = myComponents.getComponent();
        for (ComponentType component : componentTypes) {
            if (component.getId() == id) {
                component.setCategory(newValue);
            }
        }
    }

    public static void getContent() {
        try {
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshaller.marshal(myComponents, sw);
            SAX.getContent(sw.toString());
            syncDom();
        } catch (JAXBException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveJaxb() {
        File f = FileStuffs.createAndSave();
        if (f == null) {
            return;
        }
        try {
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(myComponents, f);
            JOptionPane.showMessageDialog(null, "Archivo guardado correctamente", "GG", JOptionPane.INFORMATION_MESSAGE);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void syncDom() {
        try {
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.newDocument();

            marshaller.marshal(myComponents, document);
            DOM.doc = document;
        } catch (JAXBException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void syncJaxb(Document doc) {
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            myComponents = (Components) unmarshaller.unmarshal(doc);
            UpdateText.updateComboBox1(getBox1Content());
            getContent();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
