package gui;

import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;

public class ApplicationLauncher {

    public static void main(String[] args) {
        try {
          
            Locale.setDefault(new Locale("es"));

            System.out.println("Locale configurado: " + Locale.getDefault());

            
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

           
            BLFacade appFacadeInterface = new BLFacadeImplementation();

            
            MainGUI mainGUI = new MainGUI();

            
            MainGUI.setBussinessLogic(appFacadeInterface);

            
            mainGUI.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error en ApplicationLauncher: " + e.toString());
            e.printStackTrace();
        }
    }
}