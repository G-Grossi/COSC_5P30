package functions;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import main.IntData;
import main.RuleDiscoveryProblem;

@SuppressWarnings("serial")
public class Y extends GPNode{

	public String toString() {return " Y ";}
	public int expectedChildren() {return 0;}
	
	
	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	
	{
		int result;
		IntData rd = ((IntData)(input));
		rd.x = ((RuleDiscoveryProblem)problem).Y;
		
	}

	
}
