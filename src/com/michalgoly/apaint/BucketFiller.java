package com.michalgoly.apaint;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class BucketFiller {
	
	private DrawingArea drawingArea;
	private UndoRedoManager manager;
	
	public BucketFiller(DrawingArea drawingArea, UndoRedoManager manager) {
		this.drawingArea = drawingArea;
		this.manager = manager;
	}
	
	public void apply(int x, int y) {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		manager.getMenuBar().setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply fill algorithm
		BufferedImage img = deepCopy(currentImg);
		int fillColor = drawingArea.getCurrentColor().getRGB();
		ArrayList<Point> examList = new ArrayList<Point>();
		
		int initialColor = img.getRGB(x, y);
		examList.add(new Point(x, y));
		
		Rectangle2D imgBounds = new Rectangle2D.Double(0, 0, img.getWidth(), img.getHeight());
		
		while (examList.size() > 0) {
			Point p = examList.remove(0);
			
			// make sure point is within the image
			if (!imgBounds.contains(p)) {
				continue;
			}
			
			try {
				if (img.getRGB(p.x, p.y) == initialColor) {
					x = p.x;
					y = p.y;
					img.setRGB(p.x, p.y, fillColor);
					
					examList.add(new Point(x - 1, y)); // check west neighbor
					examList.add(new Point(x + 1, y)); // check east neighbor
					examList.add(new Point(x, y - 1)); // check north neighbor
					examList.add(new Point(x, y + 1)); // check south neighbor
	
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				continue;
			}
		}
		
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
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
}
