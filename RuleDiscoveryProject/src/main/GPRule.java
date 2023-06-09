package main;
import java.util.ArrayList;
import java.util.List;

import ec.EvolutionState;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * A class that represents rules as a list of binary atoms
 * Each atom is a triple (s,p,o). This first atom in the list
 * is the head atom, all remaining atoms are body atoms of the rule.  
 *
 */

public class GPRule {


   /**
    * Rule containing list of binary atoms  (first item in head atom)
    */
   List<Atom> rule;	
   
   
   /**
    * Stores a unique set of variables used in the rule 
    */
   List<Integer> varList;

   /** Support of the Rule 
    *  (e.g: (?x :p ?y) => (?x :p2 ?y) )
    */
   int support;
   
   /** PCA Support of the Rule 
    *  (e.g: (?x :p ?y) => (?x :p2 ?y1) )  
    */
   int PCASupport;
   
   /** Confidence for the rule (Fitness measure for GP)
    *  
    */
   float PCAConfidence;
   
   /** PCA Ratio for the rule 
    *  (support/PCASupport)
    */
   float PCARatio;
   
   /** Support of the head only
    *  
    */
   int headSupport;
   
   /** headCoverage Ratio for the head/rule 
    *  (headSupport/support)
    */
   float headCoverage;
   
   /** standardFitness for the rule (1 - PCA) 
    *  (headSupport/support)
    */
   float standardFitness;
   
   
   String headStr;      	// used to build rule for support query
   String bodyStr;
   String inverseBodyStr;
   String headStrPCA;
   String bodyStrPCA;
   String headSupportOnlyStr; // used to build head query only for head support query
   
   
   /**
    * Prefix string for start of a query.
    */
   String queryPrefixSelect =  "PREFIX pf: <http://jena.hpl.hp.com/ARQ/property#>" + 
			    "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			    "PREFIX :<http://yago-knowledge.org/resource/>" +
			    "PREFIX dbp: <http://dbpedia.org/ontology/>" +
			    "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
			    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
			    "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>" +
			    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
			    "SELECT DISTINCT ";
   
   
   /**
    * Init GPRule
    */
   public GPRule() {
	 rule = new ArrayList<Atom>();
	 varList = new ArrayList<Integer>();
   }

   /**
    * Adds atom to front of rule
    */
   public void AddAtomToFront(int s, int p, int o)
   {
	   Atom atom = new Atom(s, p, o);
	   rule.add(0,atom);
	   // When new atoms are added to the list, add variables to varlist (only once)
	   if(!varList.contains(s))
	   {
		   varList.add(s);
	   }
	   if(!varList.contains(o))
	   {
		   varList.add(o);
	   }
		   
   }
   
   /** PCA Confidence
    * 
    * @return PCA Confidence
    */
   public float GetPCAConfidence()
   {
	   return PCAConfidence;
   }
   
   /** GetSupport
    * 
    * @return support of rule
    */
   public int GetSupport()
   {
	   return support;
   }
   
   /** GetPCASupport
    * 
    * @return PCAsupport of rule
    */
   public int GetPCASupport()
   {
	   return PCASupport;
   }
   
   /** GetHeadSupport
    * 
    * @return head support of rule
    */
   public int GetHeadSupport()
   {
	   return headSupport;
   }
   
   
   /** GetHeadCoverage
    * 
    * @return head coverage of rule
    */
   public float GetHeadCoverage()
   {
	   return headCoverage;
   }
   
   
   public float GetStandardFitness()
   {
	   return standardFitness;
   }
   
   
     
   /** GetAtomAtIndex
    * Returns atom at given index
    */
   public Atom GetAtomAtIndex(int index)
   {
	   return rule.get(index);
   }
   
   /** GetBodySize
    * Returns body size (assumes at least one atom (head) is in the rule list).
    */
   public int GetBodySize()
   {
	   int bodySize = rule.size() - 1;
	   return bodySize;
   }
   
   /** GetSize
    * Returns number of atoms in the rule
    */
   public int GetSize()
   {
	   return rule.size();
   }
   
   /** GetHeadStr
    * 
    * @return
    */
   public String GetHeadStr()
   {
	   return headStr;
   }
   
   /** GetHeadSupportOnlyStr
    * 
    * @return
    */
   public String GetHeadSupportOnlyStr()
   {
	   return headSupportOnlyStr;
   }
   
   /** GetBodyStr
    * 
    * @return
    */
   public String GetBodyStr()
   {
	   return bodyStr;
   }
   
   /** GetInvBodyStr
    * 
    * @return
    */
   public String GetInvBodyStr()
   {
	   return inverseBodyStr;
   }
  
   /** GetHeadStrPCA
    * 
    * @return
    */
   public String GetHeadStrPCA()
   {
	   return headStrPCA;
   }
   
   /** GetBodyStrPCA
    * 
    * @return
    */
   public String GetBodyStrPCA()
   {
	   return bodyStrPCA;
   }
   
   /** SetSupport
    *  
    * @param sup
    */
   public void SetSupport(int sup)
   {
	   support = sup;
   }
   
   
   /** SetHeadSupport
    *  
    * @param sup
    */
   public void SetHeadSupport(int sup)
   {
	   headSupport = sup;
   }
   
   
   /** Set PCASupport
    * 
    * @param sup
    */
   public void SetPCASupport(int sup)
   {
	   PCASupport = sup;
   }
   
   /** CalculatePCA()
    * 
    */
   public void CalculatePCA()
   {
	   PCAConfidence = 0.0f;
	   headCoverage = 0.0f;
	   //if(support+PCASupport > 0)
	   //{
	   		//PCAConfidence = (float) support/ (float)(support+PCASupport);
	   //}
	   if(PCASupport > 0)
	   { 
		   PCAConfidence = (float) support/ (float)(PCASupport); // matches results from AMIE https://resources.mpi-inf.mpg.de/yago-naga/amie/data/yago2/amie_yago2_2.html
		   if (PCAConfidence > 1.0f)
			   PCAConfidence = 1.0f;	  
	   }
	   
	   if(PCASupport > 0)
	   {
		   PCARatio = (float) support/ (float)(PCASupport);
	   }
		   
	   if(headSupport > 0)
	   {
		  headCoverage = (float)support/ (float)headSupport;
		  if(headCoverage > 1.0)
			  headCoverage = 1.0f;
	   }
	   standardFitness = 1.0f - (PCAConfidence + headCoverage)/2.0f;
   }
   
   /** ReportPCA() to Console
    * 
    */
   public void ReportPCA()
   {
	   System.out.println("Support Rule:  " + GetHeadStr() + GetBodyStr());
	   System.out.println("PCA Support Rule:  " + GetHeadStrPCA() + GetBodyStrPCA());
	   System.out.println("Head Coverage Rule:  " + GetHeadSupportOnlyStr());
	   
	   System.out.println("support: " + support + " PCASupport: " + PCASupport + " PCA: " + PCAConfidence + " PCARatio: " + PCARatio + " StFit: " + standardFitness);
	   System.out.println("Head support: " + headSupport + " Support: "+ support + " Head Coverage (support/headSupport): " + headCoverage);
	   
   }

   /** ReportPCA() to File
    * 
    */
   public void ReportPCAToFile(String fileName)
   {
	   String pathToFiles = "C:\\Users\\Gina Grossi\\eclipse-workspace\\GP\\RuleDiscoveryProject\\RuleDiscoveryProject\\RawOutput\\" + fileName;
	   
	   PrintWriter out;
	   try {
		   out = new PrintWriter(pathToFiles);
		   out.println("Support Rule:  " + GetHeadStr() + GetBodyStr());
		   out.println("PCA Support Rule:  " + GetHeadStrPCA() + GetBodyStrPCA());
		   out.println("Head Coverage Rule:  " + GetHeadSupportOnlyStr());
		   
		   out.println("support: " + support + " PCASupport: " + PCASupport + " PCA: " + PCAConfidence + " PCARatio: " + PCARatio+ " StFit: " + standardFitness);
		   out.println("Head support: " + headSupport + " Support: "+ support + " Head Coverage (support/headSupport): " + headCoverage);
		   // Close the file
		   out.close();  
	   } catch (FileNotFoundException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   } 
	
   }
   
   /** ReportPCA() using out param
    * 
    */
   public void ReportPCAToWriter(PrintWriter out)
   {
	   out.println("**********************************************************************************");
	   out.println(" ");
	   out.println("Support Rule:  " + GetHeadStr() + GetBodyStr());
	   out.println("PCA Support Rule:  " + GetHeadStrPCA() + GetBodyStrPCA());
	   out.println("Head Coverage Rule:  " + GetHeadSupportOnlyStr());
	   out.println("support: " + support + " PCASupport: " + PCASupport + " PCA: " + PCAConfidence + " PCARatio: " + PCARatio+ " StFit: " + standardFitness);
	   out.println("Head support: " + headSupport + " Support: "+ support + " Head Coverage (support/headSupport): " + headCoverage);
	   out.println(" ");
	   out.println("**********************************************************************************");
   }
   
   /**
    * Builds query string to test support of the rule.
    */
   public String BuildRuleSupportQueryString(String[] headPredicates, String[] predicates, String[] variables)
   {
	   if(rule.size() == 0 ) return " ";
	   
	   String query;
	   Atom head;				  
	   String selectVar =" ";				  // Contains variables for SELECT
	   
	   String headSub;
	   String headPred;
	   String headObj; 
	   String headNegObj;						
	   int subIdx;
	   int predIdx;
	   int objIdx;
	
	   // Add prefix to query
	   query = queryPrefixSelect;
	   
	   
	   // Get head
	   head = rule.get(0);
	   
	   // Look up indices
	   subIdx  = head.GetSIdx();
	   predIdx = head.GetPIdx();
	   objIdx  = head.GetOIdx();;
	   
	   // Find each s,p,o using atom indices
	   headSub = "?" + variables[subIdx];
	   headPred = ":" + headPredicates[predIdx];
	   headObj = "?" + variables[objIdx];
	   
	   
       // Get Variables for select  	
	   for(int i= 0; i < varList.size(); i++)
	   {
		   selectVar += "?" + variables[varList.get(i)] + " ";
	   }
	   
	   selectVar +=  "WHERE { ";
	   
	   // Add variables and where clause to query
	   query += selectVar;

	   headStr = head.AtomToString(headPredicates, variables) + " . "; 
	   query += headStr;
	   
	   bodyStr = " ";
	   // Add Body or Bodies to query
	   for(int i = 1; i < rule.size(); i++ )
	   {
		   Atom body = rule.get(i);
		   bodyStr += body.AtomToString(predicates, variables) + " . "; 
	   }
	   
	   inverseBodyStr = " ";
	   // Add body atoms in reverse order to cache
	   for(int i = rule.size()-1; i >=1 ; i-- )
	   {
		   Atom body = rule.get(i);
		   inverseBodyStr += body.AtomToString(predicates, variables) + " . "; 
	   }
	   
	   query += bodyStr;
		
	   // Add closing bracket
	   query += "}";
	   
	 // Example of Query string
	 //  "SELECT DISTINCT ?a ?b  WHERE { " +
	 //  " {?a :isMarriedTo  ?b .} " +     
	 //   " {?b :isMarriedTo  ?a .} " +  
	 //   "}";	
	   
	   return query;
   }
   
   /**
    * GetPredicateString
    * @param atomIndex
    * @param predicates
    * @param targetPredicates
    * @return
    */
   public String GetPredicateString(int atomIndex, String[] predicates, String[] targetPredicates)
   {
	   String predicateStr ="";
	   
	   Atom atom = rule.get(atomIndex);
	   int predIndex = atom.GetPIdx();
	   
	   if(atomIndex == 0) // head atom
	   {
		   predicateStr = targetPredicates[predIndex];
	   }
	   else
	   {
		   predicateStr = predicates[predIndex];
	   }
	   return predicateStr;
   }
   
   /**
    * 
    * @param headPredicates
    * @param variables
    * @return
    */
   public String BuildHeadQueryString(String[] headPredicates, String[] variables)
   {
	   if(rule.size() == 0 ) return " ";
	   
	   String query;
	   Atom head;				  
	   String selectVar =" "; // Contains variables for SELECT
	   
	   String headSub;
	   String headPred;
	   String headObj; 
	   String headNegObj;						
	   int subIdx;
	   int predIdx;
	   int objIdx;
   
	   // Add prefix to query
	   query = queryPrefixSelect;
	   
	   
	   // Get head
	   head = rule.get(0);
	   
	   // Look up indices
	   subIdx  = head.GetSIdx();
	   predIdx = head.GetPIdx();
	   objIdx  = head.GetOIdx();;
	   
	   // Find each s,p,o using atom indices
	   headSub = "?" + variables[subIdx];
	   headPred = ":" + headPredicates[predIdx];
	   headObj = "?" + variables[objIdx];
	   
    
	   selectVar =  headSub +  " " + headObj + " ";
	   
	   selectVar +=  "WHERE { ";
	   
	   // Add variables and where clause to query
	   query += selectVar;
	
	   headSupportOnlyStr = head.AtomToString(headPredicates, variables) + " . "; 
	   query += headSupportOnlyStr;
	    
	   
	   // Add closing bracket
	   query += "}";
	    
	   return query;
   }
   
 
   /**
    * Builds query string to test negative support of the rule.
    */
   public String BuildRuleNegativeQueryString(String[] headPredicates, String[] predicates, String[] variables)
   {
	   if(rule.size() == 0 ) return " ";
	   
	   String query;
	   Atom head;				  
	   String selectVar =" "; // Contains variables for SELECT
	
	   // Used to store head variables and predicates for negation
	   String headSub;
	   String headPred;
	   String headObj; 
	   String headNegObj;						
	   int subIdx;
	   int predIdx;
	   int objIdx;
	   
	   // Add prefix to query
	   query = queryPrefixSelect;
	   
	   // Get head atom
	   head = rule.get(0);
	   
	   // Look up indices
	   subIdx  = head.GetSIdx();
	   predIdx = head.GetPIdx();
	   objIdx  = head.GetOIdx();;
	   
	   
	   // Find each s,p,o using atom indices
	   headSub = "?" + variables[subIdx];
	   headPred = ":" + headPredicates[predIdx];
	   headObj = "?" + variables[objIdx];
	   
	   // Create a negation variable for PCA
	   headNegObj = headObj +"_1";
	   
       // Get Variables for select  	
	   for(int i= 0; i < varList.size(); i++)
	   {
		   
		   selectVar += "?" + variables[varList.get(i)] + " ";
	   }
	   
	   selectVar +=  "WHERE { ";
	   
	   // Add variables and where clause to query
	   query += selectVar;
	  
	   // Build head string for query
	   headStrPCA = headSub + " " + headPred + " " + headNegObj + ".";
	   // Add head to query
	   query += headStrPCA;
	   
	   bodyStrPCA = " ";
	   // Add Body or Bodies to query
	   for(int i = 1; i < rule.size(); i++ )
	   {
		   Atom body = rule.get(i);
		   bodyStrPCA += body.AtomToString (predicates, variables)+ " . ";	   
	   }
	   
	   query += bodyStrPCA;
	   
	   
	   // Add closing bracket
	   query += "}";
	   
	 //?b <isMarriedTo> ?a => ?a <isMarriedTo> ?b	    
	 //"SELECT DISTINCT ?b1    WHERE { " +
	 //" {?a :isMarriedTo  ?b1 .} " +     
	 //" {?b :isMarriedTo  ?a .} " +  
	 //" FILTER (?b1 !=  ?b) " +  //DONT USE FILTER to match AMIE https://resources.mpi-inf.mpg.de/yago-naga/amie/data/yago2/amie_yago2_2.html
	 //"}";
	   return query;
   }
   
   /**
    * Clones Rule
    * @return copy of rule
    */
   public GPRule CloneRule ()
   {
	   GPRule cloneRule  = new GPRule();
	   
	   cloneRule.headStr = headStr;
	   cloneRule.bodyStr = bodyStr;
	   cloneRule.headStrPCA= headStrPCA;
	   cloneRule.bodyStrPCA = bodyStrPCA;
	   cloneRule.headSupportOnlyStr = headSupportOnlyStr;
	   
	   cloneRule.support = support;
	   cloneRule.PCASupport = PCASupport;
	   cloneRule.PCAConfidence = PCAConfidence;
	   cloneRule.PCARatio = PCARatio;
	   cloneRule.headSupport = headSupport;
	   cloneRule.headCoverage = headCoverage;
	   cloneRule.standardFitness = standardFitness;
	   
	   // need rule to be in proper order 
	   for(int i =  rule.size()-1; i >=0; i-- )
	   {
		   // get last and add it to the front so head is first in the list after copy
		   Atom ruleAtom = rule.get(i);
		   cloneRule.AddAtomToFront(ruleAtom.GetSIdx(), ruleAtom.GetPIdx(), ruleAtom.GetOIdx());	   
	   }
	 
	   for(int i = 0; i < varList.size(); i++)
	   {
		   cloneRule.varList.add(varList.get(i));
	   }
	   return cloneRule;
   }
   
   /** 
    * Resets Rule
    */
   public void RuleReset()
   {
	   headStr= "";
	   bodyStr = "";
	   headStrPCA= "";
	   bodyStrPCA = "";
	   rule.clear();
	   varList.clear();
	   support = 0;
	   PCASupport = 0;
	   PCAConfidence = 0.0f;
	   PCARatio = 0.0f;
	   standardFitness = 0.0f;
   }
}
