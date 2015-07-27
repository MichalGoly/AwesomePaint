package com.goly2.spaint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TopMenuBar extends JMenuBar {
	
	private DrawingArea drawingArea;
	private Paint paint;	
	private HashMap<String, ImageIcon> icons;
	private UndoRedoManager manager;
	private final JFileChooser chooser;

	private JMenu fileMenu;
	private JMenu editMenu;
	private FiltersJMenu filtersMenu;
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	private JMenuItem saveAsItem;
			
	public TopMenuBar(DrawingArea drawingArea, Paint paint) {
		this.drawingArea = drawingArea;
		this.paint = paint;
		icons = new HashMap<>();
		manager = new UndoRedoManager(drawingArea, this);
		this.drawingArea.setManager(manager);
		chooser = new JFileChooser();
		
		loadIcons();
		initialize();			
	}
	
	/**
	 * Set value of undoItem
	 * @param b true or false
	 */
	public void setUndoItemEnabled(boolean b) {
		if (undoItem != null) undoItem.setEnabled(b);
	}
	
	/**
	 * Set value of redoItem
	 * @param b true or false
	 */
	public void setRedoItemEnabled(boolean b) {
		if (redoItem != null) redoItem.setEnabled(b);
	}
	
	/**
	 * Get JMenu object
	 * @param key Either "file" or "edit"
	 * @return JMenu object
	 */
	public JMenu getCustomMenu(String key) {
		if (key.equals("file")) return fileMenu;
		else if (key.equals("edit")) return editMenu;
		else return null;
	}
	/**
	 * Loads the icons and put them to the ArrayList
	 */
	private void loadIcons() {
		icons.put("undo", new ImageIcon(getClass()
				.getResource("/icons/undo.png")));
		icons.put("redo", new ImageIcon(getClass()
				.getResource("/icons/redo.png")));
		icons.put("new", new ImageIcon(getClass()
				.getResource("/icons/new.png")));
		icons.put("open", new ImageIcon(getClass()
				.getResource("/icons/open.png")));
//		icons.put("save", new ImageIcon(getClass()
//				.getResource("/icons/save.png")));
		icons.put("save_as", new ImageIcon(getClass()
				.getResource("/icons/save_as.png")));
		icons.put("exit", new ImageIcon(getClass()
				.getResource("/icons/exit.png")));
	}
	
	private void initialize() {
		// Create File menu plus sub-menus and add them to the JMenuBar
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		add(fileMenu);
		
		JMenuItem newItem = new JMenuItem("New...");
		newItem.addActionListener(new NewItemListener());
		newItem.setIcon(icons.get("new"));
		newItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		fileMenu.add(newItem);
		
		JMenuItem openItem = new JMenuItem("Open...");
		openItem.addActionListener(new OpenItemListener());
		openItem.setIcon(icons.get("open"));
		openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		fileMenu.add(openItem);
		fileMenu.addSeparator();
				
		saveAsItem = new JMenuItem("Save As...");
		saveAsItem.addActionListener(new SaveItemListener());
		saveAsItem.setIcon(icons.get("save_as"));
		saveAsItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		saveAsItem.setEnabled(false);
		fileMenu.add(saveAsItem);
		fileMenu.addSeparator();
		
		
		JMenuItem exitItem = new JMenuItem(new AbstractAction("Exit") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitItem.setIcon(icons.get("exit"));
		fileMenu.add(exitItem);
		
		// Create Edit menu plus sub-menus and add them to the JMenuBar
		editMenu = new JMenu("Edit");
		editMenu.setMnemonic('E');
		add(editMenu);
		
		undoItem = new JMenuItem("Undo");
		undoItem.addActionListener(new UndoItemListener());
		undoItem.setIcon(icons.get("undo"));
		undoItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		undoItem.setEnabled(false);
		editMenu.add(undoItem);
		
		redoItem = new JMenuItem("Redo");
		redoItem.addActionListener(new RedoItemListener());
		redoItem.setIcon(icons.get("redo"));
		redoItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Y"));
		redoItem.setEnabled(false);
		editMenu.add(redoItem);
		
		// Create filtersMenu
		filtersMenu = new FiltersJMenu("Filters", drawingArea, this);
		filtersMenu.setMnemonic('l');
		add(filtersMenu);
		
	}
	
	private class NewItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			int testWidth = 0;
			int testHeight = 0;
			
			ImageSizeChooser sizeChooser = new ImageSizeChooser();
			if (sizeChooser.showDialog(getParent(), "Enter Image Size")) {
				testWidth = sizeChooser.getEneterdWidth();
				testHeight = sizeChooser.getEneterdHeight();
			}
			
			// Check if size has been entered
			if (testWidth > 0 && testHeight > 0 ) {
				paint.resizeFrame(testWidth, testHeight);
				BufferedImage img = new BufferedImage(testWidth, testHeight,
						BufferedImage.TYPE_INT_ARGB);
				drawingArea.newImage(img);
				
				saveAsItem.setEnabled(true);
				filtersMenu.enableMenu(true);
			}
		}
	}
	
	private class OpenItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			setOpenExtensionFilter();
			int returnValue = chooser.showOpenDialog(getParent());
			
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedImage img = ImageIO
							.read(chooser.getSelectedFile());
					paint.resizeFrame(img.getWidth(), img.getHeight());
					drawingArea.openImage(img);
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
			
			saveAsItem.setEnabled(true);
			filtersMenu.enableMenu(true);
		}
		
		private void setOpenExtensionFilter() {
			chooser.resetChoosableFileFilters();
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setFileFilter(new FileNameExtensionFilter("Images",
					"jpg", "jpeg", "bmp", "gif", "png"));
		}
	}
	
	private class SaveItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (drawingArea.getCurrentImage() == null) return;
			
			if (event.getActionCommand().equals("Save As...")) {
				setSaveExtensionFilter();
				int returnVal = chooser.showSaveDialog(getParent());
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {					
					// Write an image to HDD
					try {
						ExtensionChooser extC = new ExtensionChooser(chooser);
						ImageIO.write(drawingArea.getCurrentImage(),
								extC.getExtension(), extC.getFile());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}				
			} else {
				// Should never happen
				new Exception("Unknown action command")
					.printStackTrace();
			}
		}
		
		private void setSaveExtensionFilter() {
			chooser.resetChoosableFileFilters();
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.addChoosableFileFilter(new FileNameExtensionFilter(
					"PNG image (*.png)", "png"));
			chooser.addChoosableFileFilter(new FileNameExtensionFilter(
					"JPEG image(*.jpg)", "jpg"));
			chooser.addChoosableFileFilter(new FileNameExtensionFilter(
					"GIF image (*.gif)", "gif"));
			chooser.addChoosableFileFilter(new FileNameExtensionFilter(
					"BMP image (*.bmp)", "bmp"));
		}
	}
	
	private class UndoItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			manager.undo();	
			undoItem.setEnabled(false);
		}
	}

	private class RedoItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			manager.redo();
			redoItem.setEnabled(false);
		}
	}
	
}