package com.goly2.spaint;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ImageSizeChooser extends JPanel {
	
	private JDialog dialog;
	private JButton okButton;
	private JTextField widthField;
	private JTextField heightField;
	
	private boolean ok;
	private boolean illegalValues;
	
	private int enteredWidth;
	private int enteredHeight;
	
	public ImageSizeChooser() {
		setLayout(new BorderLayout());
		dialog = null;
		
		// JPanel with fields to enter width and height
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		panel.add(new JLabel("               Width:"));
		panel.add(widthField = new JTextField("750"));
		panel.add(new JLabel("               Height:"));
		panel.add(heightField = new JTextField("550"));
		add(panel, BorderLayout.CENTER);
		
		// Button panel
		JPanel buttonPanel = new JPanel();
		okButton = new JButton(new AbstractAction("Ok") {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				illegalValues = false;
				
				// Check if entered values are OK
				try {
					enteredWidth = Integer.parseInt(widthField.getText());
					enteredHeight = Integer.parseInt(heightField.getText());
				} catch (NumberFormatException exception) {
					illegalValues = true;
					JOptionPane.showMessageDialog(getParent(), 
							"You've entered illegal values!", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				
				if (!illegalValues) {
					ok = true;
					dialog.setVisible(false);
				}	
			}
		});
		
		JButton cancelButton = new JButton(new AbstractAction("Cancel") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public int getEneterdWidth() {
		return enteredWidth;
	}
	
	public int getEneterdHeight() {
		return enteredHeight;
	}
	
	public boolean showDialog(Component parent, String title) {
		ok = false;
		
		// Set owner
		Frame owner = null;
		if (parent instanceof Frame) owner = (Frame) parent;
		else owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class,
				parent);
		
		// If first time
		if (dialog == null || dialog.getOwner() != owner) {
			dialog = new JDialog(owner, true);
			dialog.add(this);
			dialog.getRootPane().setDefaultButton(okButton);
			dialog.setSize(240, 120);
			dialog.setResizable(false);
		}
		
		// Set title and show dialog
		dialog.setLocationRelativeTo(null);
		dialog.setTitle(title);
		dialog.setVisible(true);
		return ok;
	}
}
