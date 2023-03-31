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

public class CHR0 extends GPNode{

	public String toString() {return "CHR0";}
	public int expectedChildren() {return 0;}
	
	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	
	{
	  //ROOT node that has no children (required by GP typed language)
		
	}
}
