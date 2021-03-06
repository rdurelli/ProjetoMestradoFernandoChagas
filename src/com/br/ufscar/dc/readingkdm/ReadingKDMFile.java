package com.br.ufscar.dc.readingkdm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.omg.kdm.action.AbstractActionRelationship;
import org.eclipse.gmt.modisco.omg.kdm.action.ActionElement;
import org.eclipse.gmt.modisco.omg.kdm.action.ActionRelationship;
import org.eclipse.gmt.modisco.omg.kdm.code.AbstractCodeElement;
import org.eclipse.gmt.modisco.omg.kdm.code.CallableUnit;
import org.eclipse.gmt.modisco.omg.kdm.code.ClassUnit;
import org.eclipse.gmt.modisco.omg.kdm.code.CodeItem;
import org.eclipse.gmt.modisco.omg.kdm.code.CodeModel;
import org.eclipse.gmt.modisco.omg.kdm.code.InterfaceUnit;
import org.eclipse.gmt.modisco.omg.kdm.code.MethodUnit;
import org.eclipse.gmt.modisco.omg.kdm.code.Package;
import org.eclipse.gmt.modisco.omg.kdm.kdm.KdmPackage;
import org.eclipse.gmt.modisco.omg.kdm.kdm.Segment;

public class ReadingKDMFile {	

	public Segment load(String KDMModelFullPath) {

		KdmPackage.eINSTANCE.eClass();

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("fer", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();
		// Get the resource
		Resource resource = resSet.getResource(URI.createURI(KDMModelFullPath),
				true);

		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		System.out.println(resource.getContents().get(0).toString());

		System.out.println("O Contents � " + resource.getContents());

		return (Segment) resource.getContents().get(0);
	}

	public ArrayList<ClassUnit> getAllClasses(Segment segment) {

		ArrayList<ClassUnit> allClasses = new ArrayList<ClassUnit>();

		CodeModel codeModel = (CodeModel) segment.getModel().get(0);

		EList<AbstractCodeElement> elements = codeModel.getCodeElement();

		for (int i = 0; i < elements.size() - 1; i++) {

			System.out.println(elements.get(i));

			if (elements.get(i) instanceof Package) {

				Package packageKDM = (Package) elements.get(i);

				this.getClasses(packageKDM.getCodeElement(), allClasses);

			}

		}

		return allClasses;

	}

	private void getClasses(EList<AbstractCodeElement> elements,
			ArrayList<ClassUnit> allClasses) {

		for (AbstractCodeElement abstractCodeElement : elements) {

			if (abstractCodeElement instanceof ClassUnit) {

				allClasses.add((ClassUnit) abstractCodeElement);

			} else if (abstractCodeElement instanceof Package) {

				Package packageToPass = (Package) abstractCodeElement;

				getClasses(packageToPass.getCodeElement(), allClasses);

			}

		}

	}

	public List<MethodUnit> getMethods(ClassUnit classUnit) {

		EList<CodeItem> allElementsOfTheClass = classUnit.getCodeElement();

		List<MethodUnit> methodUnit = new ArrayList<MethodUnit>();

		for (CodeItem codeItem : allElementsOfTheClass) {

			if (codeItem instanceof MethodUnit) {

				MethodUnit methodUnitToPutIntoTheList = (MethodUnit) codeItem;

				methodUnit.add(methodUnitToPutIntoTheList);

			}

		}

		return methodUnit;

	}
	
	public List<ActionElement> getActionsElements(MethodUnit methodUnit) {

		EList<AbstractCodeElement> allElementsOfTheMethod = methodUnit.getCodeElement();			

		List<ActionElement> actionElements = new ArrayList<ActionElement>();

		for (AbstractCodeElement codeItem : allElementsOfTheMethod) {

			if (codeItem instanceof ActionElement && ((ActionElement) codeItem).getKind().endsWith("method invocation")) {

				ActionElement actionElementToPutIntoTheList = (ActionElement) codeItem;

				actionElements.add(actionElementToPutIntoTheList);

			}

		}

		return actionElements;

	}
	
	public List<ActionRelationship> getRelationships(ActionElement actionElement) {

		EList<AbstractActionRelationship> allElementsOfTheMethod = actionElement.getActionRelation();			

		List<ActionRelationship> actionRelationships = new ArrayList<ActionRelationship>();

		for (AbstractActionRelationship codeItem : allElementsOfTheMethod) {

			if (codeItem instanceof ActionRelationship) {

				ActionRelationship actionRelationshipToPutIntoTheList = (ActionRelationship) codeItem;

				actionRelationships.add(actionRelationshipToPutIntoTheList);

			}

		}

		return actionRelationships;

	}

	public List<MethodUnit> getMethods(InterfaceUnit interfaceUnit) {

		EList<CodeItem> allElementsOfTheClass = interfaceUnit.getCodeElement();

		List<MethodUnit> methodUnit = new ArrayList<MethodUnit>();

		for (CodeItem codeItem : allElementsOfTheClass) {

			if (codeItem instanceof MethodUnit) {

				MethodUnit methodUnitToPutIntoTheList = (MethodUnit) codeItem;

				methodUnit.add(methodUnitToPutIntoTheList);

			}

		}

		return methodUnit;

	}

	public List<CallableUnit> getCallableUnits(ClassUnit classUnit) {

		EList<CodeItem> allElementsOfTheClass = classUnit.getCodeElement();

		List<CallableUnit> callableUnits = new ArrayList<CallableUnit>();

		for (CodeItem codeItem : allElementsOfTheClass) {

			if (codeItem instanceof CallableUnit) {

				CallableUnit callableUnitToPutIntoTheList = (CallableUnit) codeItem;

				callableUnits.add(callableUnitToPutIntoTheList);

			}

		}

		return callableUnits;
	}

}
