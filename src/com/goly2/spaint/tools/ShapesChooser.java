package com.goly2.spaint.tools;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.goly2.spaint.GBC;

public class ShapesChooser extends JPanel {
	
	private JDialog dialog;
	private JButton okButton;
	private JSlider slider;
	private JRadioButton yesButton;
	private JRadioButton noButton;
	
	private boolean ok;
	
	private Shapes currentShape;
	private boolean fill;
	
	public ShapesChooser() {
		setLayout(new BorderLayout());
		dialog = null;
		currentShape = Shapes.OVAL; 
		fill = false;
		
		// Create central panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		Border etched = BorderFactory.createEtchedBorder();
		Border titled = BorderFactory.createTitledBorder(etched, "Shape");
		
		JPanel shapeSliderPanel = new JPanel();
		shapeSliderPanel.setBorder(titled);		
		shapeSliderPanel.setPreferredSize(new Dimension(300, 120));
		
		// add slider to shapeSliderPanel
		slider = new JSlider(0, 150);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);
		slider.setMajorTickSpacing(75);
		slider.setMinorTickSpacing(75);
		
		Dictionary<Integer, Component> shapesTable = 
				new Hashtable<Integer, Component>();
		shapesTable.put(0, new JLabel(new ImageIcon(getClass()
				.getResource("/shapes/oval.png"))));
		shapesTable.put(75, new JLabel(new ImageIcon(getClass()
				.getResource("/shapes/rectangle.png"))));
		shapesTable.put(150, new JLabel(new ImageIcon(getClass()
				.getResource("/shapes/triangle.png"))));
		
		slider.setLabelTable(shapesTable);
		shapeSliderPanel.add(slider);
		
		panel.add(shapeSliderPanel, new GBC(0, 0, 1, 4).setFill(GBC.BOTH)
				.setWeight(100, 100));
		panel.setPreferredSize(new Dimension(300, 220));
		
		// Radio buttons
		JPanel radioPanel = new JPanel();
		ButtonGroup radioGroup = new ButtonGroup();
		yesButton = new JRadioButton("Yes");
		noButton = new JRadioButton("No", true);
		radioPanel.add(yesButton);
		radioPanel.add(noButton);
		radioGroup.add(yesButton);
		radioGroup.add(noButton);
		

		titled = BorderFactory.createTitledBorder(etched, "Fill");
		radioPanel.setBorder(titled);
		radioPanel.setPreferredSize(new Dimension(300, 40));
		panel.add(radioPanel, new GBC(0, 4, 1, 1).setFill(GBC.BOTH)
				.setWeight(100, 100).setAnchor(GBC.CENTER));
		
		// Southern JButtons
		JPanel buttonPanel = new JPanel();
		okButton = new JButton(new AbstractAction("Ok") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (yesButton.isSelected()) fill = true;
				else fill = false;
				
				switch (slider.getValue()) {
					case 0:
						currentShape = Shapes.OVAL;
						break;
					case 75:
						currentShape = Shapes.RECTANGLE;
						break;
					case 150:
						currentShape = Shapes.TRIANGLE;
						break;
					default:
						// Should not happen
						new Exception("Unexpected slider int value")
							.printStackTrace();
						break;
				}
				
				ok = true;
				dialog.setVisible(false);
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
		
		// Add central and southern panels to main panel
		add(panel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public Shapes getCurrentShape() {
		return currentShape;
	}
	
	public boolean isFilled() {
		return fill;
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
			dialog.pack();
			dialog.setResizable(false);
		}
		
		// Set title and show dialog
		dialog.setLocationRelativeTo(parent);
		dialog.setTitle(title);
		dialog.setVisible(true);
		return ok;		
	}
	
	
}
