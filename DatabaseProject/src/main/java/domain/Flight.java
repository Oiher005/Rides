package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "Flight") 

public class Flight implements Serializable{
	@Id
	@XmlID
    @Column(name = "flightCode")
    private String flightCode;
    @Column(name = "departingCity")
    private String departingCity;
    @Column(name = "arrivingCity")
    private String arrivingCity;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<ConcreteFlight> concreteFlights;



	public Flight(String flightCode, String departingCity, String arrivingCity) {
		super();

		this.flightCode = flightCode;
		this.departingCity = departingCity;
		this.arrivingCity = arrivingCity;
		concreteFlights = new ArrayList<ConcreteFlight>();
	}

	public Flight() {}

	public String getFlightCode() {
		return flightCode;
	}
	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}
	public String getDepartingCity() {
		return departingCity;
	}
	public void setDepartingCity(String departingCity) {
		this.departingCity = departingCity;
	}
	public String getArrivingCity() {
		return arrivingCity;
	}
	public void setArrivingCity(String arrivingCity) {
		this.arrivingCity = arrivingCity;
	}

	public void addConcreteFlight(String concreteFlighCode, Date date, int businessNumber, int firstNumber, int touristNumber, String time){
		ConcreteFlight cf=new ConcreteFlight(concreteFlighCode,date, businessNumber,firstNumber,touristNumber,time, this);
		concreteFlights.add(cf);
	}

	public Collection<ConcreteFlight> getConcreteFlights() {
		return concreteFlights;
	}

	public void setConcreteFlights(List<ConcreteFlight> concreteFlights) {
		this.concreteFlights = concreteFlights;
	}
	public String toString() {return flightCode+"/"+departingCity+"/"+arrivingCity;}
}

