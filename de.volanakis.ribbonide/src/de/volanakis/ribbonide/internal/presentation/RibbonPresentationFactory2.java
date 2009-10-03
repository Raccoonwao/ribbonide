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
package de.volanakis.ribbonide.internal.presentation;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.ui.presentations.IStackPresentationSite;
import org.eclipse.ui.presentations.StackPresentation;
import org.eclipse.ui.presentations.WorkbenchPresentationFactory;

import de.volanakis.ribbonide.internal.Activator;
import de.volanakis.ribbonide.internal.SharedColors;

/**
 * This presentation tweaks the part stacks to make them more vista like.
 * <ul>
 * <li>blue background for CTabFolders</li>
 * <li>blue background for CTab toolbars when next to the tab</li>
 * <li>gray background for CTab toolbars when below the tab</li>
 * <li>blue background for CTabFolder parent</li>
 * <li>blue background for Sashes</li>
 * </ul>
 * Coloring the toolbars was very tricky. Each stack has one and each CTabFolder
 * has one.
 */
public class RibbonPresentationFactory2 extends WorkbenchPresentationFactory {

	@Override
	public StackPresentation createEditorPresentation(Composite parent,
			IStackPresentationSite site) {
		StackPresentation presentation = super.createEditorPresentation(parent,
				site);
		parent.setBackground(Activator.getSharedColor(SharedColors.WINDOW_BG));
		return presentation;
	}

	@Override
	public StackPresentation createStandaloneViewPresentation(Composite parent,
			IStackPresentationSite site, boolean showTitle) {
		return new RibbonStackPresentation(parent, site);
	}

	@Override
	public StackPresentation createViewPresentation(Composite parent,
			IStackPresentationSite site) {
		return new RibbonStackPresentation(parent, site);
	}

	@Override
	public Sash createSash(Composite parent, int style) {
		Sash sash = super.createSash(parent, style);
		sash.setBackground(Activator.getSharedColor(SharedColors.WINDOW_BG));
		return sash;
	}

}
