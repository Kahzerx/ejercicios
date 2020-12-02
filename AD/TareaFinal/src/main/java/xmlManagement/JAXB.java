package xmlManagement;

import fileManagement.FileStuffs;
import jaxbGenerated.ComponentType;
import jaxbGenerated.Components;
import utils.UpdateText;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JAXB {
    private static Components myComponents;

    public static void openJAXB() {
        File f = new File(FileStuffs.fileName);
        try {
            JAXBContext context = JAXBContext.newInstance(Components.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            myComponents = (Components) unmarshaller.unmarshal(f);
            UpdateText.updateComboBox1(getBox1Content());
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

    public static void updateCat(int id, String newValue) {
        List<ComponentType> componentTypes = myComponents.getComponent();
        for (ComponentType component : componentTypes) {
            if (component.getId() == id) {
                component.setCategory(newValue);
            }
        }
    }

    public static void getContent() {
        List<ComponentType> componentTypes = myComponents.getComponent();
        String[] data = new String[9];
        ArrayList<String> authors = new ArrayList<>();
        ArrayList<TextAreaType> content = new ArrayList<>();

        for (ComponentType component : componentTypes) {
            data[0] = String.valueOf(component.getId());
            data[1] = component.getDate();
            data[2] = component.getCompArch();
            data[3] = component.getType();
            data[4] = String.valueOf(component.getSpeed());
            data[5] = component.getCategory();
            data[6] = component.getOrientation();
            data[7] = String.valueOf(component.getBits());
            data[8] = component.getNumSystem();
            authors.addAll(component.getAuthors().getAuthor());
            content.add(new TextAreaType(data, authors.toArray()));
            data = new String[9];
            authors.clear();
        }

        UpdateText.updateMainTextArea(content);
    }
}
