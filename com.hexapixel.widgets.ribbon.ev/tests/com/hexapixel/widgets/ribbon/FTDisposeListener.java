/*******************************************************************************
 * Copyright (c) 2009, Elias Volanakis
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Elias Volanakis - initial API and implementation
 *******************************************************************************/
package com.hexapixel.widgets.ribbon;


public class FTDisposeListener implements IDisposeListener {

	private int count;
	private AbstractRibbonGroupItem item;

	public void itemDisposed(AbstractRibbonGroupItem item) {
		this.item = item;
		count++;
	}
	
	public int getCount() {
		return count;
	}
	
	public AbstractRibbonGroupItem getItem() {
		return item;
	}
	
}
