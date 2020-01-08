package converter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import converter.MainPanel.ConvertListener;

public class CurrencyPanel extends JPanel {
	private static String[] comboList = {"Euro (EU)","US Dollars (USD)", "Australian Dollars (AUD)", 
			"Canadian Dollars (CAD)", "Icelandic Krona (ISK)", 
			"United Arab Emirates Dirham (AED)", "South African Rand (ZAR)", "Thai Baht (THB)"};
	private JTextField textField;
	private JLabel comboLabel;
	private JComboBox<String> comboBox;
	private JLabel resultLabel;
	private JLabel inputLabel;
	private JButton convertButton;
	private Boolean fileLoaded = false;
	ArrayList<String> errors = new ArrayList<String>();
    ArrayList<Currency> currencyList = new ArrayList<Currency>();
	
	public MainPanel mainPanel;
	
	void clearValues(){
		textField.setText(null);
		resultLabel.setText("Result: -");
	}
	
	Boolean validate(String line) {
		Boolean errorOccured = false;
		String errorLine = "";
		//check if length is 3
		String [] parts = line.split(",");
		if(parts.length != 3) {
			if(errorLine == "") {
				errorLine = line;
			}
			errorLine = errorLine + "<br/>* Require two commas";
			errorOccured = true;
		}
		
		//check if factor is double
		try {
			double factor = Double.parseDouble(parts[1].trim());
		}catch(NumberFormatException e) {
			if(errorLine == "") {
				errorLine = line;
			}
			errorLine = errorLine + "<br/>* Given factor is not valid";
			errorOccured = true;
//			e.printStackTrace();
		}
		
		//check if the symbol is valid currency symbol
		try {
			String symbol = parts[2].trim();
	    	String regex = "\\p{Sc}";
	    	Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	    	Matcher matcher = pattern.matcher(symbol);
	    	if(!matcher.find()) {
	    		if(errorLine == "") {
					errorLine = line;
				}
	    		errorLine = errorLine + "<br/>* Given currency symbol is invalid";
				errorOccured = true;
	    	}
		}catch(Exception e) {
//			e.printStackTrace();
		}
		
		if(errorLine != "") {
			errors.add(errorLine);
		}
		
		return errorOccured;
	}
	
	void errorDialogBox() {
		String errorParagraph = "<html>";
		for(String error: errors) {
			errorParagraph = errorParagraph + "<p style=\"text-align: center; font-size: 12px; margin-bottom: 5px\">" + error + "</p>";
		}
		errorParagraph = errorParagraph + "</html>";
		
		JLabel errorInfo = new JLabel(errorParagraph);
		
		JOptionPane.showMessageDialog(null, errorInfo);
	}
	
	void loadCurrencyFile(File file) { 
        try {
        	String filename = file.getAbsolutePath();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"));
            String line = in.readLine();
            Currency currencies = null;
            ArrayList<String> newComboList = new ArrayList<String>();
            
            // reset the array list currencyList when file is reloaded
            if(currencyList.size() > 0) {
            	currencyList.clear();
            }
            
            
            while ( line != null ) {
            	if(!validate(line)) {
            		String [] parts = line.split(",");
                    newComboList.add(parts[0].trim());
                    currencies = new Currency(parts[0].trim(), Double.parseDouble(parts[1].trim()), parts[2].trim());
                    currencyList.add(currencies); 
            	}
                
                line = in.readLine(); 
            }
            //convert array list of string to array of string
            String[] currencyArray = new String[newComboList.size()];
            currencyArray = newComboList.toArray(currencyArray);
            
            //assigning new combo list to existing combo list
            comboList = currencyArray;
            comboBox.removeAllItems();
            for(String comboItem:comboList){
            	comboBox.addItem(comboItem);
            }
            
            // set file is loaded
            fileLoaded = true;
            //show errors occurred via dialogue box
            if(errors.size() > 0) {
            	errorDialogBox();
            	errors.clear();
            }
//            for(Currency currency:currencyList) {
//            	char charSymbol = symbol.charAt(1);
//                System.out.print(Character.getType(charSymbol) == Character.CURRENCY_SYMBOL);  
//            }
            in.close();
        } catch (Exception e) {
//        	e.printStackTrace();
//            String msg = e.getMessage();
        }
	}
	
	CurrencyPanel(){
		//Object instantiation of ConvertListener class
		ActionListener listener = new ConvertListener();
		
		//Create label for combo box
		comboLabel = new JLabel("Select currency: ");
		comboLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		comboLabel.setForeground(SystemColor.textHighlight);
		comboLabel.setBounds(31, 29, 187, 27);
		
		//Create the combo box
		comboBox = new JComboBox<String>(comboList);
		comboBox.setToolTipText("Select your Choice here");
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 14));
		comboBox.setForeground(SystemColor.textHighlight);
		comboBox.setBounds(273, 29, 237, 27);
		
		//Create label for text field
		inputLabel = new JLabel("Enter british pounds:");
		inputLabel.setForeground(SystemColor.textHighlight);
		inputLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		inputLabel.setBounds(31, 73, 237, 27);

		//Create the convert button
		convertButton = new JButton("Convert");
		convertButton.setToolTipText("Click here to perform conversion.");
		convertButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		convertButton.setBackground(SystemColor.textHighlight);
		convertButton.setForeground(Color.WHITE);
		convertButton.setBounds(273, 117, 119, 29);
		
		convertButton.addActionListener(listener); // convert values when pressed

		//Create label for the result
		resultLabel = new JLabel("Result: -");
		resultLabel.setForeground(new Color(32, 178, 170));
		resultLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		resultLabel.setBounds(31, 175, 398, 27);
		
		//Create the text field that takes input for conversion
		textField = new JTextField(5);
		textField.setToolTipText("Enter a number for the conversion.");
		textField.setBounds(273, 73, 237, 27);
		textField.setColumns(10);
		
		//Do conversion when return/Enter key is pressed
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if(event.getKeyCode() == KeyEvent.VK_ENTER) {
					convertButton.doClick();
				}
			}
		});

		add(comboLabel);
		add(comboBox);
		add(inputLabel);
		add(textField);
		add(convertButton);
		add(resultLabel);
		
		setPreferredSize(new Dimension(600, 300));
	}
	
	private class ConvertListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			//Get input from the text field
			String text = textField.getText().trim();
			
			//Check if the text field is empty
			if (text.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Text Field seems to be empty. Please enter the value!");
				return;
			}else {
				double value;
				//Check if the text field has valid number
				try {
					value = Double.parseDouble(text);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Entered number is invalid!");
					return;
				}
	
				// the factor applied during the conversion
				double factor = 0;
				String symbol = "";
				// Setup the correct factor/offset values depending on required conversion
				// run this part when file is not loaded
				
				if(!fileLoaded) {
					switch (comboBox.getSelectedIndex()) {
					
					case 0: // Euro
						symbol = "€";
						if(mainPanel.isReverseSelected()) {
							factor = 1/1.06;
						}else {
							factor = 1.06;
						}
						break;
					
					case 1: // American Dollars
						symbol = "$";
						if(mainPanel.isReverseSelected()) {
							factor = 1/1.14;
						}else {
							factor = 1.14;
						}
						break;
						
					case 2: // Australian Dollars
						symbol = "$";
						if(mainPanel.isReverseSelected()) {
							factor = 1/1.52;
						}else {
							factor = 1.52;
						}
						break;
						
					case 3: // Canadian Dollars
						symbol = "$";
						if(mainPanel.isReverseSelected()) {
							factor = 1/1.77;
						}else {
							factor = 1.77;
						}
						break;
						
					case 4: //Icelandic Krona
						symbol = "kr";
						if(mainPanel.isReverseSelected()) {
							factor = 1/130.62;
						}else {
							factor = 130.62;
						}
						break;
						
					case 5: //United Arab Emirates Dirham
						symbol = "د.إ";
						if(mainPanel.isReverseSelected()) {
							factor = 1/4.60;
						}else {
							factor = 4.60;
						}
						break;
					
					case 6: //South African Rand
						symbol = "R";
						if(mainPanel.isReverseSelected()) {
							factor = 1/17.96;
						}else {
							factor = 17.96;
						}
						break;
						
					case 7: //Thai baht
						symbol = "฿";
						if(mainPanel.isReverseSelected()) {
							factor = 1/44.28;
						}else {
							factor = 44.28;
						}
						break;
					}
					
				}
				
				
				// if file is loaded then
				if(fileLoaded) {
					for(Currency currency : currencyList) {
						if(comboBox.getSelectedIndex() == currencyList.indexOf(currency)) {
							symbol = currency.getSymbol();
							if(mainPanel.isReverseSelected()) {
								factor = 1/currency.getFactor();
							}else {
								factor = currency.getFactor();
							}
						}
					}
				}
				
				if(mainPanel.isReverseSelected()) {
					symbol = "£";
				}
				
				double result;
				String finalResult;
				
				result = factor * value;
				
				// Show up to rounded two decimal places in result
				DecimalFormat resultDf = new DecimalFormat("#.##");
				resultDf.setRoundingMode(RoundingMode.CEILING);
				String formattedResult = resultDf.format(result);
				
				if((comboBox.getSelectedIndex() == 4 || comboBox.getSelectedIndex() == 5) && !mainPanel.isReverseSelected()) {
					finalResult = formattedResult + symbol;
				}else {
					finalResult = symbol + formattedResult;
				}
			
				//Update label that shows result
				resultLabel.setText("Result: "+finalResult);
				mainPanel.incConversionCount();
			
			}
		}
	}

	
}
