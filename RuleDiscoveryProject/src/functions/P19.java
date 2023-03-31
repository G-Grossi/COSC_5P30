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
public class P19 extends GPNode {

	public String toString() {return RuleDiscoveryProblem.predicates[RuleDiscoveryProblem.P_19];}
	public int expectedChildren() {return 0;}
	
	
	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	
	{
		
		IntData rd = ((IntData)(input));
		rd.x = RuleDiscoveryProblem.P_19;
	}
	
}
