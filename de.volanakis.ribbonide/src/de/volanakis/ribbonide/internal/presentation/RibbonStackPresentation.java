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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.presentations.IPresentablePart;
import org.eclipse.ui.presentations.IStackPresentationSite;
import org.eclipse.ui.presentations.StackDropResult;
import org.eclipse.ui.presentations.StackPresentation;

class RibbonStackPresentation extends StackPresentation {

	/** The presentation stack control */
	private Canvas presentation;
	/** The current part (view/editor) */
	private IPresentablePart current;

	protected RibbonStackPresentation(Composite parent,
			IStackPresentationSite stackSite) {
		super(stackSite);

		presentation = new Canvas(parent, SWT.NO_BACKGROUND);
	}

	@Override
	public void addPart(IPresentablePart newPart, Object cookie) {
		// TODO Auto-generated method stub
		System.out.println("# addPart " + newPart + ", " + cookie);

	}

	@Override
	public void dispose() {
		if (presentation != null) {
			presentation.dispose();
			presentation = null;
		}
	}

	@Override
	public StackDropResult dragOver(Control currentControl, Point location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Control getControl() {
		return presentation;
	}

	@Override
	public Control[] getTabList(IPresentablePart part) {
		return new Control[] { part.getControl() };
	}

	@Override
	public void removePart(IPresentablePart oldPart) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectPart(IPresentablePart toSelect) {
		System.out.println("# selectPart: "
				+ (toSelect != null ? toSelect.getName() : "null"));
		if (current == toSelect) {
			return;
		}
		setVisibleToControl(false);
		current = toSelect;
		setVisibleToControl(true);
		setBoundsToControl(presentation.getBounds());
	}

	@Override
	public void setActive(int newState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBounds(Rectangle bounds) {
		presentation.setBounds(bounds);
		setBoundsToControl(presentation.getBounds());
	}

	@Override
	public void setState(int state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVisible(boolean isVisible) {
		presentation.setVisible(isVisible);
		setVisibleToControl(isVisible);
		if (isVisible) {
			updateBounds();
		}
	}

	private void updateBounds() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showPaneMenu() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showSystemMenu() {
		// TODO Auto-generated method stub
	}

	// helping methods
	// ////////////////

	private void setBoundsToControl(Rectangle bounds) {
		if (current != null) {
			current.setBounds(presentation.getBounds());
		}
	}

	private void setVisibleToControl(boolean visible) {
		if (current != null) {
			current.setVisible(visible);
		}
	}

}
