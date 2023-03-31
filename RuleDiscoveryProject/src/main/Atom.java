package main;

import java.util.List;

/**
 * A class that defines a binary atom where each atom is a triple (s,p,o)
 * s = subject, p = predicate, o = object.
 * Triples are stored as indices to be set by the GP. Indices are 
 * used as lookups into separate arrays, one each for s,
 * p, and o  
 *
 */

public class Atom {
	
	/**
	 * These indices will be set by GP
	 * 
	 */
	int subIdx;
	int predIdx;
	int objIdx;
	
	public Atom(int s, int p, int o)
	{
		subIdx = s;
		predIdx = p;
		objIdx = o;
	}
	
	/** 
	 * Access to indices 
	 *
	 */
	
	public int GetSIdx() {return subIdx;}
	public int GetPIdx() {return predIdx;}
	public int GetOIdx() {return objIdx;}
	
	
	/** 
	 * Builds and returns a string representing the atom (to be used in query)
	 * Pass in lists for subject, predicate and object and use indices as lookups for lists.
	 * 
	 */
	public String AtomToString(String[] predicateList, String[] variableList)
	{
		//?a :isMarriedTo  ?b .
		String atomString;
		String sub = "?" + variableList[subIdx];
		String pred = ":" + predicateList[predIdx];
		String obj = "?" + variableList[objIdx];
	
		// Build list for query.
		atomString = sub + " " + pred + " " + obj;
		
		return atomString;
	}
	

}
