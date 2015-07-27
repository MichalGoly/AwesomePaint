package com.goly2.spaint;

import java.awt.image.BufferedImage;

/**
 * Provides undo/redo usability and ensures that those actions are permitted.
 * It also stores undo and redo Buffered Images. 
 * @author michal
 *
 */
public class UndoRedoManager {
	
	private DrawingArea drawingArea;
	private TopMenuBar menuBar;
	
	private BufferedImage undoImg;
	private BufferedImage redoImg;
	
	public UndoRedoManager(DrawingArea drawingArea, TopMenuBar menuBar) {
		this.drawingArea = drawingArea;
		this.menuBar = menuBar;
		undoImg = null;
		redoImg = null;
	}
	
	/**
	 * Save previous image
	 * @param undoImg
	 */
	public void setUndoImg(BufferedImage undoImg) {
		this.undoImg = undoImg;
	}
	
	/**
	 * Save redo image
	 * @param redoImg
	 */
	public void setRedoImg(BufferedImage redoImg) {
		this.redoImg = redoImg;
	}
	
	/**
	 * Provides access to menuBar for DrawingArea
	 * @return menuBar
	 */
	public TopMenuBar getMenuBar() {
		return menuBar;
	}
	
	public void undo() {
		if (undoImg != null) {
			redoImg = drawingArea.getCurrentImage();
			drawingArea.setCurrentImage(undoImg);
			drawingArea.setShouldDrawImage(false);
			menuBar.setRedoItemEnabled(true);
			menuBar.setUndoItemEnabled(false);
		}
	}
	
	public void redo() {
		if (redoImg != null) {
			drawingArea.setCurrentImage(redoImg);
			menuBar.setUndoItemEnabled(true);
		}
	}
}
