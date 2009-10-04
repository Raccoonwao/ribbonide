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

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

import com.hexapixel.widgets.ribbon.QuickAccessShellToolbar;
import com.hexapixel.widgets.ribbon.RibbonTabFolder;

public final class ShellBuilder extends AbstractWindowBuilder {

	ShellBuilder(Shell shell) {
		super(shell);
	}

	public ShellBuilder(IWorkbenchWindow window) {
		super(window);
	}

	@Override
	protected void configureHelp(RibbonTabFolder rtf) {
		RibbonActionFactory.configureHelp(rtf, getWindow());
	}

	@Override
	protected void fillBigButtonMenu(Menu bbMenu) {
		if (getWindow() == null) {
			return;
		}
		CommandContributionItem item = new CommandContributionItem(
				new CommandContributionItemParameter(getWindow(), null,
						"org.eclipse.ui.file.exit",
						CommandContributionItem.STYLE_PUSH));
		item.fill(bbMenu, -1);
	}

	@Override
	protected void fillQuickAccessToolbar(final QuickAccessShellToolbar qat) {
		RibbonActionFactory.createSave(qat, getWindow());
		RibbonActionFactory.createUndo(qat, getWindow());
		RibbonActionFactory.createRedo(qat, getWindow());
	}

	@Override
	protected void postCreate() {
		hookPerspectivesToTabs();
	}

	// helping methods
	// ////////////////

	private void hookPerspectivesToTabs() {
		IWorkbenchWindow window = getWindow();
		if (window != null) {
			window.addPerspectiveListener(new PerspectiveToTabSynchronizer(
					getShell()));
		}
	}
}
