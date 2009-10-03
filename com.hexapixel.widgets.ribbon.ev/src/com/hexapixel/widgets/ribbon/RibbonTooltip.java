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

import org.eclipse.swt.graphics.Image;

public class RibbonTooltip {

	private Image mImage;
	private String mTitle;
	private String mContent;
	private Image mHelpImage;
	private String mHelpText;
	private int mExtraTextSpacing;
	
	public RibbonTooltip(String title, String content) {
		mTitle = title;
		mContent = content;
	}
	
	public RibbonTooltip(String title, String content, Image image) {
		mTitle = title;
		mContent = content;
		mImage = image;
	}

	public RibbonTooltip(String title, String content, Image image, Image helpImage, String helpText) {
		mTitle = title;
		mContent = content;
		mImage = image;
		mHelpImage = helpImage;
		mHelpText = helpText;
	}

	public Image getImage() {
		return mImage;
	}

	public void setImage(Image image) {
		mImage = image;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		mContent = content;
	}

	public Image getHelpImage() {
		return mHelpImage;
	}

	public void setHelpImage(Image helpImage) {
		mHelpImage = helpImage;
	}

	public String getHelpText() {
		return mHelpText;
	}

	public void setHelpText(String helpText) {
		mHelpText = helpText;
	}
	
	public int getExtraTextSpacing() {
		return mExtraTextSpacing;
	}
	
	public void setExtraTextSpacing(int spacing) {
		mExtraTextSpacing = spacing;
	}
	
	
}
