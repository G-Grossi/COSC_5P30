/**
 * 
 */
package jenaProject2;

/**
 * @author Gina Grossi
 *
 */
public class Main {

	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RuleTest testRules = new RuleTest();
		
		testRules.InitData();
		testRules.TestRules();
		RuleManager.WriteFoundRulesToFile();
	}

}
