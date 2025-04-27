package service;
import java.sql.SQLException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface BLFacadeInterface {
	@WebMethod
	public void openDB()throws ClassNotFoundException,SQLException;
	@WebMethod
	public void closeDB()throws SQLException;
	@WebMethod
	public String RunQuery(String query);
	@WebMethod
	public List<String> getLogs();


}
