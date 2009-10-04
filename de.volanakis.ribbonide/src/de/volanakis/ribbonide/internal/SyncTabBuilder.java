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

import org.eclipse.ui.IWorkbenchWindow;

import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.hexapixel.widgets.ribbon.RibbonTab;
import com.hexapixel.widgets.ribbon.RibbonToolbar;
import com.hexapixel.widgets.ribbon.RibbonToolbarGrouping;

public final class SyncTabBuilder extends AbstractTabBuilder {

	private final IWorkbenchWindow window;

	public SyncTabBuilder(AbstractWindowBuilder shellBuilder) {
		super(shellBuilder, "Sync",
				"org.eclipse.team.ui.TeamSynchronizingPerspective");
		window = shellBuilder.getWindow();
	}

	@Override
	protected void fillRibbonTab(RibbonTab tab) {
		createGroupSync(tab);
		createGroupOpen(tab);
		createGroupNavigation(tab);
	}

	private void createGroupSync(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Sync");

		// RibbonActionFactory.createOpenType(group, window);
		//		
		// RibbonButtonGroup subgroup = new RibbonButtonGroup(group);
		// RibbonActionFactory.createOpenPdeArtifact(subgroup, window);
		// RibbonActionFactory.createOpenResource(subgroup, window);
		// RibbonActionFactory.createSearch(subgroup, window);
	}

	private void createGroupOpen(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Open");

		RibbonActionFactory.createOpenType(group, window);

		RibbonButtonGroup subgroup = new RibbonButtonGroup(group);
		RibbonActionFactory.createOpenPdeArtifact(subgroup, window);
		RibbonActionFactory.createOpenResource(subgroup, window);
		RibbonActionFactory.createSearch(subgroup, window);
	}

	private void createGroupNavigation(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Navigate");

		RibbonToolbar toolbar = new RibbonToolbar(group,
				RibbonToolbar.STYLE_BORDERED, 2);

		RibbonToolbarGrouping rtgNavigate = new RibbonToolbarGrouping(toolbar,
				1);
		RibbonActionFactory.createGoLastEdit(rtgNavigate, window);
		RibbonActionFactory.createGoBackward(rtgNavigate, window);
		RibbonActionFactory.createGoForward(rtgNavigate, window);

		RibbonToolbarGrouping rtgAnnot = new RibbonToolbarGrouping(toolbar, 2);
		RibbonActionFactory.createGoNext(rtgAnnot, window);
		RibbonActionFactory.createGoPrevious(rtgAnnot, window);
	}
}
