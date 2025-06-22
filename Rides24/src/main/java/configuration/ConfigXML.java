package configuration;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.InputStream;

public class ConfigXML {

    private static ConfigXML instance = null;

    private String businessLogicNode;
    private boolean businessLogicLocal;
    private String locale;
    private String databasePort = "9999";
    private boolean databaseLocal = true;
    private String user = "admin";

    public static ConfigXML getInstance() {
        if (instance == null) {
            instance = new ConfigXML();
        }
        return instance;
    }

    private ConfigXML() {
        try {
          
            InputStream is = getClass().getClassLoader().getResourceAsStream("config.xml");

            if (is == null) {
                throw new RuntimeException("Archivo config.xml no encontrado en classpath");
            }

            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            if (root == null || !"config".equals(root.getNodeName())) {
                throw new RuntimeException("Archivo config.xml mal formado: falta nodo raíz <config>");
            }

            
            this.businessLogicNode = getTagValue("businessLogicNode", root);
            this.businessLogicLocal = "local".equalsIgnoreCase(getTagValue("businessLogic", root));
            this.locale = getTagValue("locale", root);

            String dbLocalStr = getTagValue("databaseLocal", root);
            if (dbLocalStr != null) this.databaseLocal = Boolean.parseBoolean(dbLocalStr);

            String port = getTagValue("databasePort", root);
            if (port != null && !port.isEmpty()) this.databasePort = port;

            String userValue = getTagValue("user", root);
            if (userValue != null && !userValue.isEmpty()) this.user = userValue;

        } catch (Exception e) {
            System.err.println("Error al leer config.xml: " + e.getMessage());
            throw new RuntimeException("Error en ConfigXML.java: problemas con config.xml", e);
        }
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList == null || nodeList.getLength() == 0)
            return null;
        return nodeList.item(0).getTextContent().trim();
    }

    // Getters
    public String getBusinessLogicNode() {
        return businessLogicNode;
    }

    public boolean isBusinessLogicLocal() {
        return businessLogicLocal;
    }

    public String getLocale() {
        return locale;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public boolean isDatabaseLocal() {
        return databaseLocal;
    }

    public String getUser() {
        return user;
    }
}