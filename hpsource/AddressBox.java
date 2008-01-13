package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * An entry form box to add/edit an address for a profile.
 *
 * @author Brandon Tanner
 * @since  1.0 (Jan 13, 2008)
 */
public class AddressBox extends JFrame implements ActionListener {
    
    private Person     p;
    private JPanel     adrPanel;
    private JComboBox  adrType;
    private JTextField adrLabel;
    private JTextField adrLine1;
    private JTextField adrLine2;
    private JTextField adrLine3;
    private JTextField adrCity;
    private JTextField adrState;
    private JTextField adrZip;
    private JComboBox  adrCountry;
    private JTextField adrDistrict;
    private JLabel     l;
    private JButton    btnSave;
    private JButton    btnCancel;
    private String     actionText;

    public AddressBox(Frame owner, Person person, boolean edit) {
        super();
        
        
        if (person.getPersonID() > 0) {
            this.p = person;
        }
        else {
            Debug.turnOn();
            Debug.print("No Person Supplied to Add/Edit Address For, exiting app.");
            System.exit(0);
        }
        
        actionText = "Add";
        if (edit) {
            actionText = "Edit";
        }
        this.setTitle(actionText+" Address for "+person.getFirstName());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        
        Container cp = this.getContentPane();
        
        // Intialize vars
        adrPanel    = new JPanel(new SpringLayout());
        adrType     = new JComboBox();
        adrLabel    = new JTextField(10);
        adrLine1    = new JTextField(10);
        adrLine2    = new JTextField(10);
        adrLine3    = new JTextField(10);
        adrCity     = new JTextField(10);
        adrState    = new JTextField(10);
        adrZip      = new JTextField(10);
        adrCountry  = new JComboBox();
        adrDistrict = new JTextField(10);       
        btnSave     = new JButton(actionText);
        btnCancel   = new JButton("Cancel");
        btnSave     .addActionListener(this);
        btnCancel   .addActionListener(this);
        
        adrPanel.setBorder(BorderFactory.createTitledBorder(actionText+" Address"));
        
        // Populate Types Combo Box
        for (Object value : AddressType.getAddressTypes()) {
            adrType.addItem(value);
        }
        
        // Populate Country Combo Box
        for (Object value : DBHPInterface.getListOfCountries()) {
            adrCountry.addItem(value);
        }
        
        l = new JLabel("Address Type: ", JLabel.TRAILING);
        adrPanel.add(l);
        l.setLabelFor(adrType);
        adrPanel.add(adrType);
        
        l = new JLabel("Mailing Label: ", JLabel.TRAILING);
        adrPanel.add(l);
        l.setLabelFor(adrLabel);
        adrPanel.add(adrLabel);

        l = new JLabel("Address Line 1: ", JLabel.TRAILING);
        adrPanel.add(l);
        l.setLabelFor(adrLine1);
        adrPanel.add(adrLine1);
        
        l = new JLabel("Address Line 2: ", JLabel.TRAILING);
        adrPanel.add(l);
        l.setLabelFor(adrLine2);
        adrPanel.add(adrLine2);
        
        l = new JLabel("Address Line 3: ", JLabel.TRAILING);
        adrPanel.add(l);
        l.setLabelFor(adrLine3);
        adrPanel.add(adrLine3);
        
        l = new JLabel("City: ", JLabel.TRAILING);
        adrPanel.add(l);
        l.setLabelFor(adrCity);
        adrPanel.add(adrCity);
        
        l = new JLabel("State/Province: ", JLabel.TRAILING);
        adrPanel.add(l);
        l.setLabelFor(adrState);
        adrPanel.add(adrState);

        l = new JLabel("Zip/Postal Code: ", JLabel.TRAILING);
        adrPanel.add(l);
        l.setLabelFor(adrZip);
        adrPanel.add(adrZip);
        
        l = new JLabel("District: ", JLabel.TRAILING);
        adrPanel.add(l);
        l.setLabelFor(adrDistrict);
        adrPanel.add(adrDistrict);
        
        l = new JLabel("Country: ", JLabel.TRAILING);
        adrPanel.add(l);
        l.setLabelFor(adrCountry);
        adrPanel.add(adrCountry);
        
        adrPanel.add(btnSave);
        adrPanel.add(btnCancel);
        
        //Lay out the panel.
        SpringUtilities.makeCompactGrid(adrPanel,
                                        11, 2,   //rows, cols
                                        3, 3,   //initX, initY
                                        3, 3);  //xPad, yPad
        
               
        cp.add(adrPanel);
        
        this.pack();
        
        // Set the location of the About Window centered relative to the MainMenu
        // --CENTER--
        Point aboutBoxLocation = new Point();
        
        double aboutBoxX = owner.getLocation().getX() + ((owner.getWidth() / 2) - (this.getWidth() / 2));
        double aboutBoxY = owner.getLocation().getY() + ((owner.getHeight() / 2) - (this.getHeight() / 2));
        
        aboutBoxLocation.setLocation(aboutBoxX, aboutBoxY);
        this.setLocation(aboutBoxLocation);
        // --END CENTER--
        
        this.setVisible(true);
    }
    
    /** Performs any actions this object has */
    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
        if (pressedButton == btnCancel) {
            this.dispose();
        }
        else if (pressedButton == btnSave) {
            if (actionText.equals("Edit")) {
                // Not Implemented Yet
            }
            else {
                // Need to Check for Required Parts first ...
                // Add New Address for Person
                Address a = new Address();
                a.setAddressType((KeyValue)adrType.getSelectedItem());
                a.setAddressLabel(adrLabel.getText().trim());
                a.setAddressLine1(adrLine1.getText().trim());
                a.setAddressLine2(adrLine2.getText().trim());
                a.setAddressLine3(adrLine3.getText().trim());
                a.setAddressCity(adrCity.getText().trim());
                a.setAddressState(adrState.getText().trim());
                a.setAddressZip(adrZip.getText().toLowerCase());
                a.setAddressDistrict(adrDistrict.getText().trim());
                a.setAddressCountry((KeyValue)adrCountry.getSelectedItem());
                this.p.addAddress(a);
                if (this.p.saveToDatabase()) {
                    Debug.print("Address Successfully Added");
                }
                else {
                    Debug.print("Error Adding Address for ID "+p.getPersonID());
                }
                
            }
            this.dispose();
        }
    }   
    
}