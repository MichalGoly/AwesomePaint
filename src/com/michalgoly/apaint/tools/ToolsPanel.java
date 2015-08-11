package com.michalgoly.apaint.tools;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.michalgoly.apaint.DrawingArea;

/**
 * ToolsPanel extends JPanel and it's responsible for tool's layout. It also 
 * provides method which checks which JButton is selected and returns its
 * instance afterwards.
 * @author michal
 *
 */
public class ToolsPanel extends JPanel {
	
	private DrawingArea drawingArea;
	
	private JButton brush;
	private JButton eraser;
	private JButton line;
	private JButton bucket;
	private JButton shapes;
	private JPanel sizeSliderPanel;
	
	public ToolsPanel(DrawingArea drawingArea) {
		this.drawingArea = drawingArea;
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));		
		
		// Initialize tools
		initTools();
		
		// Initialize slider
		initSlider();
		
	}
	
	public JButton getShapesButton() {
		return shapes;
	}
	
	/**
	 * Initialize tools and add them to JPanel.
	 */
	private void initTools() {
		// Set brush
		brush = new JButton(new ImageIcon(getClass()
				.getResource("/brush.png")));
		brush.addActionListener(new BrushListener());
		brush.setToolTipText("Brush");
		
		// Set eraser
		eraser = new JButton(new ImageIcon(getClass()
				.getResource("/eraser.png")));
		eraser.addActionListener(new EraserListener());
		eraser.setToolTipText("Eraser");
		
		// Set line button
		line = new JButton(new ImageIcon(getClass()
				.getResource("/line.png")));
		line.addActionListener(new LineListener());
		line.setToolTipText("Line");
		
		// Set bucket listener
		bucket = new JButton(new ImageIcon(getClass()
				.getResource("/bucket.png")));
		bucket.addActionListener(new BucketListener());
		bucket.setToolTipText("Bucket fill");
		
		// Set shapes button
		shapes = new JButton(new ImageIcon(getClass()
				.getResource("/shapes.png")));
		shapes.addActionListener(new ShapesListener());
		shapes.setToolTipText("Shapes");
		
		// Add components to the panel
		add(brush);
		add(eraser);
		add(line);
		add(bucket);
		add(shapes);
	}
	
	private void initSlider() {
		sizeSliderPanel = new SizeSlider(drawingArea);
		add(sizeSliderPanel);
	}
	
	private class BrushListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			drawingArea.selectBrush();
		}
	}
	
	private class EraserListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			drawingArea.selectEraser();
		}
	}
	
	private class LineListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			drawingArea.selectLine();
		}	
	}
	
	private class ShapesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ShapesChooser shapesChooser = new ShapesChooser();
			if (shapesChooser.showDialog(shapes, "Create new shape")) {
				drawingArea.selectShapes();
				drawingArea.setFill(shapesChooser.isFilled());
				drawingArea.setCurrentShape(shapesChooser.getCurrentShape());
			}
		}	
	}
	
	private class BucketListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			drawingArea.selectBucketFill();
		}		
	}
}
