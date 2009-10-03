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
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PerspectiveAdapter;

import com.hexapixel.widgets.ribbon.RibbonShell;
import com.hexapixel.widgets.ribbon.RibbonTab;
import com.hexapixel.widgets.ribbon.RibbonTabFolder;

class PerspectiveToTabSynchronizer extends PerspectiveAdapter {

	private final RibbonShell shell;

	PerspectiveToTabSynchronizer(RibbonShell shell) {
		Assert.isNotNull(shell);
		this.shell = shell;
	}

	public void perspectiveActivated(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
		RibbonTabFolder tabFolder = shell.getRibbonTabFolder();
		String pid = perspective.getId();
		if (needToSwitch(tabFolder.getSelectedTab(), pid)) {
			for (RibbonTab tab : tabFolder.getTabs()) {
				if (isTarget(tab, pid)) {
					((RibbonTabWithPerspective) tab).selectTab();
					return;
				}
			}
		}
	}

	// helping methods
	// ////////////////

	private boolean needToSwitch(RibbonTab ribbonTab, String pid) {
		return !hasPerspective(ribbonTab, pid, true);
	}

	private boolean isTarget(RibbonTab ribbonTab, String pid) {
		return hasPerspective(ribbonTab, pid, false)
				&& ribbonTab != ribbonTab.getTabFolder().getSelectedTab();
	}

	private boolean hasPerspective(RibbonTab tab, String perspectiveId,
			boolean defValue) {
		boolean result = defValue;
		if (tab instanceof RibbonTabWithPerspective) {
			RibbonTabWithPerspective rtwp = (RibbonTabWithPerspective) tab;
			String pid = rtwp.getPerspectiveId();
			result = perspectiveId.equals(pid);
		}
		return result;
	}

}