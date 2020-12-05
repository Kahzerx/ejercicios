package xmlManagement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DOM {
    public static Document doc;

    public static void addComponent(int id, int[] date, String arch, String type, String speed, String category, String orientation, String bits, String numSys, String authors) {
        Element root = doc.getDocumentElement();

        Element newComponent = doc.createElement("Component");
        newComponent.setAttribute("id", String.valueOf(id));
        newComponent.setAttribute("date", String.format("%s-%s-%s", date[0], date[1], date[2]));
        newComponent.setAttribute("comp_arch", arch);

        Element newType = doc.createElement("type");
        newType.appendChild(doc.createTextNode(type));
        newComponent.appendChild(newType);

        Element newAuthors = doc.createElement("authors");
        for (String au : authors.split("\n")) {
            Element newAuthor = doc.createElement("author");
            newAuthor.appendChild(doc.createTextNode(au));
            newAuthors.appendChild(newAuthor);
        }
        newComponent.appendChild(newAuthors);

        Element newSpeed = doc.createElement("speed");
        newSpeed.appendChild(doc.createTextNode(speed));
        newComponent.appendChild(newSpeed);

        Element newCategory = doc.createElement("category");
        newCategory.appendChild(doc.createTextNode(category));
        newComponent.appendChild(newCategory);

        Element newOrientation = doc.createElement("orientation");
        newOrientation.appendChild(doc.createTextNode(orientation));
        newComponent.appendChild(newOrientation);

        Element newBits = doc.createElement("bits");
        newBits.appendChild(doc.createTextNode(bits));
        newComponent.appendChild(newBits);

        Element newNumSys = doc.createElement("num_system");
        newNumSys.appendChild(doc.createTextNode(numSys));
        newComponent.appendChild(newNumSys);

        root.appendChild(newComponent);

        JAXB.syncJaxb(doc);
    }
}
