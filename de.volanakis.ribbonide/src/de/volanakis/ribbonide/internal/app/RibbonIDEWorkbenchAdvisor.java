/*******************************************************************************
 * Copyright (c) 2003, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Elias Volanakis - added ribbon support
 *******************************************************************************/
package de.volanakis.ribbonide.internal.app;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.ide.AboutInfo;
import org.eclipse.ui.internal.ide.application.IDEWorkbenchAdvisor;
import org.eclipse.ui.themes.IThemeManager;

import de.volanakis.ribbonide.internal.Activator;
import de.volanakis.ribbonide.internal.presentation.RibbonPresentationFactory;
import de.volanakis.ribbonide.internal.presentation.RibbonPresentationFactory2;

public class RibbonIDEWorkbenchAdvisor extends IDEWorkbenchAdvisor {

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		configureLookAndFeel(configurer);
		return new RibbonIDEWorkbenchWindowAdvisor(this, configurer);
	}

	public String getInitialWindowPerspectiveId() {
		int index = PlatformUI.getWorkbench().getWorkbenchWindowCount() - 1;

		String perspectiveId = null;
		AboutInfo[] welcomeInfos = getWelcomePerspectiveInfos();
		if (index >= 0 && welcomeInfos != null && index < welcomeInfos.length) {
			perspectiveId = welcomeInfos[index].getWelcomePerspectiveId();
		}
		if (perspectiveId == null) {
			perspectiveId = "org.eclipse.jdt.ui.JavaPerspective";
		}
		return perspectiveId;
	}

	// helping methods
	// ////////////////

	private void configureLookAndFeel(IWorkbenchWindowConfigurer configurer) {
		if ("true".equalsIgnoreCase(System.getProperty("experimental"))) {
			configurer.setPresentationFactory(new RibbonPresentationFactory2());
		} else {
			configurer.setPresentationFactory(new RibbonPresentationFactory());
		}
		IThemeManager themeMan = PlatformUI.getWorkbench().getThemeManager();
		themeMan.setCurrentTheme(Activator.THEME_ID);
	}

}
