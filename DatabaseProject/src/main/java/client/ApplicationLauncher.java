package client;

import java.net.URL;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import presentation.FlightBooking;
import service.BLFacadeImplementation;
import service.BLFacadeInterface;

public class ApplicationLauncher {
    private static BLFacadeInterface facade;
	private static DataAccess da ;
	public static BLFacadeInterface getFacade() {return facade;}
	public static void setFacade(BLFacadeInterface facade) {ApplicationLauncher.facade = facade;}

	public static DataAccess getDa() {return da;}
	public static void setDa(DataAccess da) {ApplicationLauncher.da = da;}

    public static void main(String[] args) {
    	
    	ConfigXML c=ConfigXML.getInstance();

    	try { 

    		BLFacadeInterface appFacadeInterface;
    		UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

    		if (c.isBusinessLogicLocal()) {
    			da = DataAccess.getInstance(); 
    			appFacadeInterface=new BLFacadeImplementation(da);


    		}

    		else { 

    			String serviceName= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName()+"?wsdl";
    			URL url = new URL(serviceName);
    			QName qname = new QName("http://service/", "BLFacadeImplementationService");
    			Service service = Service.create(url, qname);
    			appFacadeInterface = service.getPort(BLFacadeInterface.class);
    		} 

    		FlightBooking.setBussinessLogic(appFacadeInterface);




    	}catch (Exception e) {

    		System.out.println("Error in ApplicationLauncher: "+e.toString());
    	}
    	
    	FlightBooking a = new FlightBooking();
    	a.setVisible(true); 

    }

}
