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
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchThemeConstants;
import org.eclipse.ui.presentations.IStackPresentationSite;
import org.eclipse.ui.presentations.StackPresentation;
import org.eclipse.ui.presentations.WorkbenchPresentationFactory;
import org.eclipse.ui.themes.ITheme;
import org.eclipse.ui.themes.IThemeManager;

import de.volanakis.ribbonide.internal.Activator;
import de.volanakis.ribbonide.internal.SWTUtils;
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
public class RibbonPresentationFactory extends WorkbenchPresentationFactory {

	/**
	 * This is a hack to set the right background color for the presentation
	 * toolbars. We sample the pixel next to the control. If it's
	 * RGB(160,160,160), then the toolbar is within the CTabFolder folder.
	 */
	private static final Listener LISTENER = new Listener() {
		private GC gc;
		private Image image;
		private boolean mustInit = true;
		private Color bgColor;
		private RGB borderRGB;
		private RGB tabRGB;

		public void handleEvent(Event event) {
			// System.out.println(event.type + " " + event.widget.hashCode());
			if (mustInit) {
				mustInit = false;
				initFields(event);
			}
			Control control = (Control) event.widget;
			control.getShell().update();
			Point px = control.toDisplay(0, -1);
			gc.copyArea(image, px.x, px.y);
			ImageData id = image.getImageData();
			RGB rgb = id.palette.getRGB(id.getPixel(0, 0));
			if (!(borderRGB.equals(rgb) || tabRGB.equals(rgb))) {
				control.setBackground(bgColor);
				// System.out.println(control.handle + " " + px + ": " + rgb
				// + " -> gray " + event.type);
			} else {
				control.setBackground(Activator
						.getSharedColor(SharedColors.WINDOW_BG));
				// System.out.println(control.handle + " " + px + ": " + rgb
				// + " -> blue " + event.type);
			}
		}

		private void initFields(Event event) {
			gc = new GC(event.display);
			image = new Image(event.display, 1, 1);
			bgColor = event.display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
			borderRGB = event.display.getSystemColor(
					SWT.COLOR_WIDGET_NORMAL_SHADOW).getRGB();
			tabRGB = getThemeColor(IWorkbenchThemeConstants.INACTIVE_TAB_BG_START);
			event.display.disposeExec(new Runnable() {
				public void run() {
					SWTUtils.dispose(gc);
					SWTUtils.dispose(image);
					mustInit = true;
				}
			});
		}

		private RGB getThemeColor(String key) {
			IWorkbench workbench = PlatformUI.getWorkbench();
			IThemeManager themeMgr = workbench.getThemeManager();
			ITheme theme = themeMgr.getCurrentTheme();
			return theme.getColorRegistry().getRGB(key);
		}
	};

	private static final MouseTrackListener SASH_HOVER_LISTENER = new SashHoverListener();

	@Override
	public StackPresentation createEditorPresentation(Composite parent,
			IStackPresentationSite site) {
		StackPresentation presentation = super.createEditorPresentation(parent,
				site);
		processComposite(parent);
		return presentation;
	}

	@Override
	public StackPresentation createStandaloneViewPresentation(Composite parent,
			IStackPresentationSite site, boolean showTitle) {
		StackPresentation presentation = super
				.createStandaloneViewPresentation(parent, site, showTitle);
		processComposite(parent);
		return presentation;
	}

	@Override
	public StackPresentation createViewPresentation(Composite parent,
			IStackPresentationSite site) {
		StackPresentation presentation = super.createViewPresentation(parent,
				site);
		processComposite(parent);
		return presentation;
	}

	@Override
	public Sash createSash(Composite parent, int style) {
		Sash sash = super.createSash(parent, style);
		sash.setBackground(Activator.getSharedColor(SharedColors.WINDOW_BG));
		sash.addMouseTrackListener(SASH_HOVER_LISTENER);
		return sash;
	}

	// helping methods
	// ////////////////

	private void processComposite(Composite parent) {
		boolean isTabFolder = parent instanceof CTabFolder;
		if (isTabFolder) {
			processFolder((CTabFolder) parent);
		}
		for (Control child : parent.getChildren()) {
			if (child instanceof ToolBar) {
				processToolbar((ToolBar) child);
			} else if (child instanceof Composite && !isTabFolder) {
				processComposite((Composite) child);
			}
		}
	}

	private void processFolder(final CTabFolder folder) {
		if (folder.getData("proc") == null) {
			folder.setData("proc", Boolean.TRUE);
			folder.setBackground(Activator
					.getSharedColor(SharedColors.WINDOW_BG));
			folder.getParent().setBackground(
					Activator.getSharedColor(SharedColors.WINDOW_BG));
			folder.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					// the contents of a tab are created lazily
					// need to rerun listener creation after switching tabs
					e.display.asyncExec(new Runnable() {
						public void run() {
							if (folder.isDisposed()) {
								return;
							}
							processComposite(folder.getParent());
						}
					});
				}
			});
		}
	}

	private void processToolbar(final ToolBar toolBar) {
		if (toolBar.getData("proc") == null) {
			toolBar.setData("proc", Boolean.TRUE);
			/*
			 * The whole thing makes 'reset perpective' seemengly slower,
			 * because it will paint each part stack individually, but it fixes
			 * the bg color problem. Acceptable from my POV, since we don't
			 * create / reset perspectives often.
			 * 
			 * The alternative is to get rid of the blue bg color totally, but
			 * it doesn't look as nice.
			 */
			// toolBar.addListener(SWT.Paint, LISTENER);
			// toolBar.addListener(SWT.Move, LISTENER);
			toolBar.addListener(SWT.Resize, LISTENER);
			toolBar.addListener(SWT.MouseEnter, LISTENER);
			// System.out.println("## added lsrn to " + toolBar.handle);
			toolBar.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if (toolBar.isDisposed()) {
						return;
					}
					Event event = new Event();
					event.display = toolBar.getDisplay();
					event.widget = toolBar;
					LISTENER.handleEvent(event);
				}
			});
		}
	}
}
