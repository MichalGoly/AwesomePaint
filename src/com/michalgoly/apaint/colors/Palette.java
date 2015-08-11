package com.michalgoly.apaint.colors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.michalgoly.apaint.DrawingArea;
/**
 * Palette with colors. paintComponent method is overridden to allow this. 
 * @author michal
 *
 */
public class Palette extends JPanel {
	
	public static final int DEFAULT_WIDTH = 50;
	public static final int DEFAULT_HEIGHT = 214;
	
	private DrawingArea drawingArea;
	private ActiveColor activeColor;
	private Color[] allColors;
	private Point[] recPoints;
	private Rectangle[] squares;
		
	private final int SIDE = 17;
	
	public Palette(DrawingArea drawingArea, ActiveColor activeColor) {
		// Set default settings
		this.drawingArea = drawingArea;
		this.activeColor = activeColor;
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

		// Fill arays with data
		allColors = new Color[] {Color.BLACK, Color.WHITE, Color.GRAY,
				Color.LIGHT_GRAY, new Color(136, 0, 21), 
				new Color(185, 122, 87), new Color(237, 28, 36), 
				new Color(255, 174, 201), new Color(255, 127, 39), 
				new Color(255, 201, 14), new Color(255, 242, 0), 
				new Color(239, 228, 176), new Color(34, 177, 76), 
				new Color(181, 230, 29), new Color(0, 162, 232), 
				new Color(153, 217, 234), new Color(63, 72, 204), 
				new Color(112, 146, 190), new Color(163, 73, 164), 
				new Color(200, 191, 231)};
		
		recPoints = new Point[] {new Point(4, 4), new Point(29, 4), 
				new Point(4, 25), new Point(29, 25), new Point(4, 46), 
				new Point(29, 46), new Point(4, 67), new Point(29, 67), 
				new Point(4, 88), new Point(29, 88), new Point(4, 109), 
				new Point(29, 109), new Point(4, 130), new Point(29, 130),
				new Point(4, 151), new Point(29, 151), new Point(4, 172), 
				new Point(29, 172), new Point(4, 193), new Point(29, 193)};
		
		squares = new Rectangle[recPoints.length];
		for (int i = 0; i < recPoints.length; i++) {
			squares[i] = new Rectangle(recPoints[i].x, recPoints[i].y, SIDE,
					SIDE);
		}
		
		// Add mouse listener
		addMouseListener(new MouseHandler());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		for (int i = 0; i < allColors.length; i++) {
			int x = recPoints[i].x;
			int y = recPoints[i].y;
			
			g2.setColor(allColors[i]);
			g2.fillRect(x, y, SIDE, SIDE);
			
			g2.setColor(Color.BLACK);
			g2.drawRect(x, y, SIDE, SIDE);
		}
	}
	
	private class MouseHandler extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent event) {
			if (event.getButton() == MouseEvent.BUTTON1) {
				for (int i = 0; i < squares.length; i++) {
					if (squares[i].contains(event.getPoint())) {
						drawingArea.setColor(allColors[i]);
						activeColor.setCurrentColor(allColors[i]);
						break;
					}
				}
			}
		}
	}
}
