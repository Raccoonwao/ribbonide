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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;

import com.hexapixel.widgets.ribbon.QuickAccessShellToolbar;
import com.hexapixel.widgets.ribbon.RibbonButton;

import de.volanakis.ribbonide.internal.d.ICD;
import de.volanakis.ribbonide.internal.e.ICE;

public final class RibbonActionFactory {

	/*
	 * Later: see org.eclipse.ui.internal.ide.actions.LTKLauncher for an example
	 * how to do this with commands and handlers.
	 */

	public static RibbonButton createSave(QuickAccessShellToolbar qat,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(qat, ICE
				.getImage("save_edit.gif"), ICD.getImage("save_edit_ev.gif"),
				RibbonButton.STYLE_PUSH);
		result.setEnabled(false);
		if (window != null) {
			new ActionDelegate(result, ActionFactory.SAVE.create(window));
		}
		return result;
	}

	public static RibbonButton createUndo(QuickAccessShellToolbar qat,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(qat, ICE
				.getImage("undo_edit.gif"), ICD.getImage("undo_edit.gif"),
				RibbonButton.STYLE_PUSH);
		result.setEnabled(false);
		if (window != null) {
			new ActionDelegate(result, ActionFactory.UNDO.create(window));
		}
		return result;
	}

	public static RibbonButton createRedo(QuickAccessShellToolbar qat,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(qat, ICE
				.getImage("redo_edit.gif"), ICD.getImage("redo_edit.gif"),
				RibbonButton.STYLE_PUSH);
		result.setEnabled(false);
		if (window != null) {
			new ActionDelegate(result, ActionFactory.REDO.create(window));
		}
		return result;
	}

	// helping classes
	// ////////////////

	private static final class ActionDelegate extends SelectionAdapter
			implements IPropertyChangeListener {

		private final RibbonButton button;
		private final IAction action;

		public ActionDelegate(RibbonButton button, IAction action) {
			button.addSelectionListener(this);
			action.addPropertyChangeListener(this);
			this.button = button;
			this.action = action;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			button.setSelected(false);
			action.run();
		}

		public void propertyChange(PropertyChangeEvent event) {
			// TODO [ev] what about disposal... need to remove PCL
			button.setEnabled(action.isEnabled());
		}
	}

}
