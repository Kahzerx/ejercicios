package management;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import utils.UpdateText;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SAX {
    private static SAXParser parser;
    private static SAXMng sm;

    public static void onOpenSax() {
        File f = new File("logixs.xml");
        if (!f.exists()) {
            JOptionPane.showMessageDialog(null, "Archivo no encontrado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();
            sm = new SAXMng();

            getContent(f);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void getContent(File f) throws IOException, SAXException {
        sm.data = new String[9];
        sm.authors.clear();
        sm.content.clear();
        sm.isAuthor = false;
        sm.j = 0;

        parser.parse(f, sm);
        UpdateText.updateMainTextArea(sm.content);
    }

    static class SAXMng extends DefaultHandler {
        ArrayList<String> authors = new ArrayList<>();
        ArrayList<SaxType> content = new ArrayList<>();
        String[] data = new String[9];
        boolean isAuthor = false;
        int j = 0;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (qName.equals("Component")) {
                data[0] = attributes.getValue(attributes.getQName(0));
                data[1] = attributes.getValue(attributes.getQName(1));
                data[2] = attributes.getValue(attributes.getQName(2));
            }
            else if(qName.equals("authors")) {
                isAuthor = true;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (qName.equals("num_system")) {
                content.add(new SaxType(data, authors.toArray()));
                authors.clear();
                data = new String[9];
                j = 0;
            }
            else if(qName.equals("authors")) {
                isAuthor = false;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            StringBuilder temp = new StringBuilder();
            for (int i = start; i < start + length; i++) {
                temp.append(ch[i]);
            }
            if (!temp.toString().trim().equals("")){
                if (isAuthor) {
                    authors.add(temp.toString().trim());
                }
                else {
                    data[j + 3] = temp.toString().trim();
                    j++;
                }
            }
        }
    }
}
