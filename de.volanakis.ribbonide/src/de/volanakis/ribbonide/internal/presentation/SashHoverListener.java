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

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Sash;

import de.volanakis.ribbonide.internal.Activator;
import de.volanakis.ribbonide.internal.SharedColors;

/**
 * Changes the {@link Sash} background on hover. Restores on exit.
 */
final class SashHoverListener extends MouseTrackAdapter {

	@Override
	public void mouseHover(MouseEvent e) {
		Control control = (Control) e.widget;
		control.setBackground(Activator
				.getSharedColor(SharedColors.WINDOW_SHASH_HOVER));
	}

	@Override
	public void mouseExit(MouseEvent e) {
		Control control = (Control) e.widget;
		control.setBackground(Activator.getSharedColor(SharedColors.WINDOW_BG));
	}

}
