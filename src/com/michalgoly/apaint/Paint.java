/**
 * First ever Paint application. Poor design but works!
 * @version 24-12-2013 v1.0
 * @author Michal Goly
 */
package com.michalgoly.apaint;

import java.awt.GridBagLayout;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import com.michalgoly.apaint.colors.ColorsPanel;
import com.michalgoly.apaint.tools.ToolsPanel;

public class Paint extends JFrame {
	
	public static final int DEFAULT_WIDTH = 750;
	public static final int DEFAULT_HEIGHT = 550;
	
	private DrawingArea drawingArea;
	private TopMenuBar menuBar;
	private ColorsPanel colorsPanel;
	private ToolsPanel toolsPanel;
	private JScrollPane scrollPane;
	
	public Paint() {
		// Create DrawingArea and put it in scrollPane
		drawingArea = new DrawingArea();
		scrollPane = new JScrollPane(drawingArea);
		
		// Add JMenuBar
		menuBar = new TopMenuBar(drawingArea, this);
		setJMenuBar(menuBar);
		
		// Construct colorPanel and toolsPanel
		colorsPanel = new ColorsPanel(drawingArea);
		toolsPanel = new ToolsPanel(drawingArea);
		
		// Set layout and add components into the frame
		initializeLayout();
		
		//Set frame
		initializeFrame();
	}
	
	public void initializeLayout() {
		setLayout(new GridBagLayout());
		add(toolsPanel, new GBC(0, 0, 16, 1).setWeight(
				0, 0).setFill(GBC.HORIZONTAL));
		add(colorsPanel, new GBC(0, 1, 1, 11)
				.setWeight(0, 100).setAnchor(GBC.NORTH));
		
		add(scrollPane, new GBC(1, 1, 15, 11).setFill(GBC.BOTH)
			.setWeight(100, 100));		
	}
	
	public void initializeFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle("Awesome Paint");
		try {
			setIconImage(ImageIO.read(getClass().getResource("/palette.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);	
	}
	
	public void resizeFrame(int imageWidth, int imageHeight) {
		int deltaX = 52;
		int deltaY = 92;
		setSize(imageWidth + deltaX, imageHeight + deltaY);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(
					"com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(
						"com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			} catch (Exception ex) {System.exit(0);}
		}
		new Paint();
	}
}
