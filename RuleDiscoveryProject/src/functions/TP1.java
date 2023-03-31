package functions;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPNode;
import ec.gp.GPIndividual;
import main.IntData;
import main.RuleDiscoveryProblem;


@SuppressWarnings("serial")
public class TP1 extends GPNode {

	public String toString() {return RuleDiscoveryProblem.targetPredicates[RuleDiscoveryProblem.TP_1];}
	public int expectedChildren() {return 0;}
	
	
	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	
	{
		int predicate;
		IntData rd = ((IntData)(input));
		
		
		String targePredString = RuleDiscoveryProblem.targetPredicates[RuleDiscoveryProblem.TP_1];
		
		//Look up target predict using the predicate map to get proper index
		predicate =  RuleDiscoveryProblem.targPredMap.get(targePredString);
		
		rd.x = predicate;
	}
	
}
