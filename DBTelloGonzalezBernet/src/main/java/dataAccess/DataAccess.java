package dataAccess;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import configuration.ConfigXML;
import configuration.UtilDate;

public class DataAccess {

	private static DataAccess instance= null;
	private Connection connection;
	ConfigXML c=ConfigXML.getInstance();
	private ArrayList<String> log;

	private DataAccess() {
		log=new ArrayList<String>();
		System.out.println("DataAccess Eraikitzaile.");
	}

	public static DataAccess getInstance() {
		if (instance == null) {
			instance = new DataAccess();
			System.out.println("Cargando DB.");
		}
		return instance;
	}

	public void open() throws ClassNotFoundException,SQLException {

			connection = MySQLConnection.getConnection();
			System.out.println("Database Open.");		
	}

	public void close() throws SQLException{
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Conexión cerrada correctamente.");
			}
		} catch (SQLException e) {
			System.err.println("Error al cerrar conexión: " + e.getMessage());
		}
	}

	public Connection getConnection() {
		System.out.println("Database Open.");

		return connection;
	}
	
    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }

        try {
            // Crear un GregorianCalendar a partir de la fecha
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);

            // Convertir GregorianCalendar a XMLGregorianCalendar
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String RunQuery(String query) {
    	log.add(query);
    	System.out.println(log);
        if (connection == null) {
            return "Error: Database connection is not open.";
        }

        StringBuilder output = new StringBuilder();

        try (Statement stmt = connection.createStatement()) {
            if (query.toLowerCase().trim().startsWith("select")) {
                try (ResultSet rs = stmt.executeQuery(query)) {
                    ResultSetMetaData meta = rs.getMetaData();
                    int columns = meta.getColumnCount();

                    boolean hasResults = false;
                    while (rs.next()) {
                        hasResults = true;
                        for (int i = 1; i <= columns; i++) {
                            output.append(meta.getColumnName(i))
                                  .append(": ")
                                  .append(rs.getString(i))
                                  .append("\t");
                        }
                        output.append("\n");
                    }
                    if (!hasResults) {
                        output.append("No results.\n");
                    }
                }
            } else {
                int affectedRows = stmt.executeUpdate(query);
                output.append("Query executed successfully.\nRows affected: ").append(affectedRows).append("\n");
            }
        } catch (SQLException e) {
            output.append("SQL Error:\n").append(e.getMessage()).append("\n");
        }

        return output.toString();
    }

    public List<String> getLogs(){
    	return log;
    	
    }
    


}


