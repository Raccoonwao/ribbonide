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

import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Widget;

public final class SWTUtils {

	public static void dispose(Resource resource) {
		if (resource != null && !resource.isDisposed()) {
			resource.dispose();
		}
	}

	public static void dispose(Widget widget) {
		if (widget != null && !widget.isDisposed()) {
			widget.dispose();
		}
	}

}
