package converter;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class Converter {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Converter");
        //Add icon to the window
        Image icon = Toolkit.getDefaultToolkit().getImage("icons/convert.png");    
        frame.setIconImage(icon);    
         
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
     // Create a new master panel
        JPanel masterPanel = new JPanel();
        
     // Use a box layout to stack the panels
        masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.PAGE_AXIS));
       
        MainPanel panel = new MainPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
        frame.setJMenuBar(panel.setupMenu());
        
        frame.pack();
        //Center the window to screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
       
        //*********************************Create the new currency panel********************************
        CurrencyPanel currencyPanel = new CurrencyPanel();
       
        // Add the sub-panels to the master panel
        masterPanel.add(panel);
        masterPanel.add(currencyPanel);
		currencyPanel.setBorder(new TitledBorder(null, "currency converter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panel.currencyPanel = currencyPanel;

		currencyPanel.mainPanel = panel;
		
        // Add the master panel to the frame
        frame.getContentPane().add(masterPanel);
        currencyPanel.setLayout(null);
      
        frame.pack();
        frame.setVisible(true);
        
        
    }
}

