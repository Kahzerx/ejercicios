//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.21 at 08:24:25 PM CET 
//


package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="Libro" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Titulo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Autor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Editorial" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="publicado_en" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "libro"
})
@XmlRootElement(name = "Libros")
public class Libros {

    @XmlElement(name = "Libro")
    protected List<Libros.Libro> libro;

    /**
     * Gets the value of the libro property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the libro property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLibro().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Libros.Libro }
     * 
     * 
     */
    public List<Libros.Libro> getLibro() {
        if (libro == null) {
            libro = new ArrayList<Libros.Libro>();
        }
        return this.libro;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Titulo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Autor" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Editorial" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *       &lt;attribute name="publicado_en" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "titulo",
        "autor",
        "editorial"
    })
    public static class Libro {

        @XmlElement(name = "Titulo", required = true)
        protected String titulo;
        @XmlElement(name = "Autor", required = true)
        protected String autor;
        @XmlElement(name = "Editorial", required = true)
        protected String editorial;
        @XmlAttribute(name = "publicado_en")
        protected String publicadoEn;

        /**
         * Gets the value of the titulo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTitulo() {
            return titulo;
        }

        /**
         * Sets the value of the titulo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTitulo(String value) {
            this.titulo = value;
        }

        /**
         * Gets the value of the autor property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAutor() {
            return autor;
        }

        /**
         * Sets the value of the autor property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAutor(String value) {
            this.autor = value;
        }

        /**
         * Gets the value of the editorial property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEditorial() {
            return editorial;
        }

        /**
         * Sets the value of the editorial property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEditorial(String value) {
            this.editorial = value;
        }

        /**
         * Gets the value of the publicadoEn property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPublicadoEn() {
            return publicadoEn;
        }

        /**
         * Sets the value of the publicadoEn property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPublicadoEn(String value) {
            this.publicadoEn = value;
        }

    }

}
