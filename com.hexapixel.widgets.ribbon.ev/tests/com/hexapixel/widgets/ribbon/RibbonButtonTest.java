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


public class RibbonButtonTest extends AbstractRibbonGroupItemTest {

	@Override
	protected AbstractRibbonGroupItem createControl(RibbonShell ribbonShell) {
		RibbonTabFolder ftf = ribbonShell.getRibbonTabFolder();
		RibbonTab tab = new RibbonTab(ftf, "tab");
		RibbonGroup group = new RibbonGroup(tab, "group");
		return new RibbonButton(group, null, "ribbonbutton", RibbonButton.STYLE_NONE);
	}

}
