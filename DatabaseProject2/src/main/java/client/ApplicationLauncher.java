package client;

import java.net.URL;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import presentation.DatabaseProjectManager;
import service.AppFacadeManager;
import service.BLFacadeImplementation;
import service.BLFacadeInterface;

public class ApplicationLauncher {
	private static DataAccess da ;

	public static DataAccess getDa() {return da;}
	public static void setDa(DataAccess da) {ApplicationLauncher.da = da;}

	public static void main(String[] args) {

		ConfigXML c=ConfigXML.getInstance();

		try { 

			BLFacadeInterface appFacadeInterface;
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			da = DataAccess.getInstance();
			appFacadeInterface=new BLFacadeImplementation(da);

			AppFacadeManager.setBusinessLogic(appFacadeInterface);

		}catch (Exception e) {System.out.println("Error in ApplicationLauncher: "+e.toString());}

		DatabaseProjectManager a = new DatabaseProjectManager();
		a.setVisible(true); 

	}

}
