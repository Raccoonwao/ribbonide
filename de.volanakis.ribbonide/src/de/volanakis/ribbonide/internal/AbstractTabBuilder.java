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
package de.volanakis.ribbonide.internal;

import org.eclipse.core.runtime.Assert;

import com.hexapixel.widgets.ribbon.RibbonTab;
import com.hexapixel.widgets.ribbon.RibbonTabFolder;

public abstract class AbstractTabBuilder {

	private final AbstractShellBuilder shellBuilder;
	private final String text;
	private final String perspectiveId;

	private RibbonTab ribbonTab;

	AbstractTabBuilder(AbstractShellBuilder shellBuilder, String tabName) {
		this(shellBuilder, tabName, null);
	}

	AbstractTabBuilder(AbstractShellBuilder shellBuilder, String tabName,
			String perspectiveId) {
		Assert.isNotNull(shellBuilder);
		this.shellBuilder = shellBuilder;
		Assert.isNotNull(tabName);
		text = tabName;
		this.perspectiveId = perspectiveId;
		shellBuilder.addTabBuilder(this);
	}

	public void create() {
		ribbonTab = createTab();
		fillRibbonTab(ribbonTab);
	}

	// protected methods
	// //////////////////

	protected RibbonTab createTab() {
		if (perspectiveId != null) {
			return new RibbonTabWithPerspective(getRibbonTabFolder(), text,
					perspectiveId);
		}
		return new RibbonTab(getRibbonTabFolder(), text);
	}

	protected void fillRibbonTab(RibbonTab tab) {
		// default implementation
	}

	// helping methods
	// ////////////////

	private final RibbonTabFolder getRibbonTabFolder() {
		return shellBuilder.getShell().getRibbonTabFolder();
	}
}
