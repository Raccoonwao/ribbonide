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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.hexapixel.widgets.generic.Utils;

/**
 * Test the 'IDE-Ribbon' without having to start the full IDE.
 */
public class RibbonTester {

	public static void main(String args[]) {
		Display display = new Display();

		Shell shell = new Shell(display, SWT.NO_TRIM | SWT.DOUBLE_BUFFERED);

		ShellBuilder shellBuilder = new ShellBuilder(shell);
		new EditTabBuilder(shellBuilder);
		new DebugTabBuilder(shellBuilder);
		new SyncTabBuilder(shellBuilder);
		shellBuilder.create();

		shell.setLayout(new FillLayout(SWT.VERTICAL));
		Utils.centerDialogOnScreen(shell);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
