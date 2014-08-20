package com.br.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;


public class GettingArchitecture implements IObjectActionDelegate {

	private Shell shell;

	private IFile file;
	
	public GettingArchitecture() {

	}

	@Override
	public void run(IAction action) {
		
		System.out.println("O arquivo..." + this.file.getName());
		
	}
	
	private List<String> getAllJavaFile(IResource allResource[],
			List<String> listOfAllFileJava) throws CoreException {

		IFile resourceFile = null;
		IFolder resourceFolder = null;

		if (allResource.length < 1) {

			return listOfAllFileJava;

		} else {

			for (IResource iResource : allResource) {

				if (iResource instanceof IFile) {
					resourceFile = (IFile) iResource;

					if (resourceFile.getFileExtension().equals("java")) {

						listOfAllFileJava.add(resourceFile.getLocation()
								.toString());

					}

				} else if (iResource instanceof IFolder) {

					resourceFolder = (IFolder) iResource;

					getAllJavaFile(resourceFolder.members(), listOfAllFileJava);

				}
			}

			return listOfAllFileJava;
		}

	}
	

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {

		if (selection instanceof IStructuredSelection) {
			action.setEnabled(updateSelection((IStructuredSelection) selection));
		} else {
			action.setEnabled(false);
		}

	}

	public boolean updateSelection(IStructuredSelection selection) {
		for (Iterator<?> objects = selection.iterator(); objects.hasNext();) {
			Object object = AdapterFactoryEditingDomain.unwrap(objects.next());
			if (object instanceof IJavaProject) {
				this.file = (IFile) object;
				return true;
			}
		}
		return false;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();

	}

}
