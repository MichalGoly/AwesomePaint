package com.michalgoly.apaint;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class FiltersJMenu extends JMenu {
	
	private FiltersManager filtersManager;
	
	private JMenu colorAdjustment;
	private JMenu disortionAndWarping;
	private JMenu effects;
	
	public FiltersJMenu(String name, DrawingArea drawingArea,
			TopMenuBar menuBar) {
		super(name);
		filtersManager = new FiltersManager(drawingArea, menuBar);
		
		initColorsAdjustment();
		initDisortionAndWrapping();
		initEffects();
	}
	
	/**
	 * Either enables or disables all filters menus as well as its sub menus
	 * @param b true or false
	 */
	public void enableMenu(boolean b) {
		colorAdjustment.setEnabled(b);
		disortionAndWarping.setEnabled(b);
		effects.setEnabled(b);		
	}
	
	private void initColorsAdjustment() {
		// Color and adjustment menu and sub menu
		colorAdjustment = new JMenu("Color adjustment");
		colorAdjustment.setEnabled(false);
		add(colorAdjustment);
		
		//grayscaleItem
		JMenuItem grayscaleItem = new JMenuItem(
				new AbstractAction("Grayscale") {
			
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.grayscale();				
					}
				});
		colorAdjustment.add(grayscaleItem);
		
		//invertItem
		JMenuItem invertItem = new JMenuItem(
				new AbstractAction("Invert") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.invert();
					}
				});
		colorAdjustment.add(invertItem);
		
		//mask menu
		JMenu maskMenu = new JMenu("Mask");
		JMenuItem redMask = new JMenuItem(
				new AbstractAction("Red") {
							
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.mask(0xff00ffff);
					}
				});
		maskMenu.add(redMask);
		
		JMenuItem greenMask = new JMenuItem(
				new AbstractAction("Green") {
							
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.mask(0xffff00ff);
					}
				});
		maskMenu.add(greenMask);
		
		JMenuItem blueMask = new JMenuItem(
				new AbstractAction("Blue") {
							
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.mask(0xffffff00);
					}
				});
		maskMenu.add(blueMask);
		colorAdjustment.add(maskMenu);
		
		// posterizeItem
		JMenuItem posterizeItem = new JMenuItem(
				new AbstractAction("Posterize") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.posterize();
					}
				});
		colorAdjustment.add(posterizeItem);
		
		// solarizeItem
		JMenuItem solarizeItem = new JMenuItem(
				new AbstractAction("Solarize") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.solarize();
					}
				});
		colorAdjustment.add(solarizeItem);
		
		// Gamma Menu
		JMenu gammaMenu = new JMenu("Gamma");
		JMenuItem gammaIncrease = new JMenuItem(
				new AbstractAction("Increase") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.gamma(1.2f);
					}
				});
		gammaMenu.add(gammaIncrease);
		
		JMenuItem gammaDecrease = new JMenuItem(
				new AbstractAction("Decrease") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.gamma(0.8f);
					}
				});
		gammaMenu.add(gammaDecrease);
		
		colorAdjustment.add(gammaMenu);
		
		// Edge item
		JMenuItem edgeItem = new JMenuItem(
				new AbstractAction("Edge") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.edge();
					}
				});
		colorAdjustment.add(edgeItem);
	}
	
	private void initDisortionAndWrapping() {
		// Disorion and wrapping menu and sub menu
		disortionAndWarping = new JMenu("Disortion and wrapping");
		disortionAndWarping.setEnabled(false);
		add(disortionAndWarping);
		
		// Diffuse item
		JMenuItem diffuseItem = new JMenuItem(
				new AbstractAction("Diffuse") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.diffuse();
					}
				});
		disortionAndWarping.add(diffuseItem);
		
		// Dissolve item 
		JMenuItem dissolveItem = new JMenuItem(
				new AbstractAction("Dissolve") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.dissolve();
					}
				});
		disortionAndWarping.add(dissolveItem);
		
		// Kaleidoscope item
		JMenuItem kaleidoscopeItem = new JMenuItem(
				new AbstractAction("Kaleidoscope") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.kaleidoscope();
					}
				});
		disortionAndWarping.add(kaleidoscopeItem);
		
		// Marble item
		JMenuItem marbleItem = new JMenuItem(
				new AbstractAction("Marble") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.marble();
					}
				});
		disortionAndWarping.add(marbleItem);

		// Marble item
		JMenuItem rippleItem = new JMenuItem(
				new AbstractAction("Ripple") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.ripple();
					}
				});
		disortionAndWarping.add(rippleItem);
			
	}
	
	private void initEffects() {
		// Effects menu and sub menu
		effects = new JMenu("Effects");
		effects.setEnabled(false);
		add(effects);
		
		// Pixellate item
		JMenuItem pixItem = new JMenuItem(
				new AbstractAction("Pixellate") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.pixellate();
					}
				});
		effects.add(pixItem);
		
		// Chrome item
		JMenuItem chromeItem = new JMenuItem(
				new AbstractAction("Chrome") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.chrome();
					}
				});
		effects.add(chromeItem);
		
		// Crystallize item
		JMenuItem crystallizeItem = new JMenuItem(
				new AbstractAction("Crystallize") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.crystallize();
					}
				});
		effects.add(crystallizeItem);
		
		// Painting menu
		JMenu paintingMenu = new JMenu("Painting look");
		JMenuItem linesPainting = new JMenuItem(
				new AbstractAction("Lines") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.painting(1);
					}
				});
		paintingMenu.add(linesPainting);
		
		JMenuItem circlesPainting = new JMenuItem(
				new AbstractAction("Circles") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.painting(2);
					}
				});
		paintingMenu.add(circlesPainting);
		
		JMenuItem crossesPainting = new JMenuItem(
				new AbstractAction("Crosses") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.painting(3);
					}
				});
		paintingMenu.add(crossesPainting);
		effects.add(paintingMenu);
		
		// bump item
		JMenuItem bumpItem = new JMenuItem(
				new AbstractAction("Bump") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.bump();
					}
				});
		effects.add(bumpItem);
		
		// blur effect
		JMenuItem blurItem = new JMenuItem(
				new AbstractAction("Blur") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.blur();
					}
				});
		effects.add(blurItem);
		
		// sharpen item
		JMenuItem sharpenItem = new JMenuItem(
				new AbstractAction("Sharpen") {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						filtersManager.sharpen();
					}
				});
		effects.add(sharpenItem);	
		
	}
}
