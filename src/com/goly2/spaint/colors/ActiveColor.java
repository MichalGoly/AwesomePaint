package com.goly2.spaint.colors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JPanel;

import com.goly2.spaint.DrawingArea;

public class ActiveColor extends JPanel {
	
	public static final int DEFAULT_WIDTH = 50;
	public static final int DEFAULT_HEIGHT = 50;
	
	private Color currentColor;
	private DrawingArea drawingArea;
	
	public ActiveColor(DrawingArea drawingArea) {
		this.drawingArea = drawingArea;
		currentColor = Color.BLACK;
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		
		// Add mouse handler
		addMouseListener(new MouseHandler());
	}
	
	/**
	 * Set current color and repaint JPanel to show the change.
	 * @param color
	 */
	public void setCurrentColor(Color color) {
		currentColor = color;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(currentColor);
		g2.fillOval(4, 4, 42, 42);
		g2.setPaint(Color.BLACK);
		g2.drawOval(4, 4, 42, 42);

	}
	
	private class MouseHandler extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent event) {
			Color newColor = JColorChooser.showDialog(getParent(), 
					"Choose custom color", currentColor);
			if (newColor != null) {
				setCurrentColor(newColor);			
				drawingArea.setColor(newColor);
			}
		}
	}
}
