package dataAccess;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import domain.ConcreteFlight;
import domain.Flight;
public class DataAccess {


	private static DataAccess instance= null;
	private Connection connection;




	ConfigXML c=ConfigXML.getInstance();

	private DataAccess() {
		open();
		System.out.println("DataAccess created => isDatabaseLocal: " + c.isDatabaseLocal());
	}

	public static DataAccess getInstance() {
		if (instance == null) {
			instance = new DataAccess();
			System.out.println("Cargando nueva DB.");
		}
		return instance;
	}

	public void open() {
		try {
			connection = MySQLConnection.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Error al abrir conexión: " + e.getMessage());
		}
	}

	public void close() {
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
		return connection;
	}


	public void initializeDB() {

		try {

			Flight f1 = new Flight("F1","Donostia","Bilbo");
			Flight f2 = new Flight("F2","Donostia","Madrid");
			Flight f3 = new Flight("F3","Barcelona","Donostia");
			Flight f4 = new Flight("F4","Barcelona","Malaga");
			Flight f5 = new Flight("F5","Malaga","Madrid");
			Flight f6 = new Flight("F6","Malaga","Santander");
			Flight f7 = new Flight("F7","Malaga","Granada");

			f1.addConcreteFlight("CF1-1",newDate(2025,1,23),0,2,3,"12:00");
			f1.addConcreteFlight("CF1-2",newDate(2025,1,23),3,0,3,"12:30");
			f1.addConcreteFlight("CF1-3",newDate(2025,1,23),1,2,2,"13:00");
			f1.addConcreteFlight("CF1-4",newDate(2025,1,23),3,3,0,"14:00");
			f1.addConcreteFlight("CF1-5",newDate(2025,1,23),3,3,0,"15:00");
			f1.addConcreteFlight("CF1-6",newDate(2025,1,23),3,3,0,"16:00");
			f1.addConcreteFlight("CF1-7",newDate(2025,1,23),3,3,0,"17:00");

			f2.addConcreteFlight("CF2-1",newDate(2025,1,23),1,2,3,"12:00");
			f2.addConcreteFlight("CF2-2",newDate(2025,1,23),3,0,3,"12:30");
			f2.addConcreteFlight("CF2-3",newDate(2025,1,23),1,2,2,"13:00");
			f2.addConcreteFlight("CF2-4",newDate(2025,1,23),3,3,0,"14:00");
			f2.addConcreteFlight("CF2-5",newDate(2025,1,23),3,3,0,"15:00");
			f2.addConcreteFlight("CF2-6",newDate(2025,1,23),3,3,0,"16:00");
			f2.addConcreteFlight("CF2-7",newDate(2025,1,23),3,3,0,"17:00");

			f3.addConcreteFlight("CF3-1",newDate(2025,1,23),1,2,3,"10:00");
			f3.addConcreteFlight("CF3-2",newDate(2025,1,23),3,0,3,"11:00");
			f3.addConcreteFlight("CF3-3",newDate(2025,1,23),1,2,2,"12:00");
			f3.addConcreteFlight("CF3-4",newDate(2025,1,23),3,3,0,"13:00");
			f3.addConcreteFlight("CF3-5",newDate(2025,1,23),3,3,0,"15:00");
			f3.addConcreteFlight("CF3-6",newDate(2025,1,23),3,3,0,"19:00");
			f3.addConcreteFlight("CF3-7",newDate(2025,1,23),3,3,0,"21:00");

			f4.addConcreteFlight("CF4-1",newDate(2025,1,22),1,2,3,"9:00");
			f4.addConcreteFlight("CF4-2",newDate(2025,1,23),3,0,3,"11:00");
			f4.addConcreteFlight("CF4-3",newDate(2025,1,23),1,2,2,"13:00");
			f4.addConcreteFlight("CF4-4",newDate(2025,1,23),3,3,0,"15:00");
			f4.addConcreteFlight("CF4-5",newDate(2025,1,23),3,3,0,"17:00");
			f4.addConcreteFlight("CF4-6",newDate(2025,1,23),3,3,0,"19:00");
			f4.addConcreteFlight("CF4-7",newDate(2025,1,23),3,3,0,"21:00");

			f5.addConcreteFlight("CF5-1",newDate(2025,1,22),1,2,3,"8:00");
			f5.addConcreteFlight("CF5-2",newDate(2025,1,23),3,0,3,"10:00");
			f5.addConcreteFlight("CF5-3",newDate(2025,1,23),1,2,2,"12:00");
			f5.addConcreteFlight("CF5-4",newDate(2025,1,23),3,3,0,"14:00");
			f5.addConcreteFlight("CF5-5",newDate(2025,1,23),3,3,0,"16:00");
			f5.addConcreteFlight("CF5-6",newDate(2025,1,23),3,3,0,"18:00");
			f5.addConcreteFlight("CF5-7",newDate(2025,1,23),3,3,0,"20:00");


			f6.addConcreteFlight("CF6-1",newDate(2024,1,22),1,2,3,"8:30");
			f6.addConcreteFlight("CF6-2",newDate(2024,1,23),3,0,3,"10:30");
			f6.addConcreteFlight("CF6-3",newDate(2024,1,23),1,2,2,"12:30");
			f6.addConcreteFlight("CF6-4",newDate(2024,1,23),3,3,0,"14:30");
			f6.addConcreteFlight("CF6-5",newDate(2024,1,23),3,3,0,"16:30");
			f6.addConcreteFlight("CF6-6",newDate(2024,1,23),3,3,0,"18:30");
			f6.addConcreteFlight("CF6-7",newDate(2024,1,23),3,3,0,"20:30");

			this.storeFlight(f1);
			this.storeFlight(f2);
			this.storeFlight(f3);
			this.storeFlight(f4);
			this.storeFlight(f5);
			this.storeFlight(f6);
			this.storeFlight(f7);

			
			System.out.println("Db initialized");

		}
		catch (Exception e){
			e.printStackTrace();
		}

	}



	public void storeFlight(Flight flight) {
		try {
			String insertFlight = "INSERT INTO Flight (flightCode, departingCity, arrivingCity) VALUES (?, ?, ?)";
			PreparedStatement stmtFlight = connection.prepareStatement(insertFlight);
			stmtFlight.setString(1, flight.getFlightCode());
			stmtFlight.setString(2, flight.getDepartingCity());
			stmtFlight.setString(3, flight.getArrivingCity());
			stmtFlight.executeUpdate();

			String insertConcrete = "INSERT INTO ConcreteFlight (code, date, businessNumber, firstNumber, touristNumber, hour, flightCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmtConcrete = connection.prepareStatement(insertConcrete);

			for (ConcreteFlight cf : flight.getConcreteFlights()) {
				stmtConcrete.setString(1, cf.getConcreteFlightCode());
				stmtConcrete.setDate(2, new java.sql.Date(cf.getDate().getTime()));
				stmtConcrete.setInt(3, cf.getBussinesNumber());
				stmtConcrete.setInt(4, cf.getFirstNumber());
				stmtConcrete.setInt(5, cf.getTouristNumber());
				stmtConcrete.setString(6, cf.getTime());
				stmtConcrete.setString(7, flight.getFlightCode());
				stmtConcrete.addBatch();
			}
			stmtConcrete.executeBatch();
			System.out.println("Vuelo almacenado: " + flight.getFlightCode());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ConcreteFlight getFlightByCode(String code) {
		try {
			String query = "SELECT * FROM ConcreteFlight WHERE code = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, code);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				ConcreteFlight cf = new ConcreteFlight();
				cf.setConcreteFlightCode(rs.getString("code"));
				cf.setDate(rs.getDate("date"));
				cf.setBusinessNumber(rs.getInt("businessNumber"));
				cf.setFirstNumber(rs.getInt("firstNumber"));
				cf.setTouristNumber(rs.getInt("touristNumber"));
				cf.setTime(rs.getString("hour"));
				return cf;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	private Date newDate(int year,int month,int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day,0,0,0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}	




	public ArrayList<Flight> getflights() {
		ArrayList<Flight> flights = new ArrayList<>();
		try {
			String query = "SELECT * FROM Flight";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				flights.add(new Flight(
					rs.getString("flightCode"),
					rs.getString("departingCity"),
					rs.getString("arrivingCity")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flights;
	}


	public void updateFlightByNameRestingSits(String code, int ticket, int num) {
		String field;

		switch (ticket) {
			case 1:
				field = "businessNumber";
				break;
			case 2:
				field = "firstNumber";
				break;
			default:
				field = "touristNumber";
		}

		try {
			String query = "UPDATE ConcreteFlight SET " + field + " = ? WHERE code = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, num - 1);
			stmt.setString(2, code);
			stmt.executeUpdate();
			System.out.println("Actualizado vuelo " + code);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	public List<String> getAllDepartingCities() {
		List<String> cities = new ArrayList<>();
		try {
			String query = "SELECT DISTINCT departingCity FROM Flight ORDER BY departingCity";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				cities.add(rs.getString("departingCity"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cities;
	}


	public List<String> getArrivalCitiesFrom(String departingCity) {
		List<String> cities = new ArrayList<>();
		try {
			String query = "SELECT DISTINCT arrivingCity FROM Flight WHERE departingCity = ? ORDER BY arrivingCity";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, departingCity);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				cities.add(rs.getString("arrivingCity"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cities;
	}

	public List<ConcreteFlight> getConcreteFlights(String departingCity, String arrivingCity, XMLGregorianCalendar date) {
		List<ConcreteFlight> flights = new ArrayList<>();
		try {
			String query = "SELECT cf.* FROM ConcreteFlight cf " +
			               "JOIN Flight f ON cf.flightCode = f.flightCode " +
			               "WHERE f.departingCity = ? AND f.arrivingCity = ? AND cf.date = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, departingCity.trim());
			stmt.setString(2, arrivingCity.trim());
			stmt.setDate(3, new java.sql.Date(date.toGregorianCalendar().getTimeInMillis()));
			
			ResultSet rs = stmt.executeQuery();

			boolean found = false;

			while (rs.next()) {
				found = true;
				System.err.println(" Vuelo encontrado: " + rs.getString("code"));

				ConcreteFlight cf = new ConcreteFlight();
				cf.setConcreteFlightCode(rs.getString("code"));
				cf.setDate(rs.getDate("date"));
				cf.setBusinessNumber(rs.getInt("businessNumber"));
				cf.setFirstNumber(rs.getInt("firstNumber"));
				cf.setTouristNumber(rs.getInt("touristNumber"));
				cf.setTime(rs.getString("hour"));
				flights.add(cf);
			}

			if (!found) {
				System.err.println(" No hay vuelos concretos encontrados para:");
				System.err.println(" - Desde: " + departingCity);
				System.err.println(" - Hacia: " + arrivingCity);
				System.err.println(" - Fecha: " + new java.sql.Date(date.toGregorianCalendar().getTimeInMillis()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flights;
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


}


