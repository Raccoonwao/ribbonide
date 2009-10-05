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

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
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
import org.eclipse.ui.internal.WorkbenchWindow;
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

		// installMenuRevealer(ribbon, configurer);
		installMenuRevealer2(ribbon, configurer);

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

	private void installMenuRevealer(final Control ribbon,
			final IWorkbenchWindowConfigurer configurer) {
		Shell shell = ribbon.getShell();
		Menu menuBar = configurer.createMenuBar();
		final MenuRevealer menuRevealer = new MenuRevealer(shell, menuBar);

		ribbon.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if (e.y < 5) {
					menuRevealer.showMenu();
				} else {
					menuRevealer.hideMenu();
				}
			}
		});

		menuBar.addMenuListener(new MenuAdapter() {
			@Override
			public void menuHidden(MenuEvent e) {
				menuRevealer.hideMenu();
			}
		});

		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				e.display.removeFilter(SWT.KeyUp, menuRevealer);
				e.display.removeFilter(SWT.KeyDown, menuRevealer);
			}
		});
		shell.getDisplay().addFilter(SWT.KeyUp, menuRevealer);
		shell.getDisplay().addFilter(SWT.KeyDown, menuRevealer);
	}

	private void installMenuRevealer2(final Control ribbon,
			final IWorkbenchWindowConfigurer configurer) {
		final Shell shell = ribbon.getShell();
		final Shell menuShell = new Shell(shell, SWT.NO_TRIM);
		WorkbenchWindow window = (WorkbenchWindow) configurer.getWindow();
		Menu menuBar = window.getMenuManager().createMenuBar(menuShell);
		menuShell.setMenuBar(menuBar);

		final MenuRevealer2 menuRevealer = new MenuRevealer2(shell, menuShell);

		menuShell.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.F4 && e.stateMask == SWT.ALT) {
					configurer.getWindow().close();
				} else if (e.keyCode == SWT.ESC) {
					menuRevealer.hideMenu();
				}
			}
		});

		ribbon.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if (e.y < 5) {
					menuRevealer.showMenu();
				} else {
					menuRevealer.hideMenu();
				}
			}
		});

		menuBar.addMenuListener(new MenuAdapter() {
			@Override
			public void menuHidden(MenuEvent e) {
				menuRevealer.hideMenu();
			}
		});

		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				e.display.removeFilter(SWT.KeyUp, menuRevealer);
				e.display.removeFilter(SWT.KeyDown, menuRevealer);
			}
		});
		shell.getDisplay().addFilter(SWT.KeyUp, menuRevealer);
		shell.getDisplay().addFilter(SWT.KeyDown, menuRevealer);
	}

	// helping classes
	// ////////////////

	/**
	 * Reveals/hides a MenuBar in the current shell.
	 */
	private static final class MenuRevealer implements Listener {

		private final Shell shell;
		private final Menu menuBar;

		MenuRevealer(Shell shell, Menu menuBar) {
			Assert.isNotNull(shell);
			this.shell = shell;
			Assert.isNotNull(menuBar);
			this.menuBar = menuBar;
		}

		public void handleEvent(Event event) {
			if (!fromSameShell(shell, event)) {
				return;
			}
			if (event.type == SWT.KeyDown && event.keyCode == SWT.ALT) {
				showMenu();
			} else if (event.type == SWT.KeyUp && event.keyCode == SWT.ALT) {
				hideMenu();
			}
		}

		public void hideMenu() {
			if (shell.getMenuBar() != null) {
				shell.setRedraw(false);
				try {
					shell.setMenuBar(null);
				} finally {
					shell.setRedraw(true);
				}
			}
		}

		public void showMenu() {
			if (shell.getMenuBar() == null) {
				shell.setRedraw(false);
				try {
					shell.setMenuBar(menuBar);
				} finally {
					shell.setRedraw(true);
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
	}

	/**
	 * Reveals/hides a second shell with the menu.
	 */
	private static final class MenuRevealer2 implements Listener {

		private final Shell shell;
		private final Shell menuShell;

		MenuRevealer2(Shell shell, Shell menuShell) {
			Assert.isNotNull(shell);
			this.shell = shell;
			menuShell.open();
			menuShell.setVisible(false);
			menuShell.setAlpha(230);
			this.menuShell = menuShell;
		}

		public void handleEvent(Event event) {
			if (!(fromSameShell(shell, event) || fromSameShell(menuShell, event))) {
				return;
			}
			if (event.type == SWT.KeyDown && event.keyCode == SWT.ALT) {
				showMenu();
			} else if (event.type == SWT.KeyUp && event.keyCode == SWT.ALT) {
				hideMenu();
			}
		}

		public void hideMenu() {
			if (menuShell.isVisible()) {
				boolean hasFocus = menuShell.isFocusControl();
				menuShell.setVisible(false);
				if (hasFocus) {
					shell.setFocus();
				}
			}
		}

		public void showMenu() {
			if (!menuShell.isVisible()) {
				Point dLoc = shell.toDisplay(0, 0);
				Point size = shell.getSize();
				menuShell.setLocation(dLoc.x, dLoc.y);
				menuShell.setSize(size.x, 20 + 10);
				updateRegion(menuShell);
				menuShell.setVisible(true);
				menuShell.setFocus();
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

		private void updateRegion(Shell menuShell) {
			Region region = new Region(menuShell.getDisplay());
			Rectangle bounds = menuShell.getBounds();
			int maxX = bounds.width;
			int maxY = bounds.height;

			region.add(0, 0, maxX, maxY);

			int screenWidth = menuShell.getMonitor().getBounds().x;
			if (maxX != screenWidth) { // not maximized
				// top left
				region.subtract(0, 0, 4, 1);
				region.subtract(0, 1, 2, 1);
				region.subtract(0, 2, 1, 1);
				region.subtract(0, 3, 1, 1);

				// top right
				region.subtract(maxX - 4, 0, 4, 1);
				region.subtract(maxX - 2, 1, 2, 1);
				region.subtract(maxX - 1, 2, 1, 1);
				region.subtract(maxX - 1, 3, 1, 1);
			}

			menuShell.setRegion(region);
		}

	}

}
