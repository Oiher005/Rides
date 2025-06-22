package service;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebService;

import dataAccess.DataAccess;

@WebService(endpointInterface = "service.BLFacadeInterface")
public class BLFacadeImplementation implements BLFacadeInterface{

	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		dbManager= DataAccess.getInstance();


	}
	public BLFacadeImplementation(DataAccess da)  {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		dbManager=da;		
	}

  
	@Override
	public void openDB() throws ClassNotFoundException,SQLException {
		System.out.println("Openning Database");
		dbManager.open();
	}
	
	@Override
	public void closeDB() throws SQLException{
		System.out.println("Closing Database");
		dbManager.close();
	}
	
	@Override
	public String RunQuery(String query) {
		return dbManager.RunQuery(query);
	}
	
	@Override
	public List<String> getLogs() {
		return dbManager.getLogs();
	}

}