package com.goly2.spaint.colors;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.goly2.spaint.DrawingArea;
import com.goly2.spaint.GBC;

/**
 * ColorsPanel is located on the western side of the JFrame. It is responsible
 * for colors choosing and it displays current color in a special ellipse area.
 * @author michal
 *
 */
public class ColorsPanel extends JPanel {
	
	private Palette palette;
	private ActiveColor activeColor;
	
	public ColorsPanel(DrawingArea drawingArea) {
		
		// Set layout
		setLayout(new GridBagLayout());		
		
		// Top label
		JLabel activeLabel = new JLabel("Active");
		add(activeLabel, new GBC(0, 0).setAnchor(GBC.NORTH));
		JLabel colorLabel = new JLabel("color");
		add(colorLabel, new GBC(0, 1).setAnchor(GBC.NORTH));
		
		//Init active color panel
		activeColor = new ActiveColor(drawingArea);
		add(activeColor, new GBC(0, 2).setAnchor(GBC.NORTH));
				
		// Init palette
		palette = new Palette(drawingArea, activeColor);
		add(palette, new GBC(0, 3).setAnchor(GBC.NORTH));
			
	}
}