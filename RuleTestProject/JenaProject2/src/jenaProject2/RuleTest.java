package jenaProject2;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
/**
 * A class that tests the existence of rule within a loaded knowledge graph.  
 *
 */
public class RuleTest {
public static final int QUERY_THRESHOLD = 500000;
	
	

	/** Variable indices for GP
	 * 
	 */
	public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
	
	/** Possible predicates in rules
	 * 
	 */
	public static final String[] predicates = {
			"diedIn",
			"livesIn",
			"isLocatedIn",
			"isMarriedTo",
			"isPoliticianOf",
			"hasOfficialLanguage",
			"created", 
			"directed",
			"wasBornIn",
			"isCitizenOf",
			"hasChild",    
			"influences",
			"actedIn",
			"hasCapital",
			"hasAcademicAdvisor",
			"graduatedFrom",
			"playsFor",
			"worksAt",
			"produced",
			"isLeaderOf",
			"isKnownFor",
			 "hasCurrency",
			"dealsWith",
		    "isInterestedIn",
		    "exports",
		    "imports",
			"participatedIn",
			"holdsPoliticalPosition",
			"isAffiliatedTo",
			};

	
	 /** Possible target predicates in rules
	 * 
	 */
	// Target Predicates found in AMIE https://resources.mpi-inf.mpg.de/yago-naga/amie/data/yago2/amie_yago2_2.html
	public static  String[] targetPredicates = {
			 "isPoliticianOf",
			 "isAffiliatedTo", 
			 "livesIn",
			 "created",
			 "directed",
			 "isCitizenOf",
			 "isMarriedTo",
			 "diedIn",
			 "hasOfficialLanguage"
			};
	
	/** Model to how data set
	 * 
	 */
	Model model = ModelFactory.createDefaultModel();
	
	
	/** Map for predicate name, index into predicates array
	 * 
	 */
	public static Map <String, Integer> predMap = new HashMap<String,Integer>(); 
	public static Map <String, Integer> targPredMap = new HashMap<String,Integer>(); 
	
	/** Possible variables in rules
	 * 
	 */
	public static String[] variables = {"x","y","z"};
	
	public void InitData()
	{
		
		for(int predIndex = 0;  predIndex < predicates.length; predIndex++)
		{
			predMap.put(predicates[predIndex], predIndex );
		}
		
		for(int predIndex = 0;  predIndex < targetPredicates.length; predIndex++)
		{
			targPredMap.put(targetPredicates[predIndex], predIndex );
		}
		
		// Read in data set
		 model.read("yagoFacts.decoded.ttl"); // testing 
		//model.read("yago2core_facts.ttl"); // training
	
	}
	
	/** Test Rules
	 *  Used to test discovered rules system with queries.
	 */
	public  void TestRules()
	{
	
		/** Create rules variables and query string variables
		 * 
		 */
		GPRule ruleGP  = new GPRule();
		
		String supportQueryString;
		String negativeQueryString;
		String headQueryString;
	
		int support = 0;
		int headSupport = 0;
		int PCASupport = 0;
		
		RuleManager.RunTimeMeasuresStart();
		
		//*******************************************************************************************************
		//Discovered Rule ?x :diedIn ?y .  ?x :livesIn ?y . 
		ruleGP.AddAtomToFront(X, predMap.get("livesIn"), Y);
		ruleGP.AddAtomToFront(X, targPredMap.get("diedIn"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		//*******************************************************************************************************
		//Discovered Rule ?x :isAffiliatedTo ?y .  ?x :playsFor ?y .   (not in training set BUT discovered rule that was pruned)
		ruleGP.AddAtomToFront(X, predMap.get("playsFor"), Y);
		ruleGP.AddAtomToFront(X, targPredMap.get("isAffiliatedTo"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		
		
		//*******************************************************************************************************
		//Discovered Rule ?x :livesIn ?y .  ?x :isCitizenOf ?y .  
		ruleGP.AddAtomToFront(X, predMap.get("isCitizenOf"), Y);
		ruleGP.AddAtomToFront(X, targPredMap.get("livesIn"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		//*******************************************************************************************************
		//Discovered Rule ?x :created ?y .  ?x :isKnownFor ?y . 
		ruleGP.AddAtomToFront(X, predMap.get("isKnownFor"), Y);
		ruleGP.AddAtomToFront(X, targPredMap.get("created"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		
		//*******************************************************************************************************
		//Discovered Rule  ?x :isMarriedTo ?y .  ?y :isMarriedTo ?x . 
		ruleGP.AddAtomToFront(Y, predMap.get("isMarriedTo"), X);
		ruleGP.AddAtomToFront(X, targPredMap.get("isMarriedTo"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		//*******************************************************************************************************
		//Discovered Rule  ?x :isPoliticianOf ?y .  ?x :diedIn ?z . ?x :livesIn ?z . 
		ruleGP.AddAtomToFront(X, predMap.get("livesIn"), Z);
		ruleGP.AddAtomToFront(X, predMap.get("diedIn"), Z);
		ruleGP.AddAtomToFront(X, targPredMap.get("isPoliticianOf"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();

		//*******************************************************************************************************
		//Discovered Rule  ?x :isCitizenOf ?y .  ?z :isLocatedIn ?y . ?x :isLocatedIn ?z .  
		ruleGP.AddAtomToFront(X, predMap.get("isLocatedIn"), Z);
		ruleGP.AddAtomToFront(Z, predMap.get("isLocatedIn"), Y);
		ruleGP.AddAtomToFront(X, targPredMap.get("isCitizenOf"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		//*******************************************************************************************************
		//Discovered Rule  ?x :isPoliticianOf ?y .  ?x :diedIn ?z . ?z :isLocatedIn ?y . 
		ruleGP.AddAtomToFront(Z, predMap.get("isLocatedIn"), Y);
		ruleGP.AddAtomToFront(X, predMap.get("diedIn"), Z);
		ruleGP.AddAtomToFront(X, targPredMap.get("isPoliticianOf"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		
		
		//*******************************************************************************************************
		//Discovered Rule  ?x :livesIn ?y .  ?z :livesIn ?y . ?x :isMarriedTo ?z .   
		ruleGP.AddAtomToFront(X, predMap.get("isMarriedTo"), Z);
		ruleGP.AddAtomToFront(Z, predMap.get("livesIn"), Y);
		ruleGP.AddAtomToFront(X, targPredMap.get("livesIn"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		
		//*******************************************************************************************************
		//Discovered Rule  ?x :livesIn ?y .  ?x :isLeaderOf ?y . ?x :wasBornIn ?y . 
		ruleGP.AddAtomToFront(X, predMap.get("wasBornIn"), Y);
		ruleGP.AddAtomToFront(X, predMap.get("isLeaderOf"), Y);
		ruleGP.AddAtomToFront(X, targPredMap.get("livesIn"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		//*******************************************************************************************************
		//Discovered Rule  ?x :isPoliticianOf ?y .  ?x :isLeaderOf ?y . ?y :participatedIn ?z . 
		ruleGP.AddAtomToFront(Y, predMap.get("participatedIn"), Z);
		ruleGP.AddAtomToFront(X, predMap.get("isLeaderOf"), Y);
		ruleGP.AddAtomToFront(X, targPredMap.get("isPoliticianOf"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		

		//*******************************************************************************************************
		//Discovered Rule  ?x :isPoliticianOf ?y .  ?x :wasBornIn ?z . ?x :diedIn ?z . 
		ruleGP.AddAtomToFront(X, predMap.get("diedIn"), Z);
		ruleGP.AddAtomToFront(X, predMap.get("wasBornIn"), Z);
		ruleGP.AddAtomToFront(X, targPredMap.get("isPoliticianOf"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		//*******************************************************************************************************
		//Discovered Rule  ?x :livesIn ?y .  ?x :wasBornIn ?z .. 
		ruleGP.AddAtomToFront(X, predMap.get("wasBornIn"), Z);
		ruleGP.AddAtomToFront(X, targPredMap.get("livesIn"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		//*******************************************************************************************************
		//Discovered Rule  ?x :directed ?y .  ?x :created ?y .. 
		ruleGP.AddAtomToFront(X, predMap.get("created"), Y);
		ruleGP.AddAtomToFront(X, targPredMap.get("directed"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		//*******************************************************************************************************
		//Discovered Rule   ?x :hasOfficialLanguage ?y .  ?z :isLocatedIn ?x . ?z :hasOfficialLanguage ?y . 
		ruleGP.AddAtomToFront(Z, predMap.get("hasOfficialLanguage"), Y);
		ruleGP.AddAtomToFront(Z, predMap.get("isLocatedIn"), X);
		ruleGP.AddAtomToFront(X, targPredMap.get("hasOfficialLanguage"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		//*******************************************************************************************************
		//Discovered Rule   ?x :hasOfficialLanguage ?y .  ?z :livesIn ?x . ?z :isCitizenOf ?x . 
		ruleGP.AddAtomToFront(Z, predMap.get("isCitizenOf"), X);
		ruleGP.AddAtomToFront(Z, predMap.get("livesIn"), X);
		ruleGP.AddAtomToFront(X, targPredMap.get("hasOfficialLanguage"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
		
		
		//*******************************************************************************************************
		//Discovered Rule  ?x :livesIn ?y .  ?z :livesIn ?y . ?x :hasChild ?z .
		ruleGP.AddAtomToFront(X, predMap.get("hasChild"), Z);
		ruleGP.AddAtomToFront(Z, predMap.get("livesIn"), Y);
		ruleGP.AddAtomToFront(X, targPredMap.get("livesIn"), Y);
		
		//RULE TEST *********************************************************************************************
		supportQueryString = ruleGP.BuildRuleSupportQueryString(targetPredicates,predicates,variables);
		negativeQueryString = ruleGP.BuildRuleNegativeQueryString(targetPredicates,predicates, variables);
		headQueryString = ruleGP.BuildHeadQueryString(targetPredicates, variables);
		
		System.out.println(supportQueryString);
		System.out.println(negativeQueryString);
		System.out.println(headQueryString);
		
		support = QueryRule(supportQueryString,false);
		PCASupport = QueryRule(negativeQueryString,false);
		headSupport =  QueryRule(headQueryString,false);
		
		ruleGP.SetSupport(support);
		ruleGP.SetHeadSupport(headSupport);
		ruleGP.SetPCASupport(PCASupport);
		ruleGP.CalculatePCA();
		ruleGP.ReportPCA();
		
		RuleManager.AddRuleToFoundRules(ruleGP.CloneRule());
		ruleGP.RuleReset();
 
		RuleManager.RunTimeMeasuresStop();
	
	}
	/** Performs a query on the string passed in and returns the count of the query
	 * 
	 * @param queryString
	 * @param printQuery
	 * @return
	 */
	public int QueryRule(String queryString, boolean printQueryResults)
	{
		int count = 0;
		Query query = QueryFactory.create(queryString) ;
		try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
			ResultSet supportResults = qexec.execSelect() ;
		
		    for ( ; supportResults.hasNext() ; )
		    {
		      QuerySolution soln = supportResults.nextSolution() ;
		      if(printQueryResults)
		    	  System.out.println(soln);
		      count = count+ 1;
		      if(count > QUERY_THRESHOLD)
		      {
		    	  System.out.println("COUNT " + queryString + " "+ count);
		    	  break; // AVOID OUT OF MEMORY
		      }
		      
		    }
		    qexec.close();	
		}
		return count;
	}
}
