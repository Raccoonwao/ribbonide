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

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

public abstract class AbstractWindowBuilder extends AbstractShellBuilder {

	private final IWorkbenchWindow window;

	AbstractWindowBuilder(Shell shell) {
		super(shell, computeTitle());
		window = null;
	}

	public AbstractWindowBuilder(IWorkbenchWindow window) {
		super(window.getShell(), computeTitle());
		this.window = window;
	}

	/**
	 * @return the IWorkbenchWindow for this builder or null
	 */
	public final IWorkbenchWindow getWindow() {
		return window;
	}

	// helping methods
	// ////////////////

	private static String computeTitle() {
		IProduct product = Platform.getProduct();
		return product != null ? product.getName() : "Eclipse SDK"; //$NON-NLS-1$
	}
}
