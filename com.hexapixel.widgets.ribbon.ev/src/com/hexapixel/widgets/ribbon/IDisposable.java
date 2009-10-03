/*******************************************************************************
 * Copyright (c) Emil Crumhorn and others - Hexapixel.com - emil.crumhorn@gmail.com
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    emil.crumhorn@gmail.com  - initial API and implementation
 *    eclipse-dev@volanakis.de - support for dispose listeners
 *******************************************************************************/ 

package com.hexapixel.widgets.ribbon;


interface IDisposable {
	
	public void dispose();
	
	public void addDisposeListener(IDisposeListener listener);
	public void removeDisposeListener(IDisposeListener listener);
}
