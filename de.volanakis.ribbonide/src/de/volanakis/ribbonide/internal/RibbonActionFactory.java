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

import static com.hexapixel.widgets.ribbon.AbstractRibbonGroupItem.STYLE_PUSH;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandEvent;
import org.eclipse.core.commands.ICommandListener;
import org.eclipse.core.commands.common.CommandException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.actions.NewWizardShortcutAction;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.IWizardRegistry;

import com.hexapixel.widgets.ribbon.AbstractRibbonGroupItem;
import com.hexapixel.widgets.ribbon.IDisposeListener;
import com.hexapixel.widgets.ribbon.QuickAccessShellToolbar;
import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.hexapixel.widgets.ribbon.RibbonTabFolder;
import com.hexapixel.widgets.ribbon.RibbonToolbarGrouping;

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
				STYLE_PUSH);
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
				STYLE_PUSH);
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
				STYLE_PUSH);
		result.setEnabled(false);
		if (window != null) {
			new ActionDelegate(result, ActionFactory.REDO.create(window));
		}
		return result;
	}

	public static RibbonButton createOpenType(RibbonGroup parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("opentype_30.png"), null, "Type", STYLE_PUSH);
		if (window != null) {
			new CommandDelegate(result, "org.eclipse.jdt.ui.navigate.open.type");
		}
		return result;
	}

	public static RibbonButton createOpenPdeArtifact(RibbonButtonGroup parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("open_artifact_obj.gif"), ICD
				.getImage("open_artifact_obj.gif"), "Plugin", STYLE_PUSH);
		if (window != null) {
			new CommandDelegate(result, "org.eclipse.pde.ui.openPluginArtifact");
		}
		return result;
	}

	public static RibbonButton createOpenResource(RibbonButtonGroup parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("open_file_ev.png"),
				ICD.getImage("open_file_ev.png"), "File", STYLE_PUSH);
		if (window != null) {
			new CommandDelegate(result, "org.eclipse.ui.navigate.openResource");
		}
		return result;
	}

	public static RibbonButton createSearch(RibbonButtonGroup parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("search.gif"), "Search", STYLE_PUSH);
		if (window != null) {
			new CommandDelegate(result,
					"org.eclipse.search.ui.openSearchDialog");
		}
		return result;
	}

	public static RibbonButton createGoLastEdit(RibbonToolbarGrouping parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("last_edit_pos.gif"), ICD
				.getImage("last_edit_pos.gif"), STYLE_PUSH);
		if (window != null) {
			new CommandDelegate(result,
					"org.eclipse.ui.edit.text.gotoLastEditPosition");
		}
		return result;
	}

	public static RibbonButton createGoBackward(RibbonToolbarGrouping parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("backward_nav.gif"),
				ICD.getImage("backward_nav.gif"), STYLE_PUSH);
		if (window != null) {
			new CommandDelegate(result,
					"org.eclipse.ui.navigate.backwardHistory");
		}
		return result;
	}

	public static RibbonButton createGoForward(RibbonToolbarGrouping parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("forward_nav.gif"), ICD.getImage("forward_nav.gif"),
				STYLE_PUSH);
		if (window != null) {
			new CommandDelegate(result,
					"org.eclipse.ui.navigate.forwardHistory");
		}
		return result;
	}

	public static RibbonButton createGoNext(RibbonToolbarGrouping parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("next_nav.gif"), ICD.getImage("next_nav.gif"),
				STYLE_PUSH);
		if (window != null) {
			new CommandDelegate(result, "org.eclipse.ui.navigate.next");
		}
		return result;
	}

	public static RibbonButton createGoPrevious(RibbonToolbarGrouping parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("next_nav.gif"), ICD.getImage("next_nav.gif"),
				STYLE_PUSH);
		if (window != null) {
			new CommandDelegate(result, "org.eclipse.ui.navigate.previous");
		}
		return result;
	}

	public static RibbonButton createNew(RibbonButtonGroup parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("new_wiz.gif"), ICD.getImage("new_wiz.gif"),
				"New...", STYLE_PUSH);
		if (window != null) {
			new CommandDelegate(result, "org.eclipse.ui.newWizard");
		}
		return result;
	}

	public static RibbonButton createNewJava(RibbonButtonGroup parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("newjprj_wiz.gif"), ICD.getImage("newjprj_wiz.gif"),
				"Project", STYLE_PUSH);
		if (window != null) {
			new WizardDelegate(result, window,
					"org.eclipse.jdt.ui.wizards.JavaProjectWizard");
		}
		return result;
	}

	public static RibbonButton createNewPlugin(RibbonButtonGroup parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("newpprj_wiz.gif"), ICD.getImage("newpprj_wiz.gif"),
				"Plugin", STYLE_PUSH);
		if (window != null) {
			new WizardDelegate(result, window,
					"org.eclipse.pde.ui.NewProjectWizard");
		}
		return result;
	}

	public static RibbonButton createNewPackage(RibbonButtonGroup parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("newpack_wiz.gif"), ICD.getImage("newpack_wiz.gif"),
				"Package", STYLE_PUSH);
		if (window != null) {
			new WizardDelegate(result, window,
					"org.eclipse.jdt.ui.wizards.NewPackageCreationWizard");
		}
		return result;
	}

	public static RibbonButton createNewClass(RibbonButtonGroup parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("newclass_wiz.gif"),
				ICD.getImage("newclass_wiz.gif"), "Class", STYLE_PUSH);
		if (window != null) {
			new WizardDelegate(result, window,
					"org.eclipse.jdt.ui.wizards.NewClassCreationWizard");
		}
		return result;
	}

	public static RibbonButton createNewInterface(RibbonButtonGroup parent,
			IWorkbenchWindow window) {
		RibbonButton result = new RibbonButton(parent, ICE
				.getImage("newint_wiz.gif"), ICD.getImage("newint_wiz.gif"),
				"Interface", STYLE_PUSH);
		if (window != null) {
			new WizardDelegate(result, window,
					"org.eclipse.jdt.ui.wizards.NewInterfaceCreationWizard");
		}
		return result;
	}

	public static void configureHelp(RibbonTabFolder rtf,
			IWorkbenchWindow window) {
		rtf.setHelpImage(ICE.getImage("help_contents.gif"));
		if (window != null) {
			new CommandDelegate(rtf.getHelpButton(),
					"org.eclipse.ui.help.helpContents");
		}
	}

	// helping methods
	// ////////////////

	private static ICommandService getCommandService() {
		return (ICommandService) PlatformUI.getWorkbench().getService(
				ICommandService.class);
	}

	private static IHandlerService getHandlerService() {
		return (IHandlerService) PlatformUI.getWorkbench().getService(
				IHandlerService.class);
	}

	// helping classes
	// ////////////////

	private static final class ActionDelegate extends SelectionAdapter
			implements IPropertyChangeListener, IDisposeListener {

		private final RibbonButton button;
		private final IAction action;

		public ActionDelegate(final RibbonButton button, IAction action) {
			this.button = button;
			this.action = action;

			button.addDisposeListener(this);
			action.addPropertyChangeListener(this);
			button.addSelectionListener(this);
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			Activator.trace("sel: %s", action.getId());
			button.setSelected(false);
			action.run();
		}

		public void propertyChange(PropertyChangeEvent event) {
			button.setEnabled(action.isEnabled());
		}

		public void itemDisposed(AbstractRibbonGroupItem item) {
			button.removeSelectionListener(this);
			action.removePropertyChangeListener(this);
		}
	}

	private static final class CommandDelegate extends SelectionAdapter
			implements ICommandListener, IDisposeListener {

		private final RibbonButton button;
		private final String commandId;
		private final Command command;

		public CommandDelegate(RibbonButton button, String commandId) {
			this.button = button;
			Assert.isNotNull(commandId);
			this.commandId = commandId;

			command = getCommandService().getCommand(commandId);
			if (!command.isDefined()) {
				Activator.logWarning(new Exception("Undefined command: "
						+ commandId));
			}
			button.addDisposeListener(this);
			command.addCommandListener(this);
			boolean enabled = command.isDefined() ? command.isEnabled() : false;
			button.setEnabled(enabled);
			button.addSelectionListener(this);
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			Activator.trace("sel: %s - e:%s, h:%s", commandId, command
					.isEnabled(), command.isHandled());
			button.setSelected(false);
			try {
				getHandlerService().executeCommand(commandId, null);
				if (button.isEnabled() != command.isEnabled()) {
					button.setEnabled(command.isEnabled());
				}
			} catch (CommandException cex) {
				Activator.log(cex);
			}
		}

		public void commandChanged(CommandEvent commandEvent) {
			if (commandEvent.isEnabledChanged()) {
				boolean isEnabled = commandEvent.getCommand().isEnabled();
				Activator.trace("enabl: %s? %s", commandId, isEnabled);
				button.setEnabled(isEnabled);
			}
		}

		public void itemDisposed(AbstractRibbonGroupItem item) {
			button.removeSelectionListener(this);
			command.removeCommandListener(this);
		}
	}

	private static final class WizardDelegate extends SelectionAdapter
			implements IPropertyChangeListener, IDisposeListener {

		private RibbonButton button;
		private IAction action;

		public WizardDelegate(RibbonButton button, IWorkbenchWindow window,
				String wizardId) {
			this.button = button;

			IWizardRegistry nwRegistry = PlatformUI.getWorkbench()
					.getNewWizardRegistry();
			IWizardDescriptor descriptor = nwRegistry.findWizard(wizardId);
			if (descriptor != null) {
				action = new NewWizardShortcutAction(window, descriptor);
				button.setEnabled(action.isEnabled());

				button.addDisposeListener(this);
				action.addPropertyChangeListener(this);
				button.addSelectionListener(this);
			} else {
				Activator.logWarning(new Exception("Undefined wizard: "
						+ wizardId));
				button.setEnabled(false);
			}
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			button.setSelected(false);
			action.run();
		}

		public void propertyChange(PropertyChangeEvent event) {
			button.setEnabled(action.isEnabled());
		}

		public void itemDisposed(AbstractRibbonGroupItem item) {
			action.removePropertyChangeListener(this);
		}
	}

}
