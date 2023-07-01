
package com.iqbal.crud;

import com.iqbal.db.dbRow;
import com.iqbal.tools.Utils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author user
 */
public class CustomerForm extends JDialog {
    private String ObjectKeyID = "";
    public CustomerForm(JFrame parent, String title){
        super(parent);
        setTitle(title);
        setModal(true);
        this.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        initComponents();
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parent);
    }
    
    private Map<String, JTextField> entries;
    private JPanel form;
    private void initComponents(){
        Border padding = BorderFactory.createEmptyBorder(5,5,5,5);
        ((JComponent) getContentPane()).setBorder(padding);
        
        entries = new HashMap<String, JTextField>();
        form = new JPanel(new MigLayout("insets 5"));
        addFormElement("CustomerId", "CustomerId", new JTextField());
        addFormElement("ShortName", "ShortName", new JTextField());
        addFormElement("FullName", "FullName", new JTextField());
        addFormElement("Address1", "Address1", new JTextField());
        addFormElement("Address2", "Address2", new JTextField());
        addFormElement("Address3", "Address3", new JTextField());
        addFormElement("City", "City", new JTextField());
        addFormElement("PostalCode", "PostalCode", new JTextField());
        
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerForm.this.save();
            }
            
        });
        form.add(btnSave, "span, right, push, gapy 10, wrap");
        
        getContentPane().add(form, BorderLayout.CENTER);
    }
    
    private void addFormElement(String label, String field, JTextField el){
        form.add(new JLabel(label));
        form.add(el, "w 200, wrap");
        entries.put(field, el);
    }
    
    public void save(){
        dbRow values = getEntries();
        Utils.stdout(values);
        if(!validate(values)){
            return;
        }
        
        if(!ObjectKeyID.equals("")){
            values.put("id", ObjectKeyID);
        }
        
        int saved = Launcher.getDatabaseConnection().save("customer", "id", values);
        if(saved > 0){
            setVisible(false);
            Utils.alert("Saved Data");
        } else {
            Utils.alert("Failed to save data");
        }
    }
    
    private boolean validate(dbRow values){
        for(String s : values.keySet()){
        	
			/*
			 * if(values.get(s).trim().isEmpty()){ Utils.alert("All fields are required.");
			 * return false; }
			 */
        	
        	String address1 = values.get("Address1");
        	String address2 = values.get("Address2");
        	String address3 = values.get("Address3");
        	
        	//System.out.println("......"+address3.length());
        	
        	 if (address1.length()>80) {
        		 Utils.alert("Address 1 fields should not more than 80 character.");
                 return false;
			 }
        	 if (address2.length()>80) {
        		 Utils.alert("Address 2 fields should not more than 80 character.");
                 return false;
			 }

        	 if (address3.length()>80) {
        		 Utils.alert("Address 3 fields should not more than 80 character.");
                 return false;
			 }
        	 if(values.get("PostalCode").trim().isEmpty()){
                 Utils.alert("Postal Code fields are required.");
                 return false;
             }


        }
        return true;
    }
    
    private dbRow getEntries(){
        dbRow values = new dbRow();
        for(String name : entries.keySet()){
            values.put(name, entries.get(name).getText());
        }
        
        return values;
    }
    
    public void loadData(String id){
        dbRow data = Customers.getMember(id);
        if(data == null){
            setVisible(false);
            return;
        }
        
        ObjectKeyID = id;
        for(String field : data.keySet()){
            if(entries.containsKey(field)){
                entries.get(field).setText(data.get(field));
            }
        }
    }
}
