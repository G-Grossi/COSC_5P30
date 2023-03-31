package functions;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPNode;
import ec.gp.GPIndividual;
import main.FloatData;
import main.IntData;
import main.RuleDiscoveryProblem;
@SuppressWarnings("serial")
public class BodyAtomSet2 extends GPNode{
	public String toString() {return "BODYAtomSet2 ";}
	public int expectedChildren() {return 2;}
	
	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	
	{
		IntData rd = ((IntData)(input));
		
		// Calls evaluate on Body 1
		children[0].eval(state, thread, input, stack, individual, problem);
	
		// Calls evaluate on Body 2
		children[1].eval(state, thread, input, stack, individual, problem);
	   
	}

}
