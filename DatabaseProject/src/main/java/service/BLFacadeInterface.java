package service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import domain.Flight;
import domain.ConcreteFlight;
@WebService
public interface BLFacadeInterface {
	@WebMethod
	public void updateFlightByNameRestingSits(String kode, int ticket, int num);
	@WebMethod
	public ArrayList<Flight> getflights();
	@WebMethod
	public ConcreteFlight getFlightByCode(String name);
	@WebMethod
	public void storeFlight(Flight hegaldi);
	@WebMethod
	public void initializeDB();
	@WebMethod
	public List<String> getAllDepartingCities();
	@WebMethod
	public List<String> getArrivalCitiesFrom(String departingCity);
	@WebMethod
	public List<ConcreteFlight> getConcreteFlights(String departingCity, String arrivingCity, XMLGregorianCalendar date);

}
