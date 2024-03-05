package org.example.satdwn.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.satdwn.model.Descripcion;
import org.example.satdwn.model.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


public class ParserFile {

    public static Logger LOGGER = LogManager.getLogger(ParserFile.class);

    public ParserFile() {
    }

    public static Response getParseValues(String path) {

        Response response = new Response();
        Descripcion descripcion = new Descripcion();

        try {

            //LOGGER.info("path from AWS:  { " + path + " }");
            File archivoXML = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivoXML);
            doc.getDocumentElement().normalize();
            Element comprobanteElement = doc.getDocumentElement();


            /* get client from xml*/
            NodeList repectEgr = comprobanteElement.getElementsByTagName("cfdi:Receptor");
            Element cliente = (Element) repectEgr.item(0);
            descripcion.setCliente(cliente.getAttribute("Nombre"));

            /* get amount from xml*/
            Element total = doc.getDocumentElement();
            descripcion.setCantidad(total.getAttribute("Total"));

            /* get date from xml*/
            Element date = doc.getDocumentElement();
            descripcion.setFecha(date.getAttribute("Fecha").substring(0, 10));
            response.setDescripcion(descripcion);

            archivoXML.delete();

        } catch (Exception e) {
            LOGGER.error("error { " + e.getLocalizedMessage() + " }");
        }
        return response;
    }
}
