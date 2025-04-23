package presentation;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import domain.ConcreteFlight;
import service.BLFacadeInterface;


public class FlightBooking extends JFrame {
	 

	private static final long serialVersionUID = 1L;
	private JPanel contentPane= null;
	private JLabel lblDepartCity = new JLabel("Departing city:");
	private JLabel lblArrivalCity = new JLabel("Arrival City");
	private JLabel lblYear = new JLabel("Year:");
	private JLabel lblRoomType = new JLabel("Room Type:");
	private JLabel lblMonth = new JLabel("Month:");
	private JLabel lblDay = new JLabel("Day:");;
	private JLabel jLabelResult = new JLabel();
	private JLabel searchResult =   new JLabel();
	

	
	private JComboBox<String> arrivalCity = null;
	private JComboBox<String> departCity = null;
	private DefaultComboBoxModel<String> cityNames1 = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> cityNames2 = new DefaultComboBoxModel<String>();
	private List<String> ArrayListgetArrivalCities=new ArrayList<String>();
	private List<String> ArrayListgetDepartingCities= new ArrayList<String>();
	private List<String> aux;

	private static JTextField day = null;
	private JComboBox<String> months = null;
	private DefaultComboBoxModel<String> monthNames = new DefaultComboBoxModel<String>();
	private static JTextField year = null;
	
	private JRadioButton bussinesTicket = null;
	private JRadioButton firstTicket = null;
	private JRadioButton touristTicket = null;

	private ButtonGroup fareButtonGroup = new ButtonGroup();  
	
	private JButton lookforFlights = null;
	private DefaultComboBoxModel<ConcreteFlight> flightInfo = new DefaultComboBoxModel<ConcreteFlight>();

	 
	private JComboBox<ConcreteFlight> flightList = null;
	private JButton bookFlight = null;
	

	
	private List<ConcreteFlight> concreteFlightCollection;
	
	
	private ConcreteFlight selectedConcreteFlight;
	
    private static BLFacadeInterface appFacadeInterface;
	public static BLFacadeInterface getBusinessLogic(){return appFacadeInterface;}
	public static void setBussinessLogic (BLFacadeInterface afi){appFacadeInterface=afi;}



	private static int getYearDay(int type) {
		int devol=-1;
		
		if (type==0) {
			try {
				devol=Integer.parseInt(year.getText());
				return devol;
			}catch (NumberFormatException e) {return 0;}
		}else {
			try {
				devol=Integer.parseInt(day.getText());
				return devol;
			}catch (NumberFormatException e) {return 0;}			
		} 
	}

	private void hustuGuztia() {
		bussinesTicket.setEnabled(false);
		touristTicket.setEnabled(false);
		firstTicket.setEnabled(false);
	}
				  	
	private XMLGregorianCalendar newDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(year, month, day, 0, 0, 0); 
		calendar.set(Calendar.MILLISECOND, 0);
		return toXMLGregorianCalendar(calendar.getTime());
		
	}
	
	private XMLGregorianCalendar toXMLGregorianCalendar(java.util.Date date) { GregorianCalendar gCalendar = new GregorianCalendar(); gCalendar.setTime(date);
	XMLGregorianCalendar xmlCalendar = null;
	try {
	xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
	} catch (DatatypeConfigurationException ex) {
	System.out.println(ex); }
	return xmlCalendar; }


    public FlightBooking() {
        super();
        
		BLFacadeInterface facade = FlightBooking.getBusinessLogic();


//		for ( String s: aux){
//			if (!ArrayListgetDepartingCities.contains(s)) { 
//				cityNames2.addElement(s);
//				ArrayListgetDepartingCities.add(s);
//			}
//		} 
		
//		ArrayListGetArrivalCities=facade.getArrivalCitiesFrom(getName())

		for ( String s: ArrayListgetArrivalCities ) cityNames1.addElement(s);



		setTitle("Book Flight");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 450, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblDepartCity = new JLabel("Depart City");
		lblDepartCity.setBounds(21, 11, 103, 16);
		contentPane.add(lblDepartCity);
		
		ArrayListgetDepartingCities=facade.getAllDepartingCities();
		for (String s: ArrayListgetDepartingCities) cityNames2.addElement(s);
		departCity = new JComboBox<String>();
		departCity.setBounds(99, 6, 243, 26);
		contentPane.add(departCity);
		departCity.setModel(cityNames2);

//		ArrayListgetArrivalCities=facade.getArrivalCitiesFrom(departCity.getName());
//		for (String s: ArrayListgetArrivalCities) cityNames1.addElement(s);

		
		List<String> aCities=facade.getArrivalCitiesFrom((String)departCity.getSelectedItem());
		for(String aciti:aCities) {cityNames1.addElement(aciti);}
		arrivalCity =new JComboBox<String>();
		arrivalCity.setBounds(99, 35, 243, 26);
		contentPane.add(arrivalCity);
		arrivalCity.setModel(cityNames1);
	
		
		departCity.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cityNames1.removeAllElements();

				List<String> aCities=facade.getArrivalCitiesFrom((String)departCity.getSelectedItem());
				for(String aciti:aCities) {cityNames1.addElement(aciti);}

			}
		});


		lblYear = new JLabel("Year:");
		lblYear.setBounds(21, 62, 33, 16);
		contentPane.add(lblYear);

		lblMonth = new JLabel("Month:");
		lblMonth.setBounds(117, 62, 50, 16);
		contentPane.add(lblMonth);

		months = new JComboBox<String>();
		months.setBounds(163, 58, 116, 27);
		contentPane.add(months);
		months.setModel(monthNames);

		monthNames.addElement("January");
		monthNames.addElement("February");
		monthNames.addElement("March");
		monthNames.addElement("April");
		monthNames.addElement("May");
		monthNames.addElement("June");
		monthNames.addElement("July");
		monthNames.addElement("August");
		monthNames.addElement("September");
		monthNames.addElement("October");
		monthNames.addElement("November");
		monthNames.addElement("December");
		months.setSelectedIndex(0);

		lblDay = new JLabel("Day:");
		lblDay.setBounds(291, 62, 38, 16);
		contentPane.add(lblDay);

		day = new JTextField();
		day.setText("23");
		day.setBounds(331, 57, 50, 26);
		contentPane.add(day);
		day.setColumns(10);

		lblRoomType = new JLabel("Seat Type:");
		lblRoomType.setBounds(21, 242, 84, 16);
		contentPane.add(lblRoomType);


		bussinesTicket = new JRadioButton("Business");
		bussinesTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bookFlight.setEnabled(true);
			}
		});
		bussinesTicket.setEnabled(false);
		fareButtonGroup.add(bussinesTicket);
		bussinesTicket.setBounds(99, 238, 101, 23);
		contentPane.add(bussinesTicket);

		firstTicket = new JRadioButton("First");
		firstTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bookFlight.setEnabled(true);
			}
		});
		firstTicket.setEnabled(false);
		fareButtonGroup.add(firstTicket);
		firstTicket.setBounds(202, 238, 77, 23);
		contentPane.add(firstTicket);

		touristTicket = new JRadioButton("Tourist");
		touristTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookFlight.setEnabled(true);
			}
		});
		touristTicket.setEnabled(false);
		fareButtonGroup.add(touristTicket);
		touristTicket.setBounds(278, 238, 77, 23);
		contentPane.add(touristTicket);

		lookforFlights = new JButton("Look for Concrete Flights");
		lookforFlights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flightInfo.removeAllElements();
				bookFlight.setText("");
				flightList.setEnabled(true);


				XMLGregorianCalendar date =newDate(Integer.parseInt(year.getText()),months.getSelectedIndex(),Integer.parseInt(day.getText()));
				concreteFlightCollection = facade.getConcreteFlights((String)departCity.getSelectedItem(),(String)arrivalCity.getSelectedItem(),date);				
				Iterator<ConcreteFlight> flights=concreteFlightCollection.iterator();
				while (flights.hasNext()) 
					flightInfo.addElement(flights.next()); 
				if (concreteFlightCollection.isEmpty()) searchResult.setText("No flights in that city in that date");
				else searchResult.setText("Choose an available flight in this list:");
			}
		}); 
		lookforFlights.setBounds(81, 90, 261, 40);
		contentPane.add(lookforFlights);	

		jLabelResult = new JLabel("");
		jLabelResult.setBounds(109, 180, 243, 16);
		contentPane.add(jLabelResult);

		flightList = new JComboBox<ConcreteFlight>();

		flightList.setEnabled(false);
		flightList.setBounds(46, 158, 243, 26);
		flightList.setModel(flightInfo);
		contentPane.add(flightList);
		flightList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selectedConcreteFlight = (ConcreteFlight) flightList.getSelectedItem();


				if(!(selectedConcreteFlight==null)) {

					if (selectedConcreteFlight.getBussinesNumber()==0) bussinesTicket.setEnabled(false);
					else bussinesTicket.setEnabled(true);

					if (selectedConcreteFlight.getFirstNumber()==0) firstTicket.setEnabled(false);
					else firstTicket.setEnabled(true);

					if (selectedConcreteFlight.getTouristNumber()==0) touristTicket.setEnabled(false);
					else touristTicket.setEnabled(true);

					bookFlight.setText("Book: "+selectedConcreteFlight); 
				}
			}

		});


		bookFlight = new JButton("");
		bookFlight.setEnabled(false);
		bookFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num=0;
				boolean error=false;
				if (bussinesTicket.isSelected()) { 
					num=selectedConcreteFlight.getBussinesNumber();
					if (num>0) {
						facade.updateFlightByNameRestingSits(selectedConcreteFlight.getConcreteFlightCode(),  1, num);
						selectedConcreteFlight.setBusinessNumber(num-1);
					}
					else error=true; 
				}
				if (firstTicket.isSelected()) {
					num=selectedConcreteFlight.getFirstNumber();
					if (num>0) {
						facade.updateFlightByNameRestingSits(selectedConcreteFlight.getConcreteFlightCode(),  2, num);
						selectedConcreteFlight.setFirstNumber(num-1);
					}
					else error=true;
				}
				if (touristTicket.isSelected()) {
					num=selectedConcreteFlight.getTouristNumber();
					if (num>0) {
						facade.updateFlightByNameRestingSits(selectedConcreteFlight.getConcreteFlightCode(),  3, num);
						selectedConcreteFlight.setTouristNumber(num-1);
					}
					else error=true;
				} 

				if (error) {
					bookFlight.setText("Error: There were no seats available!");
					hustuGuztia();
				}
				else {
					bookFlight.setText("Booked. #seat left: "+(num-1));
					hustuGuztia();
				}
				bookFlight.setEnabled(false);
			}
		});
		bookFlight.setBounds(31, 273, 399, 40);
		contentPane.add(bookFlight);

		year = new JTextField();
		year.setText("2025");
		year.setBounds(57, 57, 50, 26);
		contentPane.add(year);
		year.setColumns(10);

		lblArrivalCity.setBounds(21, 39, 84, 16);
		contentPane.add(lblArrivalCity);

		searchResult.setBounds(57, 130, 314, 16);
		contentPane.add(searchResult);
	}    
}