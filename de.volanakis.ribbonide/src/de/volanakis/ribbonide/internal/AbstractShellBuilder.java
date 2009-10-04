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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import com.hexapixel.widgets.ribbon.QuickAccessShellToolbar;
import com.hexapixel.widgets.ribbon.RibbonShell;
import com.hexapixel.widgets.ribbon.RibbonTabFolder;

import de.volanakis.ribbonide.internal.e.ICE;

public abstract class AbstractShellBuilder {

	private final Shell shell;
	private final List<AbstractTabBuilder> tabBuilders = new ArrayList<AbstractTabBuilder>();

	private RibbonShell ribbonShell;
	private String text;
	private Image image;

	public AbstractShellBuilder(Shell shell, String shellName) {
		Assert.isNotNull(shell);
		this.shell = shell;
		Assert.isNotNull(shellName);
		text = shellName;
		image = ICE.getImage("eclipse_24.png");
	}

	public final void addTabBuilder(AbstractTabBuilder tabBuilder) {
		Assert.isNotNull(tabBuilder);
		tabBuilders.add(tabBuilder);
	}

	public Control create() {
		ribbonShell = new RibbonShell(shell);
		ribbonShell.setButtonImage(image);
		ribbonShell.setText(text);
		configureHelp(ribbonShell.getRibbonTabFolder());
		fillBigButtonMenu(ribbonShell.getBigButtonMenu());
		fillQuickAccessToolbar(ribbonShell.getToolbar());
		ribbonShell.addBigButtonListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				ribbonShell.showBigButtonMenu();
			}
		});
		for (AbstractTabBuilder tabBuilder : tabBuilders) {
			tabBuilder.create();
		}
		postCreate();
		return ribbonShell.getRibbonTabFolder();
	}

	public final RibbonShell getShell() {
		Assert.isNotNull(ribbonShell, "call create() first");
		return ribbonShell;
	}

	public final void setText(String text) {
		Assert.isNotNull(text);
		this.text = text;
		if (ribbonShell != null) {
			ribbonShell.setText(text);
		}
	}

	public final void setImage(Image image) {
		Assert.isNotNull(image);
		this.image = image;
		if (ribbonShell != null) {
			ribbonShell.setButtonImage(image);
		}
	}

	// protected methods
	// //////////////////

	protected final AbstractTabBuilder[] getTabBuilders() {
		return tabBuilders.toArray(new AbstractTabBuilder[tabBuilders.size()]);
	}

	protected void configureHelp(RibbonTabFolder rtf) {
		// default implementation
	}

	protected void fillBigButtonMenu(Menu bbMenu) {
		// default implementation
	}

	protected void fillQuickAccessToolbar(QuickAccessShellToolbar qat) {
		// default implementation
	}

	protected void postCreate() {
		// default implementation
	}

}
