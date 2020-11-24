package XMLManagement;

import fileManagement.FileManagement;
import generated.Libros;
import utils.Reset;
import utils.Update;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JAXB {

    private static Libros myBooks;

    // Al abrir el JAXB.
    public static void onOpenJAXB() {
        File f = FileManagement.chooseXMLFile();
        if (f == null) {
            Reset.noFile();
            return;
        }
        try {
            JAXBContext context = JAXBContext.newInstance(Libros.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            myBooks = (Libros) unmarshaller.unmarshal(f);

            Update.updateMainTextArea(getContent(), "JAXB");
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    // Extraer el contenido.
    private static ArrayList<String[]> getContent() {
        ArrayList<String[]> content = new ArrayList<>();
        String[] data = new String[4];

        List<Libros.Libro> books = myBooks.getLibro();
        for (Libros.Libro book : books) {
            data[0] = book.getPublicadoEn();
            data[1] = book.getTitulo();
            data[2] = book.getAutor();
            data[3] = book.getEditorial();

            content.add(data);
            data = new String[4];
        }
        return content;
    }
}
