package converter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	public CurrencyPanel currencyPanel;

	private final static String[] comboList = { "inches/cm", "miles/nautical miles", "acres/hectares", "miles per hour/km per hour", "yards/metres", "celcius/fahrenheit", "degrees/radians" };
	private JTextField textField;
	private JLabel resultLabel;
	private JLabel inputLabel;
	private JLabel comboLabel;
	private JComboBox<String> comboBox;
	private JFrame aboutFrame;
	private JPanel aboutPanel;
	private JButton convertButton;
	private JButton resetButton;
	private JLabel counter;
	private JCheckBox reverse;
	private int count = 0;
	
	//Function for making dialogue box which is shown when 'about' menu item is clicked.
	void aboutDialogueBox(){
		aboutFrame = new JFrame();
		aboutPanel = new JPanel();
		
		JLabel info = new JLabel(""
				+ "<html>"
					+ "<p style=\"font-size: 14px;\">This application allows the conversion between different units.</p>"
					+ "<p style=\"text-align: center; font-size: 12px;\">Author : Samyak Maharjan<br/>@ Copyright 2019</p>"
				+ "</html>");
		
		aboutPanel.add(info);
		JOptionPane.showMessageDialog(aboutFrame,aboutPanel);
	}

	//Making menu
	JMenuBar setupMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		//File menu
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		
		//Help menu
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		
		//Load menu
		JMenu loadFile = new JMenu("Load");
		loadFile.setMnemonic(KeyEvent.VK_L);
		
		loadFile.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent arg0) {
				System.out.println("Cancelled");
			}
			public void menuDeselected(MenuEvent arg0) {
				System.out.println("Deselected");
			}
			public void menuSelected(MenuEvent e) {
				JFileChooser chooseFile = new JFileChooser();
				chooseFile.showOpenDialog(null);
				File file = chooseFile.getSelectedFile();
				currencyPanel.loadCurrencyFile(file);
			}
		});

		menuBar.add(file);
		menuBar.add(help);
		menuBar.add(loadFile);
		
		//Menu item - Exit inside File menu
		JMenuItem fileItem = new JMenuItem("Exit");
		//icon beside the exit menu item
		fileItem.setIcon(new ImageIcon("icons/exit.png"));
		//Add accelerator to Exit menu item
		fileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		
		//Close application when Exit is clicked
		fileItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		file.add(fileItem);
		
		//Menu item - Help inside Help menu
		JMenuItem helpItem = new JMenuItem("About");
		//icon beside about menu item
		helpItem.setIcon(new ImageIcon("icons/about.png"));
		//Add accelerator to About menu item
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		
		//Open about dialogue box when about is clicked
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutDialogueBox();
			}
		});
		help.add(helpItem);

		return menuBar;
	}
	
	void incConversionCount(){
		count++;
		counter.setText("Converison count: " +Integer.toString(count));
	}
	
	boolean isReverseSelected() {
		boolean reverseSelected = reverse.isSelected();
		return reverseSelected;
	}


	MainPanel() {
		//Object instantiation of ConvertListener class
		ActionListener listener = new ConvertListener();
		
		//Create label for combo box
		comboLabel = new JLabel("Select your choice: ");
		comboLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		comboLabel.setForeground(SystemColor.textHighlight);
		comboLabel.setBounds(31, 29, 187, 27);
		
		//Create the combo box
		comboBox = new JComboBox<String>(comboList);
		comboBox.setToolTipText("Select your Choice here");
		comboBox.addActionListener(listener); //convert values when option changed
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 14));
		comboBox.setForeground(SystemColor.textHighlight);
		comboBox.setBounds(273, 29, 237, 27);

		//Create label for text field
		inputLabel = new JLabel("Enter value:");
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
		
		//Create the reset button
		resetButton = new JButton("Clear");
		resetButton.setToolTipText("Click here to reset values.");
		resetButton.setMnemonic(KeyEvent.VK_R);
		resetButton.setBackground(SystemColor.textHighlight);
		resetButton.setForeground(Color.WHITE);
		resetButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		resetButton.setBounds(273, 232, 119, 29);
		
		//Set components into their default state
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText(null);
				reverse.setSelected(false);
				resultLabel.setText("Result: -");
				counter.setText("Converison count: 0");
				count = 0;
				
				currencyPanel.clearValues();
			}
		});
		
		//Create the counter label
		counter = new JLabel("Converison count: " + count);
		counter.setToolTipText("Show number of operations performed.");
		counter.setForeground(SystemColor.textHighlight);
		counter.setFont(new Font("Tahoma", Font.BOLD, 18));
		counter.setBounds(31, 236, 200, 27);
		
		//Create the reverse check box
		reverse = new JCheckBox("Reverse");
		reverse.setForeground(SystemColor.textHighlight);
		reverse.setFont(new Font("Tahoma", Font.BOLD, 18));
		reverse.setToolTipText("Check to reverse the operation.");
		reverse.setBounds(410, 117, 200, 29);
		
		//Add all the created components in panel
		add(comboLabel);
		add(comboBox);
		add(inputLabel);
		add(textField);
		add(convertButton);
		add(resultLabel);
		add(resetButton);
		add(counter);
		add(reverse);

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

				// the offset applied during the conversion.
				double offset = 0;

				// Setup the correct factor/offset values depending on required conversion
				switch (comboBox.getSelectedIndex()) {

				case 0: // inches/cm
					if(isReverseSelected()) {
						factor = 1/2.54;
					}else {
						factor = 2.54;
					}
					break;
				
				case 1: // miles/nautical miles
					if(isReverseSelected()) {
						factor = 1.151;
					}else {
						factor = 1/1.151;
					}
					break;
					
				case 2: // acres/hectares
					if(isReverseSelected()) {
						factor = 2.471;
					}else {
						factor = 1/2.471;
					}
					break;
					
				case 3: // metres per hour/kilometres per hour
					if(isReverseSelected()) {
						factor = 1/1.609;
					}else {
						factor = 1.609;
					}
					break;
					
				case 4: // yards /metres
					if(isReverseSelected()) {
						factor = 1.094;
					}else {
						factor = 1/1.094;
					}
					break;
					
				case 5: //celcius/fahrenheit
					if(isReverseSelected()) {
						factor = 5.0/9.0;
						offset = 32;
					}else {
						factor = 9.0/5.0;
						offset = 32;
					}
					break;
				
				case 6: //degree/radian
					if(isReverseSelected()) {
						factor = 180/Math.PI;
					}else {
						factor = Math.PI/180;
					}
					break;
				}
				double result;
				if(comboBox.getSelectedIndex() == 5 && isReverseSelected()) {
					result = (value - offset) * factor;
				}else {
					result = factor * value + offset;
				}
				
				// Show up to rounded two decimal places in result
				DecimalFormat resultDf = new DecimalFormat("#.##");
				resultDf.setRoundingMode(RoundingMode.CEILING);
				String finalResult = resultDf.format(result);
				
				//Increase and update the counter
				incConversionCount();

				//Update label that shows result
				resultLabel.setText("Result: "+finalResult);
			}
		}
	}

}
