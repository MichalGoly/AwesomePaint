package com.goly2.spaint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.goly2.spaint.tools.CursorLoader;
import com.goly2.spaint.tools.Shapes;

/**
 * An area that is responsible for painting.
 * 
 * @author michal
 * 
 */
public class DrawingArea extends JPanel {

	// Brush settings
	private int drawingToolSize;
	private BasicStroke roundStroke;
	private BasicStroke squareStroke;
	private Color currentColor;
	
	// Current shape
	private Shapes currentShape;
	private boolean fill;
	
	// Logical values which represents chosen tool
	private boolean brushPressed;
	private boolean eraserPressed;
	private boolean linePressed;
	private boolean shapesPressed;
	private boolean magicPressed;
	private boolean bucketFillPressed;
	
	// Coordianates used in drawing
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	
	// Graphics and image that we draw on
	private Graphics2D graphics2D;
	private BufferedImage currentImage;
	
	// Manager that provides undo/redo operations
	private UndoRedoManager manager;
	// Checks if image is freshly loaded or not
	private boolean newImg;
	
	private boolean shouldDrawImage = true;
	
	// Loads tools cursors
	private CursorLoader cursorLoader;
	
	// Filters
	private Magic currentMagic;
	
	// BucketFiller
	private BucketFiller bucketFiller;
	
	public DrawingArea() {
		// Default settings
		currentColor = Color.BLACK;
		drawingToolSize = 10;
		currentShape = null;
		fill = false;
		roundStroke = new BasicStroke(drawingToolSize, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL);
		squareStroke = new BasicStroke(drawingToolSize);
		graphics2D = null;
		currentImage = null;
		brushPressed = true;
		eraserPressed = false;	
		linePressed = false;
		shapesPressed = false;
		magicPressed = false;
		bucketFillPressed = false;
		manager = null;
		newImg = true;
		cursorLoader = new CursorLoader();
		
		// Add mouse listener
		addMouseMotionListener(new MouseMotionHandler());
		addMouseListener(new MouseHandler());
	}

	/**
	 * Sets current color of e.g brush
	 * @param color Current color
	 */
	public void setColor(Color color) {
		currentColor = color;
	}
	
	/**
	 * User decides if shape should be drawn or not
	 * @param b
	 */
	public void setShouldDrawImage(boolean b) {
		shouldDrawImage = b;
	}
	
	public void setNewImageEnabled(boolean b) {
		newImg = b;
	}
	
	/**
	 * Sets current size of e.g brush
	 * @param toolSize
	 */
	public void setDrawingToolSize(int toolSize) {
		drawingToolSize = toolSize;
		roundStroke = new BasicStroke(drawingToolSize, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL);
		squareStroke = new BasicStroke(drawingToolSize);
	}
	
	/**
	 * Set value of the currentImage Object. Method is used to apply undo/redo
	 * usability on DrawingArea. 
	 * @param currentImage
	 */
	public void setCurrentImage(BufferedImage currentImage) {
		this.currentImage = currentImage;
		graphics2D = this.currentImage.createGraphics();
		repaint();
	}
	
	/**
	 * Set currentShape Enum value, which is used in drawing method.
	 * @param shape selected shape
	 */
	public void setCurrentShape(Shapes shape) {
		currentShape = shape;
	}
	
	public void setManager(UndoRedoManager manager) {
		this.manager = manager;
	}
	
	/**
	 * Select if shape should be filled or not
	 * @param fill
	 */
	public void setFill(boolean fill) {
		this.fill = fill;
	}
		
	/**
	 * Get copy of currentImage
	 * @return copy of currentImage
	 */
	public BufferedImage getCurrentImage() {
		if (currentImage != null){
			return deepCopy(currentImage);
		} else return null;	
	}
	
	/**
	 * Return current color
	 * @return
	 */
	public Color getCurrentColor() {
		return currentColor;
	}
	
	/**
	 * Get instance of UndoRedoManager
	 * @return
	 */
	public UndoRedoManager getUndoRedoManager() {
		if (manager != null) return manager;
		else {
			new NullPointerException().printStackTrace();
			return null;
		}
	}
	
	/**
	 * Selects brush by changing appropriate boolean values in DrawingArea
	 * object.
	 */
	public void selectBrush() {
		brushPressed = true;
		eraserPressed = false;
		linePressed = false;
		shapesPressed = false;
		magicPressed = false;
		bucketFillPressed = false;
		cursorLoader.setCursor(this, CursorLoader.BRUSH_CURSOR);
		cursorLoader.setCursorInitiated(true);
	}

	/**
	 * Selects eraser by changing appropriate boolean values in DrawingArea
	 * object.
	 */
	public void selectEraser() {
		brushPressed = false;
		eraserPressed = true;
		linePressed = false;
		shapesPressed = false;
		magicPressed = false;
		bucketFillPressed = false;
		cursorLoader.setCursor(this, CursorLoader.ERASER_CURSOR);		
		cursorLoader.setCursorInitiated(true);
	}
	
	/**
	 * Selects line by changing appropriate boolean values in DrawingArea
	 * object.
	 */
	public void selectLine() {
		brushPressed = false;
		eraserPressed = false;
		linePressed = true;
		shapesPressed = false;
		magicPressed = false;
		bucketFillPressed = false;
		cursorLoader.setCursor(this, CursorLoader.LINE_CURSOR);
		cursorLoader.setCursorInitiated(true);
		
		currentShape = Shapes.LINE;
	}
	
	/**
	 * Selects shapes by changing appropriate boolean values in DrawingArea
	 */
	public void selectShapes() {
		brushPressed = false;
		eraserPressed = false;
		linePressed = false;
		shapesPressed = true;
		magicPressed = false;
		bucketFillPressed = false;
		cursorLoader.setCursor(this, CursorLoader.SHAPES_CURSOR);
		cursorLoader.setCursorInitiated(true);	
	}
	
	public void selectMagic(Magic magic, FiltersManager mng) {
		brushPressed = false;
		eraserPressed = false;
		linePressed = false;
		shapesPressed = false;
		magicPressed = true;
		bucketFillPressed = false;
		cursorLoader.setCursor(this, CursorLoader.MAGIC_CURSOR);
		cursorLoader.setCursorInitiated(true);
		currentMagic = magic;
	}
	
	public void selectBucketFill() {
		brushPressed = false;
		eraserPressed = false;
		linePressed = false;
		shapesPressed = false;
		magicPressed = false;
		bucketFillPressed = true;
		cursorLoader.setCursor(this, CursorLoader.BUCKET_CURSOR);
		cursorLoader.setCursorInitiated(true);
	}
	
	/**
	 * Creates new image
	 * 
	 * @param bImage new image
	 */
	public void newImage(BufferedImage bImage) {
		newImg = true;
		currentImage = bImage;
		graphics2D = currentImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// cursor manipulation
		if (!cursorLoader.isCursorInitiated()) {
			cursorLoader.setCursor(this, CursorLoader.BRUSH_CURSOR);
			cursorLoader.setCursorInitiated(true);
		}
		
		clearArea();
	}
	
	/**
	 * Opens image 
	 * @param bImage
	 */
	public void openImage(BufferedImage bImage) {
		newImg = true;
		currentImage = bImage;
		graphics2D = currentImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		// cursor manipulation
		if (!cursorLoader.isCursorInitiated()) {
			cursorLoader.setCursor(this, CursorLoader.BRUSH_CURSOR);
			cursorLoader.setCursorInitiated(true);
		}

		
		// Undo/Redo
		manager.setUndoImg(deepCopy(currentImage));
		manager.setRedoImg(null);
		manager.getMenuBar().setUndoItemEnabled(false);
		manager.getMenuBar().setRedoItemEnabled(false);
		
		graphics2D.setPaint(currentColor);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (currentImage == null) return;
		Graphics2D g2 = (Graphics2D) g;

		g2.drawImage(currentImage, 0, 0, null);
		
		if ((shapesPressed || linePressed) && shouldDrawImage) {
			drawCurrentShape(g2);
		}
	}

	/**
	 * Fills graphics object with white color.
	 */
	public void clearArea() {
		graphics2D.setPaint(Color.WHITE);
		graphics2D.fillRect(0, 0, currentImage.getWidth(), currentImage
				.getHeight());		
		// Undo/Redo 
		manager.setUndoImg(deepCopy(currentImage));
		manager.setRedoImg(null);
		manager.getMenuBar().setUndoItemEnabled(false);
		manager.getMenuBar().setRedoItemEnabled(false);
		
		shouldDrawImage = false;
		
		graphics2D.setPaint(currentColor);
		repaint();
	}

	/**
	 * HELP METHOD Draw line from given coordinates.
	 */
	private void line(int x1, int y1, int x2, int y2) {
		graphics2D.setPaint(currentColor);
		graphics2D.setStroke(roundStroke);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (eraserPressed) {
			graphics2D.setPaint(Color.WHITE);
			graphics2D.setStroke(new BasicStroke(20));
		}
		graphics2D.draw(new Line2D.Double(x1, y1, x2, y2));
		repaint();
	}
	
	/**
	 * Draws current shape
	 * @param g2
	 */
	private void drawCurrentShape(Graphics2D g2) {	
		g2.setPaint(currentColor);
		g2.setStroke(squareStroke);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		switch (currentShape) {
			case TRIANGLE:			
				if (fill) {
					g2.fill(new Polygon(
							new int[] {startX, endX, 
									endX - ((endX - startX) / 2)}, 
							new int[] {endY, endY, startY}, 3));
				} else {
					g2.draw(new Polygon(
							new int[] {startX, endX, 
									endX - ((endX - startX) / 2)}, 
							new int[] {endY, endY, startY}, 3));
				}
				break;
			case RECTANGLE:
				if (fill) {
					g2.fill(new Polygon(
							new int[] {startX, endX, endX, startX},
							new int[] {endY, endY, startY, startY}, 4));
				} else {
					g2.draw(new Polygon(
							new int[] {startX, endX, endX, startX},
							new int[] {endY, endY, startY, startY}, 4));
				}
				break;
			case OVAL:
				if (fill) {
					g2.fillOval(startX, startY,
								endX - startX,
								endY - startY);
				} else {
					g2.drawOval(startX, startY,
								endX - startX,
								endY - startY);
				}
				break;
			case LINE:
				g2.draw(new Line2D.Double(startX, startY, endX, endY));
				break;
			default:
				// Should never happen
				new Exception("Illegal shape value!")
					.printStackTrace();
				break;
		}
	}
	
	/**
	 * HELP METHOD Clones BufferedImage
	 */
	private BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	private class MouseHandler extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent event) {
			if (graphics2D != null && event.getButton() == MouseEvent.BUTTON1) {
				
				// Undo/Redo
				if (!newImg) {
					manager.setUndoImg(deepCopy(currentImage));
				}
				newImg = false;
				manager.getMenuBar().setUndoItemEnabled(true);
				manager.setRedoImg(null);
				manager.getMenuBar().setRedoItemEnabled(false);
				
				// Brush action
				if (brushPressed || eraserPressed) {
					startX = event.getX();
					startY = event.getY();

					// Draw point
					line(startX, startY, startX,
							startY);
				}
				
				// Shapes action
				if (shapesPressed || linePressed) {
					startX = event.getX();
					startY = event.getY();
				}
				
				// Bucket fill action
				if (bucketFillPressed) {
					if (bucketFiller == null) {
						bucketFiller = new BucketFiller(DrawingArea.this,
								manager);
					}
					
					startX = event.getX();
					startY = event.getY();
					bucketFiller.apply(startX, startY);				
				}
				
				// Magic action TODO
				if (magicPressed) {
					startX = event.getX();
					startY = event.getY();
					System.out.println("Magic pressed");
					switch (currentMagic) {
						
							
						default:
							// Should not happen
							new Exception("Invalid Magic Enum value")
								.printStackTrace();
					}
					selectBrush();
				}
			}
		}	
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if ((graphics2D != null) && (shapesPressed || linePressed)) {
				endX = e.getX();
				endY = e.getY();
				
				drawCurrentShape(graphics2D);
				shouldDrawImage = true;
				DrawingArea.this.repaint();
			}
		}
	}

	private class MouseMotionHandler extends MouseMotionAdapter {

		@Override
		public void mouseDragged(MouseEvent event) {
			if (graphics2D != null && SwingUtilities.isLeftMouseButton(event)) {

				// Brush action
				if (brushPressed || eraserPressed) {
					endX = event.getX();
					endY = event.getY();

					// Draw line
					line(startX, startY, endX, endY);

					startX = endX;
					startY = endY;
				}
				
				// Shapes action
				if ((shapesPressed || linePressed)&& currentShape != null) {
					shouldDrawImage = true;
					
					endX = event.getX();
					endY = event.getY();
					DrawingArea.this.repaint();
				}
			}
		}
	}
}
