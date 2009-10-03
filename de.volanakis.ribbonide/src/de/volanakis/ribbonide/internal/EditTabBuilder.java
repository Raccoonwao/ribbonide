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

/**
 * Create the 'Edit' Tab, corresponding to the Java perspective.
 */
public final class EditTabBuilder extends AbstractTabBuilder {

	public EditTabBuilder(AbstractShellBuilder shellBuilder) {
		super(shellBuilder, "Edit", "org.eclipse.jdt.ui.JavaPerspective");
	}

	@Override
	protected void fillRibbonTab(RibbonTab tab) {
		createGroupLaunch(tab);
		createGroupOpen(tab);
		createGroupCreate(tab);
		createGroupNavigation(tab);
		// createGroupEditorTweaks(result);
	}

	// helping methods
	// ////////////////

	private void createGroupCreate(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Create");

		RibbonButtonGroup subgroup1 = new RibbonButtonGroup(group);
		RibbonButton rbNew = new RibbonButton(subgroup1, ICE
				.getImage("new_wiz.gif"), "New...", RibbonButton.STYLE_PUSH);
		RibbonButton rbNewJava = new RibbonButton(subgroup1, ICE
				.getImage("newjprj_wiz.gif"), "Project",
				RibbonButton.STYLE_PUSH);
		RibbonButton rbNewPlugin = new RibbonButton(subgroup1, ICE
				.getImage("newpprj_wiz.gif"), "Plugin", RibbonButton.STYLE_PUSH);

		RibbonButtonGroup subgroup2 = new RibbonButtonGroup(group);
		RibbonButton rbNewPackage = new RibbonButton(subgroup2, ICE
				.getImage("newpack_wiz.gif"), "Package",
				RibbonButton.STYLE_PUSH);
		RibbonButton rbNewClass = new RibbonButton(subgroup2, ICE
				.getImage("newclass_wiz.gif"), "Class", RibbonButton.STYLE_PUSH);
		RibbonButton rbNewInterface = new RibbonButton(subgroup2, ICE
				.getImage("newint_wiz.gif"), "Interface",
				RibbonButton.STYLE_PUSH);
	}

	private void createGroupCreate2(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Create");

		RibbonButton rbNew = new RibbonButton(group, ICE
				.getImage("new_wiz_30.png"), "New...", RibbonButton.STYLE_PUSH);

		RibbonButtonGroup subgroup1 = new RibbonButtonGroup(group);
		RibbonButton rbNewJava = new RibbonButton(subgroup1, ICE
				.getImage("newjprj_wiz.gif"), "Project",
				RibbonButton.STYLE_PUSH);
		RibbonButton rbNewPlugin = new RibbonButton(subgroup1, ICE
				.getImage("newpprj_wiz.gif"), "Plugin", RibbonButton.STYLE_PUSH);
		RibbonButton rbNewPackage = new RibbonButton(subgroup1, ICE
				.getImage("newpack_wiz.gif"), "Package",
				RibbonButton.STYLE_PUSH);

		RibbonButtonGroup subgroup2 = new RibbonButtonGroup(group);
		RibbonButton rbNewClass = new RibbonButton(subgroup2, ICE
				.getImage("newclass_wiz.gif"), "Class", RibbonButton.STYLE_PUSH);
		RibbonButton rbNewInterface = new RibbonButton(subgroup2, ICE
				.getImage("newint_wiz.gif"), "Interface",
				RibbonButton.STYLE_PUSH);
		RibbonButton rbNewTest = new RibbonButton(subgroup2, ICE
				.getImage("new_testcase.gif"), "Test", RibbonButton.STYLE_PUSH);

	}

	private void createGroupCreate3(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Create");

		RibbonButton rbNew = new RibbonButton(group, ICE
				.getImage("new_wiz_30.png"), "New...", RibbonButton.STYLE_PUSH);

		RibbonButtonGroup subgroup1 = new RibbonButtonGroup(group);
		RibbonButton rbNewJava = new RibbonButton(subgroup1, ICE
				.getImage("newjprj_wiz.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbNewPlugin = new RibbonButton(subgroup1, ICE
				.getImage("newpprj_wiz.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbNewPackage = new RibbonButton(subgroup1, ICE
				.getImage("newpack_wiz.gif"), null, RibbonButton.STYLE_PUSH);

		RibbonButtonGroup subgroup2 = new RibbonButtonGroup(group);
		RibbonButton rbNewClass = new RibbonButton(subgroup2, ICE
				.getImage("newclass_wiz.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbNewInterface = new RibbonButton(subgroup2, ICE
				.getImage("newint_wiz.gif"), null, RibbonButton.STYLE_PUSH);
		RibbonButton rbNewTest = new RibbonButton(subgroup2, ICE
				.getImage("new_testcase.gif"), null, RibbonButton.STYLE_PUSH);

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

	private void createGroupOpen(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Open");

		RibbonButton rbOpenType = new RibbonButton(group, ICE
				.getImage("opentype_30.png"), "Class", RibbonButton.STYLE_PUSH);

		RibbonButtonGroup subgroup = new RibbonButtonGroup(group);
		RibbonButton rbFind = new RibbonButton(subgroup, ICE
				.getImage("search.gif"), "Search", RibbonButton.STYLE_PUSH);
		RibbonButton rbOpenPdeArtifact = new RibbonButton(subgroup, ICE
				.getImage("open_artifact_obj.gif"), "Plugin",
				RibbonButton.STYLE_PUSH);
		RibbonButton rbOpenFile = new RibbonButton(subgroup, ICE
				.getImage("open_file_ev.png"), "File", RibbonButton.STYLE_PUSH);
	}

	private void createGroupEditorTweaks(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Editor Tweaks");

		RibbonButtonGroup sub1 = new RibbonButtonGroup(group);
		RibbonButton rbToggleMarkOccurences = new RibbonButton(sub1, ICE
				.getImage("mark_occurrences.gif"), null,
				RibbonButton.STYLE_TOGGLE);
		RibbonButton rbToggleBlockSelection = new RibbonButton(sub1, ICE
				.getImage("block_selection_mode.gif"), null,
				RibbonButton.STYLE_TOGGLE);

		RibbonButtonGroup sub2 = new RibbonButtonGroup(group);
		RibbonButton rbToggleBreadcrumb = new RibbonButton(sub2, ICE
				.getImage("toggle_breadcrumb.gif"), null,
				RibbonButton.STYLE_TOGGLE);
		RibbonButton rbShowWhitespace = new RibbonButton(sub2, ICE
				.getImage("show_whitespace_chars.gif"), null,
				RibbonButton.STYLE_TOGGLE);

		RibbonButtonGroup sub3 = new RibbonButtonGroup(group);
		RibbonButton rbFoldUninterestingElems = new RibbonButton(sub3, ICE
				.getImage("interest-folding.gif"), null,
				RibbonButton.STYLE_TOGGLE);
		RibbonButton rbPinEditor = new RibbonButton(sub3, ICE
				.getImage("pin_editor.gif"), null, RibbonButton.STYLE_TOGGLE);
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
