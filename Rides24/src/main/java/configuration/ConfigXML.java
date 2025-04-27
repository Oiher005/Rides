package configuration;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigXML {

    private static final String CONFIG_FILE = "config.xml";

    private String user;
    private String password;
    private String locale;
    private static ConfigXML theInstance = new ConfigXML();

    private ConfigXML() {
        try {
            // Cargar config.xml como recurso
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
            if (inputStream == null) {
                throw new RuntimeException("No se encontró el archivo config.xml en src/main/resources");
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();

            // Leer el <database>
            Element config = doc.getDocumentElement();
            Element database = (Element) config.getElementsByTagName("database").item(0);

            if (database == null) {
                throw new RuntimeException("No se encontró el nodo <database> en config.xml");
            }

            user = getTagValue("user", database);
            password = getTagValue("password", database);

            // Leer <locale> (está directamente dentro de <config>)
            locale = getTagValue("locale", config);

            System.out.println("Configuración cargada correctamente:");
            System.out.println("\tUser: " + user);
            System.out.println("\tPassword: " + password);
            System.out.println("\tLocale: " + locale);

        } catch (Exception e) {
            System.err.println("Error al cargar config.xml");
            e.printStackTrace();
            throw new RuntimeException("Error crítico al leer config.xml", e);
        }
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(0).getFirstChild();
            return node != null ? node.getNodeValue() : null;
        }
        return null;
    }

    public static ConfigXML getInstance() {
        return theInstance;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getLocale() {
        return locale;
    }
}
