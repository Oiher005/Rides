package service;


public class AppFacadeManager {

    private static BLFacadeInterface facade;

    public static void setBusinessLogic(BLFacadeInterface f) {
        facade = f;
    }

    public static BLFacadeInterface getBusinessLogic() {
        return facade;
    }

	
	
}
