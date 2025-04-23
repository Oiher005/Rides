package service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import dataAccess.DataAccess;
import domain.ConcreteFlight;
import domain.Flight;

@WebService(endpointInterface = "service.BLFacadeInterface")
public class BLFacadeImplementation implements BLFacadeInterface{

	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		dbManager= DataAccess.getInstance();

		
	}
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
//		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}


	@Override
	public void updateFlightByNameRestingSits(String kode, int ticket, int num) {
		System.out.println("Updating Sites of "+kode);
		dbManager.updateFlightByNameRestingSits(kode, ticket, num);
		
	}

	@Override
	public ArrayList<Flight> getflights() {
		System.out.println("Getting all the Flights");
		ArrayList<Flight> devol = dbManager.getflights();
		return devol;
	}

	@Override
	public ConcreteFlight getFlightByCode(String name) {
		System.out.println("Getting Flight with code: "+name);

		dbManager.getFlightByCode(name);
		return null;
	}

	@Override
	public void storeFlight(Flight hegaldi) {
		System.out.println("StoreFlight erabiltzen");

		dbManager.storeFlight(hegaldi);
	}

	@Override
	public void initializeDB() {
		System.out.println("DB sortzen...");

		dbManager.initializeDB();
	}




	@Override
	public List<String> getAllDepartingCities() {
		System.out.println("Getting all the departing cities");
		List<String> devol = dbManager.getAllDepartingCities();
		return devol;	}




	@Override
	public List<String> getArrivalCitiesFrom(String departingCity) {
		System.out.println("Getting all the Arrival cities");
		List<String> devol = dbManager.getArrivalCitiesFrom(departingCity);
		return devol;	
		}




	@Override
	public List<ConcreteFlight> getConcreteFlights(String departingCity, String arrivingCity, XMLGregorianCalendar date) {
		System.out.println("Getting all the concrete Flights");
		List<ConcreteFlight> devol = dbManager.getConcreteFlights(departingCity, arrivingCity, date);
		return devol;	}

}