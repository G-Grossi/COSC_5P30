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

public class CHR extends GPNode{

	public String toString() {return "CHR";}
	public int expectedChildren() {return 1;}
	
	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	
	{
		// Evaluate Tree - child should be a head atom
		children[0].eval(state, thread, input, stack, individual, problem);	
	}
}
