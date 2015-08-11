package com.michalgoly.apaint.tools;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class CursorLoader {
	
	public static final int BRUSH_CURSOR = 0;
	public static final int ERASER_CURSOR = 1;
	public static final int SHAPES_CURSOR = 2;
	public static final int LINE_CURSOR = 3;
	public static final int MAGIC_CURSOR = 4;
	public static final int BUCKET_CURSOR = 5;
	
	private boolean cursorInitiated;
	
	private Toolkit toolkit;
	private Hashtable<Integer, Cursor> cursors;
	
	public CursorLoader() {
		toolkit = Toolkit.getDefaultToolkit();
		cursors = new Hashtable<>();
		cursorInitiated = false;
		
		// Init brush cursor
		cursors.put(BRUSH_CURSOR, new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		// Init eraser cursor
		try {
			BufferedImage img = ImageIO.read(getClass()
					.getResource("/cursors/eraser_cursor.png"));
			Cursor cursor = toolkit.createCustomCursor(img,	new Point(10, 10),
					"Eraser");
			cursors.put(ERASER_CURSOR, cursor);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
				
		// Init shapes cursor
		cursors.put(SHAPES_CURSOR, new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		// Init line cursor
		cursors.put(LINE_CURSOR, new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		//Init magic cursor
		cursors.put(MAGIC_CURSOR, new Cursor(Cursor.HAND_CURSOR));
		
		// Init bucket cursor
		cursors.put(BUCKET_CURSOR, new Cursor(Cursor.HAND_CURSOR));
	}
	
	public void setCursorInitiated(boolean b) {
		cursorInitiated = b;
	}
	
	public boolean isCursorInitiated() {
		return cursorInitiated;
	}
	
	public void setCursor(Component component, int cursorType) {
		if (cursorType < 0 || cursorType > 5) {
			new Exception("Illegal cursor type value!").printStackTrace();
		}
		component.setCursor(cursors.get(cursorType));
	}
	
}
