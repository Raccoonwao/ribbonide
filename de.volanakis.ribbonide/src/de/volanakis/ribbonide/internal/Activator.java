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

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import de.volanakis.ribbonide.internal.d.ICD;
import de.volanakis.ribbonide.internal.e.ICE;

public final class Activator extends AbstractUIPlugin {

	public static final String ID = "de.volanakis.ui.ide.ribbon";
	public static final String THEME_ID = "de.volanakis.ribbonide.theme";
	public static boolean DEBUG = "true".equalsIgnoreCase(System
			.getProperty("debug"));

	/*
	 * This bundle is a singleton, otherwise static fields would be trouble
	 */
	private static ColorRegistry colorReg;
	private static ServiceTracker logServiceTracker;
	private static LogService logService;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		logServiceTracker = new ServiceTracker(context, LogService.class
				.getName(), null);
		logServiceTracker.open();
		logService = (LogService) logServiceTracker.getService();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		logServiceTracker.close();
		logServiceTracker = null;
		logService = null;
		ICD.dispose();
		ICE.dispose();
		super.stop(context);
	}

	public static Color getSharedColor(RGB key) {
		if (colorReg == null) {
			Display display = Display.getCurrent();
			Assert.isNotNull(display);
			colorReg = new ColorRegistry(display, true);
		}
		Color result = colorReg.get(key.toString());
		if (result == null) {
			colorReg.put(key.toString(), key);
			result = colorReg.get(key.toString());
		}
		return result;
	}

	public static void log(Exception exc) {
		log(LogService.LOG_ERROR, exc);
	}

	public static void logWarning(Exception exception) {
		log(LogService.LOG_WARNING, exception);
	}

	public static void trace(String format, Object... args) {
		if (Activator.DEBUG) {
			StackTraceElement[] stackTrace = new Exception().getStackTrace();
			String filename = "?";
			if (stackTrace.length > 2) {
				StackTraceElement se = stackTrace[1];
				String fullname = se.getFileName();
				int idxDot = fullname.indexOf('.');
				if (idxDot != -1) {
					filename = fullname.substring(0, fullname.indexOf('.'));
				} else {
					filename = fullname;
				}
			}
			System.out.println(String.format("[%s] ", filename)
					+ String.format(format, args));
		}
	}

	// helping methods
	// ////////////////

	private static void log(int severity, Exception exc) {
		if (logService != null) {
			logService.log(severity, exc.getMessage(), exc);
		} else {
			exc.printStackTrace();
		}
	}

}
