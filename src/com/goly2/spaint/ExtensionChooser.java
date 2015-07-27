package com.goly2.spaint;

import java.io.File;

import javax.swing.JFileChooser;

public class ExtensionChooser {
	
	private JFileChooser chooser;
	private String extension;
	private File file;
	
	public ExtensionChooser(JFileChooser chooser) {
		this.chooser = chooser;
		extension = null;
		file = null;
		
		determineExtension();
	}
	
	public String getExtension() {
		if (extension == null) {
			new NullPointerException().printStackTrace();
		}
		return extension;
	}
	
	public File getFile() {
		if (file == null) {
			new NullPointerException().printStackTrace();
		}
		return file;
	}
	
	private void determineExtension() {
		String fileName = chooser.getSelectedFile().getName();
		
		if (fileName.contains(".")) {
			int i = fileName.lastIndexOf(".");
			
			if (i > 0 &&  i < fileName.length() - 1) {
	            extension = fileName.substring(i+1).toLowerCase();
	        }
			
			file = chooser.getSelectedFile();
		} else {
			switch (chooser.getFileFilter().getDescription()) {
				case "PNG image (*.png)":
					extension = "png";
					break;
				case "JPEG image(*.jpg)":
					extension = "jpg";
					break;
				case "GIF image (*.gif)":
					extension = "gif";
					break;
				case "BMP image (*.bmp)":
					extension = "bmp";
					break;
				default:
					new Exception("Unexcepted value ln").printStackTrace();
					break;
			}
			
			file = new File(chooser.getSelectedFile() + "." + extension);
		}
	}
}
