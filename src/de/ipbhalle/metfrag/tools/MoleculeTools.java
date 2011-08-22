/*
*
* Copyright (C) 2009-2010 IPB Halle, Sebastian Wolf
*
* Contact: swolf@ipb-halle.de
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*
*/
package de.ipbhalle.metfrag.tools;

import static org.openscience.cdk.CDKConstants.ISAROMATIC;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import de.ipbhalle.metfrag.bondPrediction.AtomProperty;

public class MoleculeTools {
	
	/**
	 * Sets a unique identifier for every bond and atom
	 * in the molecule.
	 * 
	 * @param mol the mol
	 * 
	 * @return the i atom container
	 */
	public static IAtomContainer moleculeNumbering(IAtomContainer mol)
	{		
		IAtom[] atomList = AtomContainerManipulator.getAtomArray(mol);
		for (int i = 0; i < atomList.length; i++) {
			atomList[i].setID((i) + "");
		}	
		
		IBond[] bondList = AtomContainerManipulator.getBondArray(mol);
		for (int i = 0; i < bondList.length; i++) {
			bondList[i].setID((i) + "");
		}
		
		return mol;
	}
	

	/**
	 * Check if ring is aromatic.
	 *
	 * @param ring the ring
	 * @return true, if successful
	 */
	public static boolean ringIsAromatic(final IRing ring) {
		boolean isAromatic = true;
		for (IAtom atom : ring.atoms()) {
			if (!atom.getFlag(ISAROMATIC)) {
				isAromatic = false;
				break;
			}
		}
		if (!isAromatic) {
		  isAromatic = true;
			for (IBond b : ring.bonds()) {
				if (!b.getFlag(ISAROMATIC)) {
					return false;
				}
			}
		}
		return isAromatic;
	}
	
	/**
     * Gets the combined bond energy because the property stores every single energy in a list.
     * 
     * @param energyString the bond energy string
     * 
     * @return the combined bond energy
     */
    public static Double getCombinedEnergy(String energyString)
    {
    	double bondEnergy = 0.0;
    	
    	String[] arr1 = energyString.split(";");
    	for (int i = 0; i < arr1.length; i++) {
			String[] arr2 = arr1[i].split(",");
			for (int j = 0; j < arr2.length; j++) {
				bondEnergy += Double.parseDouble(arr2[j]);
			}
		}
    	
    	return bondEnergy;
    }

	
	/**
	 * Checks if the molecule has hydrogens already attached.
	 *
	 * @param mol the mol
	 * @return true, if successful
	 */
	public static boolean HydrogenAlreadyAdded(IAtomContainer mol)
	{
		boolean test = false;
		IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(mol);
		String formulaString = MolecularFormulaManipulator.getString(formula);
		
		if(formulaString.contains("H"))
		{
			test = true;
		}
		
		return test;
	}

}
