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
package de.volanakis.ribbonide.internal.app;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.internal.ide.application.IDEWorkbenchAdvisor;
import org.eclipse.ui.internal.ide.application.IDEWorkbenchWindowAdvisor;

import de.volanakis.ribbonide.internal.Activator;
import de.volanakis.ribbonide.internal.DebugTabBuilder;
import de.volanakis.ribbonide.internal.EditTabBuilder;
import de.volanakis.ribbonide.internal.SharedColors;
import de.volanakis.ribbonide.internal.ShellBuilder;
import de.volanakis.ribbonide.internal.SyncTabBuilder;

public class RibbonIDEWorkbenchWindowAdvisor extends IDEWorkbenchWindowAdvisor {

	public RibbonIDEWorkbenchWindowAdvisor(IDEWorkbenchAdvisor wbAdvisor,
			IWorkbenchWindowConfigurer configurer) {
		super(wbAdvisor, configurer);
	}

	@Override
	public void createWindowContents(final Shell shell) {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();

		Control ribbon = createRibbon(configurer.getWindow());

		Composite comp = new Composite(shell, SWT.NONE);
		comp.setBackground(Activator.getSharedColor(SharedColors.WINDOW_BG));
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(comp);

		FormLayout layout = new FormLayout();
		shell.setLayout(layout);
		shell.setBackground(Activator.getSharedColor(SharedColors.WINDOW_BG));
		FormData ribbonData = new FormData();
		ribbonData.left = new FormAttachment(0, 1);
		ribbonData.right = new FormAttachment(100, -1);
		ribbon.setLayoutData(ribbonData);

		FormData compData = new FormData();
		compData.top = new FormAttachment(ribbon, 2);
		compData.left = new FormAttachment(0, 3);
		compData.right = new FormAttachment(100, -4);
		compData.bottom = new FormAttachment(100, -4);
		comp.setLayoutData(compData);

		Control page = configurer.createPageComposite(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(page);

		final Control status = configurer.createStatusLineControl(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(status);

		final Menu menuBar = configurer.createMenuBar();
		installMenuRevealer(shell, menuBar);

		// we are only creating this to avoid an NPE in workbench code
		Control coolbar = configurer.createCoolBarControl(comp);
		GridDataFactory.fillDefaults().exclude(true).applyTo(coolbar);
	}

	@Override
	public void preWindowOpen() {
		super.preWindowOpen();
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setShellStyle(SWT.NO_TRIM | SWT.DOUBLE_BUFFERED);
		configurer.setShowCoolBar(false);
		configurer.setShowPerspectiveBar(false);
	}

	// helping methods
	// ////////////////

	private Control createRibbon(IWorkbenchWindow window) {
		ShellBuilder shellBuilder = new ShellBuilder(window);
		new EditTabBuilder(shellBuilder);
		new DebugTabBuilder(shellBuilder);
		new SyncTabBuilder(shellBuilder);
		return shellBuilder.create();
	}

	private void installMenuRevealer(final Shell shell, final Menu menuBar) {
		// ribbon.addMouseMoveListener(new MouseMoveListener() {
		// public void mouseMove(MouseEvent e) {
		// if (e.y < 5) {
		// shell.setMenuBar(menuBar);
		// } else {
		// shell.setMenuBar(null);
		// }
		// }
		// });
		final Listener toggleMenuListener = new Listener() {
			public void handleEvent(Event event) {
				if (!fromSameShell(shell, event)) {
					return;
				}
				if (event.type == SWT.KeyDown && event.keyCode == SWT.ALT) {
					if (shell.getMenuBar() == null) {
						shell.setMenuBar(menuBar);
					}
				} else if (event.type == SWT.KeyUp && event.keyCode == SWT.ALT) {
					if (shell.getMenuBar() != null) {
						shell.setMenuBar(null);
					}
				}
			}

			private boolean fromSameShell(final Shell shell, Event event) {
				boolean result = true;
				if (event.widget instanceof Control) {
					Control control = (Control) event.widget;
					result = shell == control.getShell();
				}
				return result;
			}
		};
		menuBar.addMenuListener(new MenuAdapter() {
			public void menuHidden(MenuEvent e) {
				shell.setMenuBar(null);
			}
		});
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				e.display.removeFilter(SWT.KeyUp, toggleMenuListener);
				e.display.removeFilter(SWT.KeyDown, toggleMenuListener);
			}
		});
		shell.getDisplay().addFilter(SWT.KeyUp, toggleMenuListener);
		shell.getDisplay().addFilter(SWT.KeyDown, toggleMenuListener);
	}

}
