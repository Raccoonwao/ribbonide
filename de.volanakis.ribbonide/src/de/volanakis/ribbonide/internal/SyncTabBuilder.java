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

import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.hexapixel.widgets.ribbon.RibbonTab;
import com.hexapixel.widgets.ribbon.RibbonToolbar;
import com.hexapixel.widgets.ribbon.RibbonToolbarGrouping;

import de.volanakis.ribbonide.internal.e.ICE;

public final class SyncTabBuilder extends AbstractTabBuilder {

	public SyncTabBuilder(AbstractShellBuilder shellBuilder) {
		super(shellBuilder, "Sync",
				"org.eclipse.team.ui.TeamSynchronizingPerspective");
	}

	protected void fillRibbonTab(RibbonTab tab) {
		createGroupLaunch(tab);
		createGroupOpen2(tab);
		createGroupCreate2(tab);
		createGroupNavigation(tab);
	}

	private void createGroupLaunch(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Launch");

		RibbonButton rbDebug = new RibbonButton(group, ICE
				.getImage("debug_exc_30.png"), "Debug",
				RibbonButton.STYLE_ARROW_DOWN_SPLIT);
		RibbonButton rbRun = new RibbonButton(group, ICE
				.getImage("run_exc_30.png"), "Run ",
				RibbonButton.STYLE_ARROW_DOWN_SPLIT);
		RibbonButton rbRunExt = new RibbonButton(group, ICE
				.getImage("external_tools_ev_30.png"), "Run Ext",
				RibbonButton.STYLE_ARROW_DOWN_SPLIT);
	}

	private void createGroupOpen2(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Open");

		RibbonButton rbOpenType = new RibbonButton(group, ICE
				.getImage("opentype_30.png"), "Class", RibbonButton.STYLE_PUSH);

		RibbonButtonGroup subgroup = new RibbonButtonGroup(group);
		RibbonButton rbFind = new RibbonButton(subgroup, ICE
				.getImage("search.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbOpenPdeArtifact = new RibbonButton(subgroup, ICE
				.getImage("open_artifact_obj.gif"), null,
				RibbonButton.STYLE_PUSH);
		RibbonButton rbOpenFile = new RibbonButton(subgroup, ICE
				.getImage("open_file_ev.png"), null, RibbonButton.STYLE_PUSH);
	}

	private void createGroupCreate2(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Create");

		RibbonToolbar toolbar = new RibbonToolbar(group,
				RibbonToolbar.STYLE_BORDERED, 2);

		RibbonToolbarGrouping rtgNew1 = new RibbonToolbarGrouping(toolbar, 1);
		RibbonButton rbNew = new RibbonButton(rtgNew1, ICE
				.getImage("new_wiz.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbNewJava = new RibbonButton(rtgNew1, ICE
				.getImage("newjprj_wiz.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbNewPlugin = new RibbonButton(rtgNew1, ICE
				.getImage("newpprj_wiz.gif"), null, RibbonButton.STYLE_PUSH);

		RibbonToolbarGrouping rtgNew2 = new RibbonToolbarGrouping(toolbar, 2);
		RibbonButton rbNewPackage = new RibbonButton(rtgNew2, ICE
				.getImage("newpack_wiz.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbNewClass = new RibbonButton(rtgNew2, ICE
				.getImage("newclass_wiz.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbNewInterface = new RibbonButton(rtgNew2, ICE
				.getImage("newint_wiz.gif"), null, RibbonButton.STYLE_PUSH);
	}

	private void createGroupNavigation(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Navigate");

		RibbonToolbar toolbar = new RibbonToolbar(group,
				RibbonToolbar.STYLE_BORDERED, 2);

		RibbonToolbarGrouping rtgNavigate = new RibbonToolbarGrouping(toolbar,
				1);
		RibbonButton rbLastEdit = new RibbonButton(rtgNavigate, ICE
				.getImage("last_edit_pos.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbBackward = new RibbonButton(rtgNavigate, ICE
				.getImage("backward_nav.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbForward = new RibbonButton(rtgNavigate, ICE
				.getImage("forward_nav.gif"), null, RibbonButton.STYLE_PUSH);

		RibbonToolbarGrouping rtgAnnot = new RibbonToolbarGrouping(toolbar, 2);
		RibbonButton rbNextAnnotation = new RibbonButton(rtgAnnot, ICE
				.getImage("next_nav.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbPrevAnnotation = new RibbonButton(rtgAnnot, ICE
				.getImage("prev_nav.gif"), null, RibbonButton.STYLE_PUSH);
	}

}
