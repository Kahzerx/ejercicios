package XMLManagement;

import fileManagement.FileManagement;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import utils.Reset;
import utils.Update;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SAX {

    private static SAXMng sm;
    private static SAXParser parser;

    // Se ejecuta al clickar el botón de abrir.
    public static void onOpenSAX() {
        File f = FileManagement.chooseXMLFile();
        if (f == null) {
            Reset.noFile();
            return;
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();
            sm = new SAXMng();
            getContent(f);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void getContent(File f) throws IOException, SAXException {
        sm.j = 0;
        sm.content.clear();
        sm.data = new String[4];

        parser.parse(f, sm);
        Update.updateMainTextArea(sm.content, "SAX");
    }

    static class SAXMng extends DefaultHandler {
        ArrayList<String[]> content = new ArrayList<>();
        String[] data = new String[4];
        int j;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (qName.equals("Libro")) data[0] = attributes.getValue(attributes.getQName(0));
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (qName.equals("Editorial")) {
                content.add(data);
                data = new String[4];
                j = 0;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            StringBuilder temp = new StringBuilder();
            for (int i = start; i < start + length; i++) {
                temp.append(ch[i]);
            }
            if (!temp.toString().trim().equals("")) {
                data[j + 1] = temp.toString().trim();
                j++;
            }
        }
    }
}
