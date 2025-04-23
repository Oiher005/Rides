package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.*;
import javax.xml.bind.annotation.*;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "ConcreteFlight")
public class ConcreteFlight implements Serializable{
	@Id
	@XmlID
    @Column(name = "concreteFlightCode")
	private String concreteFlightCode;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "bussinesNumber")
    private int bussinesNumber;
    @Column(name = "touristNumber")
    private int touristNumber;

    @Column(name = "firstNumber")
    private int firstNumber;
    @Column(name = "time")
    private String time;

	@OneToOne(fetch = FetchType.LAZY)
	@XmlIDREF
    @JoinColumn(name = "flight_id") // FK que apunta a Flight.flightCode
    private Flight flight;

	/*
	 *ConcreteFlight bat hauek guztiak osatutako super() bat da:
	 *	FlightCodea, data, businessCodea, zenbat bidaiari hartu dezakeen (business, first eta tourist) eta flight.
	 */
	public ConcreteFlight(String concreteFlightCode, Date date, int businessNumber,int firstNumber, int touristNumber, String time, Flight flight) {
		super();
		this.concreteFlightCode=concreteFlightCode;
		this.date = date;
		this.bussinesNumber = businessNumber;
		this.firstNumber = firstNumber;
		this.touristNumber = touristNumber;
		this.flight = flight;
		this.time=time;
		this.flight=flight;
	}
	public ConcreteFlight() {}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setBussinesNumber(int bussinesNumber) {
		this.bussinesNumber = bussinesNumber;
	}
	/*
	 *get eta seterrak hemen.
	 */
	public String getConcreteFlightCode() {
		return concreteFlightCode;
	}

	public void setConcreteFlightCode(String concreteFlightCode) {
		this.concreteFlightCode = concreteFlightCode;
	}


	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getBussinesNumber() {
		return bussinesNumber;
	}
	public void setBusinessNumber(int businessNumber) {
		this.bussinesNumber = businessNumber;
	}
	public int getTouristNumber() {
		return touristNumber;
	}
	public void setTouristNumber(int touristNumber) {
		this.touristNumber = touristNumber;
	}
	public int getFirstNumber() {
		return firstNumber;
	}
	public void setFirstNumber(int firstNumber) {
		this.firstNumber = firstNumber;
	}

	public String toString() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		//Add one to month {0 - 11}
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		return (year)+"/"+(month)+"/"+day+"/"+time+"-->"+bussinesNumber+"/"+firstNumber+"/"+touristNumber;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}
}