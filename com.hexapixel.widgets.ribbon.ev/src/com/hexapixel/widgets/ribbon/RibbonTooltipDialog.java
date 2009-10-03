/*******************************************************************************
 * Copyright (c) Emil Crumhorn - Hexapixel.com - emil.crumhorn@gmail.com
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    emil.crumhorn@gmail.com - initial API and implementation
 *******************************************************************************/ 

package com.hexapixel.widgets.ribbon;

import java.util.StringTokenizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.hexapixel.widgets.generic.ColorCache;
import com.hexapixel.widgets.generic.Utils;

public class RibbonTooltipDialog {

	private static Shell shell;
	
	private static Color mDivider = ColorCache.getInstance().getColor(158, 187, 221);
	private static Color mDividerShadow = ColorCache.getInstance().getColor(255, 255, 255);
	
	private static Color mInnerFillTop = ColorCache.getInstance().getColor(255, 251, 252);
	private static Color mInnerFillBottom = ColorCache.getInstance().getColor(204, 217, 234);
	private static Color mTextColor = ColorCache.getInstance().getColor(79, 77, 78);

	private static Color mTooltipBorder = ColorCache.getInstance().getColor(118, 118, 118);
	private static Color mTooltipShadowCornerInner = ColorCache.getInstance().getColor(131, 131, 131);
	private static Color mTooltipShadowCornerOuter = ColorCache.getInstance().getColor(148, 148, 148);
	private static Color mTooltipShadowInnerCorner = ColorCache.getInstance().getColor(186, 186, 186);
		
	public static void makeDialog(final RibbonTooltip toolTip, Point location) {
		if (shell != null && !shell.isDisposed()) 
			shell.dispose();

		shell = new Shell(Display.getDefault().getActiveShell(), SWT.ON_TOP | SWT.TOOL | SWT.NO_TRIM);
		shell.setLayout(new FillLayout());
		
		final Composite comp = new Composite(shell, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
		comp.addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent e) {
				Region region = new Region(shell.getDisplay());

				GC gc = e.gc;
				Rectangle bounds = comp.getBounds();
				// draw borders
				drawBorders(gc, bounds);
				
				// this is the margins for all content
				int marginTop = 8;
				int marginLeft = 6;
				int marginRight = 6;
				int marginBottom = 12;
				
				int x = marginLeft;
				int y = marginTop;
				
				int xMax = 0;
				int yMax = 0;
				
				// == TITLE ==
				// title is bold
				Font bold = null;
				Font old = gc.getFont();
				bold = Utils.applyBoldFont(old);

				if (toolTip.getTitle() != null && toolTip.getTitle().length() > 0) {
					gc.setForeground(mTextColor);
					gc.setFont(bold);
					Point p = gc.stringExtent(toolTip.getTitle());
					gc.drawString(toolTip.getTitle(), x, y, true);
					gc.setFont(old);
					
					y += p.y;
					xMax = Math.max(xMax, x + p.x);
					yMax = Math.max(yMax, y);					
				}				
				
				Image bigImage = toolTip.getImage();
				int imageY = y;
				if (bigImage != null) {
					// draw the image, as well as tell the normal text where it will have to go depending on image size
					// space it first, regardless of size
					x += 9;
					Rectangle imBounds = bigImage.getBounds();
					// we push it down a bit, but these don't add to the overall y position as the image
					// is (somewhat) horizontally aligned with the content text
					gc.drawImage(bigImage, x, y+12); 
					x += imBounds.width;
					
					imageY += imBounds.height+12;
				}

				// == DRAW TEXT ==				
				int textY = y;
				if (toolTip.getContent() != null && toolTip.getContent().length() > 0) {
					// if we had an image, space out this text, otherwise a little less
					if (bigImage != null)
						x += 13;
					else
						x += 8;
					
					// first we space it vertically					
					textY += 13;
					
					StringTokenizer st = new StringTokenizer(toolTip.getContent(), "\n");
	
					int widestLine = 0;				
					while (st.hasMoreTokens()) {
						String token = st.nextToken();
						Point extent = drawText(gc, token, x, textY);					
						textY += extent.y;
						widestLine = Math.max(widestLine, extent.x);
					}
					x += widestLine;
				}
				
				
				// now add the image height to Y unless the text Y was bigger
				y = Math.max(textY, imageY);

				xMax = Math.max(xMax, x);
				yMax = Math.max(yMax, y);
				
				if (toolTip.getHelpImage() != null && toolTip.getHelpText() != null) {					
					y += 8;
					
					// draw divider					
					gc.setForeground(mDivider);
					gc.drawLine(marginLeft, y, marginLeft+xMax, y);
					y++;
					gc.setForeground(mDividerShadow);
					gc.drawLine(marginLeft, y, marginLeft+xMax, y);
					y += 7;
					gc.setForeground(mTextColor);
					gc.setFont(bold);
													
					int curX = marginLeft;
					
					int widthUsed = 0;
					
					if (toolTip.getHelpImage() != null) {
						gc.drawImage(toolTip.getHelpImage(), marginLeft, y);
						curX += toolTip.getHelpImage().getBounds().width;
						curX += 9;
						widthUsed += curX - marginLeft;
					}
					
					if (toolTip.getHelpText() != null) {
						Point helpSize = gc.stringExtent(toolTip.getHelpText());
						gc.drawString(toolTip.getHelpText(), curX, y, true);
						widthUsed += helpSize.x;
					}
					
					xMax = Math.max(xMax, widthUsed);
					yMax = Math.max(yMax, y);
				}			
				
				xMax += marginLeft + marginRight;
				yMax += marginTop + marginBottom;
								
				region.add(0, 0, xMax, yMax);
				region.subtract(0, 0, 1, 1);
				region.subtract(xMax-1, yMax-1, 1, 1);
				region.subtract(0, yMax-1, 1, 1);
				region.subtract(xMax-1, 0, 1, 1);
				shell.setRegion(region);
				Rectangle size = region.getBounds();
				shell.setSize(size.width, size.height);
				if (bold != null)
					bold.dispose();
			}
			
		});
		
		shell.pack();
		shell.setLocation(location);
		shell.setVisible(true);
	}
	
	private static void drawBorders(GC gc, Rectangle bounds) {
		gc.setForeground(mInnerFillTop);
		gc.setBackground(mInnerFillBottom);
		
		gc.fillGradientRectangle(bounds.x, bounds.y, bounds.width, bounds.height, true);
						
		// draw border
		gc.setForeground(mTooltipBorder);
		gc.drawRectangle(bounds.x, bounds.y, bounds.width-1, bounds.height-1);
		
		// what would would the world be without faded gradient corners? boring! so let's draw a few.

		// draw corners
		gc.setForeground(mTooltipShadowCornerOuter);
		// top left
		gc.drawLine(bounds.x+1, bounds.y, bounds.x+1, bounds.y);
		gc.drawLine(bounds.x, bounds.y+1, bounds.x, bounds.y+1);
		// top right
		gc.drawLine(bounds.x+bounds.width-2, bounds.y, bounds.x+bounds.width-2, bounds.y);
		gc.drawLine(bounds.x+bounds.width-1, bounds.y+1, bounds.x+bounds.width-1, bounds.y+1);
		// bottom right
		gc.drawLine(bounds.x+bounds.width-1, bounds.y+bounds.height-2, bounds.x+bounds.width-1, bounds.y+bounds.height-2);
		gc.drawLine(bounds.x+bounds.width-2, bounds.y+bounds.height-1, bounds.x+bounds.width-2, bounds.y+bounds.height-1);
		// bottom left
		gc.drawLine(bounds.x+1, bounds.y+bounds.height-1, bounds.x+1, bounds.y+bounds.height-1);
		gc.drawLine(bounds.x, bounds.y+bounds.height-2, bounds.x, bounds.y+bounds.height-2);

		// shadowed corner inside the above
		gc.setForeground(mTooltipShadowCornerInner);
		// top left
		gc.drawLine(bounds.x+2, bounds.y, bounds.x+2, bounds.y);
		gc.drawLine(bounds.x, bounds.y+2, bounds.x, bounds.y+2);
		// top right
		gc.drawLine(bounds.x+bounds.width-3, bounds.y, bounds.x+bounds.width-3, bounds.y);
		gc.drawLine(bounds.x+bounds.width-1, bounds.y+2, bounds.x+bounds.width-1, bounds.y+2);
		// bottom right
		gc.drawLine(bounds.x+bounds.width-1, bounds.y+bounds.height-3, bounds.x+bounds.width-1, bounds.y+bounds.height-3);
		gc.drawLine(bounds.x+bounds.width-3, bounds.y+bounds.height-1, bounds.x+bounds.width-3, bounds.y+bounds.height-1);
		// bottom left
		gc.drawLine(bounds.x+2, bounds.y+bounds.height-1, bounds.x+2, bounds.y+bounds.height-1);
		gc.drawLine(bounds.x, bounds.y+bounds.height-3, bounds.x, bounds.y+bounds.height-3);
		
		// draw inner corner pixel in each corner
		gc.setForeground(mTooltipShadowInnerCorner);
		// top left
		gc.drawLine(bounds.x+1, bounds.y+1, bounds.x+1, bounds.y+1);
		// top right
		gc.drawLine(bounds.x+bounds.width-2, bounds.y+1, bounds.x+bounds.width-2, bounds.y+1);
		// bottom right
		gc.drawLine(bounds.x+bounds.width-2, bounds.y+bounds.height-2, bounds.x+bounds.width-2, bounds.y+bounds.height-2);
		// bottom left
		gc.drawLine(bounds.x+1, bounds.y+bounds.height-2, bounds.x+1, bounds.y+bounds.height-2);

	}
	
	private static Point drawText(GC gc, String text, int x, int y) {
		StringTokenizer sub = new StringTokenizer(text, " ");
	
        Font old = gc.getFont();
        Font used = null;
        String oldName = old.getFontData()[0].getName();
        int oldSize = (int)old.getFontData()[0].height;
        
        int curX = x;
        boolean bold = false;
        boolean italic = false;
        int size = oldSize;
        Color fg = ColorCache.getInstance().getBlack();

        int maxWidth = 0;
        int maxHeight = 0;
        
        int tokens = sub.countTokens();
        int cnt = 0;
        
		while (sub.hasMoreElements()) {			
			String token = sub.nextToken();
			
			if (isNormalize(token)) {
				bold = false;
				italic = false;
				size = oldSize;
				fg = ColorCache.getInstance().getBlack();
			}
			else {			
				int newSize = getSize(token);
				if (newSize != size)
					newSize = size;
				
				boolean newBold = isBold(token);
				if (bold && !isBold(token))
					bold = true;
				else
					bold = newBold;

				boolean newItalic = isItalic(token);
				if (italic && !isItalic(token))
					italic = true;
				else
					italic = newItalic;

				Color newColor = getColor(token);
				if (newColor != fg)
					fg = newColor;
			}
				
	        if (fg != null)
	        	gc.setForeground(fg);
	
	        token = cleanUp(token);
	        
	        int style = SWT.NORMAL;
	        if (bold)
	        	style |= SWT.BOLD;
	        if (italic)
	        	style |= SWT.ITALIC;
		        
            used = new Font(Display.getDefault(), oldName, size, style);
            gc.setFont(used);
	
	        gc.drawString(token, curX, y, true);
	        int extX = gc.stringExtent(token).x + ((cnt != tokens - 1) ? gc.stringExtent(" ").x : 0);
	        int extY = gc.stringExtent(token).y;
	        curX += extX;
	        
	        maxWidth = Math.max(maxWidth, curX);
	        maxHeight = Math.max(maxHeight, extY);
	        
	        if (used != null)
	        	used.dispose();
	        
	        cnt++;
		}
        
        gc.setFont(old);
        return new Point(maxWidth-x, maxHeight);
    }

	 private static String cleanUp(String str) {
		int start = str.indexOf("\\s");
		if (start != -1) {
			String left = str.substring(0, start);
			String right = str.substring(start + 4, str.length());

			str = left + right;
		}
		start = str.indexOf("\\c");
		str = str.replaceAll("\\\\c[0-9]{9}", "");
		str = str.replaceAll("\\\\x", "");
		str = str.replaceAll("\\\\b", "");
		return str;
	}

	private static boolean isNormalize(String str) {
		return str.indexOf("\\x") > -1;
	}
	 	
	private static boolean isBold(String str) {
		return str.indexOf("\\b") > -1;
	}

	private static boolean isItalic(String str) {
		return str.indexOf("\\i") > -1;
	}

	private static int getSize(String str) {
		int start = str.indexOf("\\s");
		if (start == -1) {
			return -1;
		}

		String size = str.substring(start + 2, start + 4);

		try {
			return Integer.parseInt(size);
		} catch (Exception badParse) {
			badParse.printStackTrace();
		}

		return -1;
	}

	private static Color getColor(String str) {
		int start = str.indexOf("\\c");
		if (start == -1)
			return null;

		int r = Integer.parseInt(str.substring(start + 2, start + 5));
		int g = Integer.parseInt(str.substring(start + 5, start + 8));
		int b = Integer.parseInt(str.substring(start + 8, start + 11));

		return ColorCache.getInstance().getColor(r, g, b);
	}

	public static void kill() {
		if (shell != null && shell.isDisposed() == false) {
			shell.dispose();
		}
	}

	public static boolean isActive() {
		return (shell != null && !shell.isDisposed());
	}
}
