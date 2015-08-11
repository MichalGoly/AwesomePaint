package com.michalgoly.apaint.tools;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.michalgoly.apaint.DrawingArea;

public class SizeSlider extends JPanel {
	
	public static final int MINIMUM_VALUE = 1;
	public static final int MAXIMUM_VALUE = 50;
	public static final int INITIAL_VALUE = 10;
	
	private DrawingArea drawingArea;
	private JSlider sizeSlider;
	
	public SizeSlider(DrawingArea drawingArea) {
		this.drawingArea = drawingArea;
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		// Add label
		add(new JLabel("Tool size:"));
		
		// Create and add JSlider
		sizeSlider = new JSlider(MINIMUM_VALUE, MAXIMUM_VALUE, INITIAL_VALUE);
		sizeSlider.setPaintTicks(true);
		sizeSlider.addChangeListener(new SliderHandler());
		sizeSlider.setPreferredSize(new Dimension(260, 50));
		add(sizeSlider);
	}
	
	private class SliderHandler implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			drawingArea.setDrawingToolSize(source.getValue());			
		}
	}
	
}
