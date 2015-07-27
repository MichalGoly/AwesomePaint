package com.goly2.spaint;

import java.awt.image.BufferedImage;

import com.jhlabs.image.BlockFilter;
import com.jhlabs.image.BlurFilter;
import com.jhlabs.image.BumpFilter;
import com.jhlabs.image.ChromeFilter;
import com.jhlabs.image.CrystallizeFilter;
import com.jhlabs.image.DiffuseFilter;
import com.jhlabs.image.DissolveFilter;
import com.jhlabs.image.EdgeFilter;
import com.jhlabs.image.GammaFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.InvertFilter;
import com.jhlabs.image.KaleidoscopeFilter;
import com.jhlabs.image.MarbleFilter;
import com.jhlabs.image.MaskFilter;
import com.jhlabs.image.PosterizeFilter;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.SmearFilter;
import com.jhlabs.image.SolarizeFilter;
import com.jhlabs.image.UnsharpFilter;


public class FiltersManager {
	
	private DrawingArea drawingArea;
	private TopMenuBar menuBar;
	
	public FiltersManager(DrawingArea drawingArea, TopMenuBar menuBar) {
		this.drawingArea = drawingArea;
		this.menuBar = menuBar;
	}
	
	public void grayscale() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		GrayscaleFilter gF = new GrayscaleFilter();
		BufferedImage img = gF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void invert() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		InvertFilter iF = new InvertFilter();
		BufferedImage img = iF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void mask(int mask) {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		MaskFilter mF = new MaskFilter(mask);
		BufferedImage img = mF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void posterize() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		PosterizeFilter pF = new PosterizeFilter();
		pF.setNumLevels(15);
		BufferedImage img = pF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void solarize() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		SolarizeFilter sF = new SolarizeFilter();
		BufferedImage img = sF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void gamma(float value) {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		GammaFilter gF = new GammaFilter(value);
		BufferedImage img = gF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void diffuse() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		DiffuseFilter dF = new DiffuseFilter(); 
		BufferedImage img = dF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void dissolve() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		DissolveFilter dF = new DissolveFilter();
		dF.setDensity(0.5f);
		BufferedImage img = dF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void kaleidoscope() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		KaleidoscopeFilter kF = new KaleidoscopeFilter();
		BufferedImage img = kF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void marble() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		MarbleFilter mF = new MarbleFilter();
		mF.setAmount(2f);
		mF.setXScale(30f);
		mF.setYScale(20f);
		BufferedImage img = mF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void ripple() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		RippleFilter rF = new RippleFilter();
		rF.setXAmplitude(20f);
		rF.setWaveType(4);
		rF.setYAmplitude(10f);
		rF.setYWavelength(10f);
		rF.setXWavelength(10f);
		BufferedImage img = rF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void pixellate() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		BlockFilter bF = new BlockFilter();
		bF.setBlockSize(10);
		BufferedImage img = bF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void chrome() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		ChromeFilter cF = new ChromeFilter();
		BufferedImage img = cF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void crystallize() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		CrystallizeFilter cF = new CrystallizeFilter();
		BufferedImage img = cF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void painting(int shape) {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		SmearFilter sF = new SmearFilter();
		sF.setShape(shape);
		sF.setAngle(45f);
		sF.setMix(0.5f);
		BufferedImage img = sF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void blur() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		BlurFilter gF = new BlurFilter();
		BufferedImage img = gF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void bump() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		BumpFilter bF = new BumpFilter();
		BufferedImage img = bF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void sharpen() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		UnsharpFilter uF = new UnsharpFilter();
		BufferedImage img = uF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
	
	public void edge() {
		BufferedImage currentImg = drawingArea.getCurrentImage();
		
		// Undo/Redo
		drawingArea.getUndoRedoManager().setUndoImg(currentImg);
		menuBar.setUndoItemEnabled(true);
		drawingArea.setNewImageEnabled(false);
		
		// Apply filter
		EdgeFilter eF = new EdgeFilter();
		BufferedImage img = eF.filter(currentImg, null);
		drawingArea.setShouldDrawImage(false);
		drawingArea.setCurrentImage(img);
	}
}
