package functions;
import main.FloatData;
import main.IntData;
import main.RuleDiscoveryProblem;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

public class X  extends GPNode{

	public String toString() {return " X ";}
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
		rd.x = ((RuleDiscoveryProblem)problem).X;
		
	}
}
