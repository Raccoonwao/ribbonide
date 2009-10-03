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

import org.eclipse.swt.widgets.Display;

import junit.framework.TestCase;

public abstract class AbstractRibbonGroupItemTest extends TestCase {

	private Display display;
	private RibbonShell ribbonShell;
	private AbstractRibbonGroupItem control;
	
	@Override
	protected void setUp() throws Exception {
		display = Display.getDefault();
		ribbonShell = new RibbonShell(display);
		control = createControl(ribbonShell);
	}
	
	@Override
	protected void tearDown() throws Exception {
		control.dispose();
		ribbonShell.getShell().dispose();
		display.dispose();
	}
	
	protected abstract AbstractRibbonGroupItem createControl(RibbonShell ribbonShell2);
	
	public void testAddDisposeListener() throws Exception {
		FTDisposeListener listener = new FTDisposeListener();
		control.addDisposeListener(listener);
		control.addDisposeListener(listener);
		
		assertEquals(0, listener.getCount());
		
		control.dispose();
		
		assertEquals(1, listener.getCount());
		assertEquals(control, listener.getItem());
		
		control.dispose();

		assertEquals(1, listener.getCount());
	}

	public void testRemoveDisposeListener() throws Exception {
		FTDisposeListener listener = new FTDisposeListener();
		control.addDisposeListener(listener);
		control.removeDisposeListener(listener);
		
		assertEquals(0, listener.getCount());
		
		control.dispose();
		
		assertEquals(0, listener.getCount());
	}
	
}
