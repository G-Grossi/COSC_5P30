package functions;
import main.FloatData;
import main.IntData;
import main.RuleDiscoveryProblem;

import java.util.Random;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

public class VarRand  extends GPNode{

	public String toString() {return " VarRand ";}
	public int expectedChildren() {return 2;}
	
	
	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	
	{
		int variable;
		int randValue;
		Random randomNum = new Random();
		
		IntData rd = ((IntData)(input));
		
		randValue = randomNum.nextInt(100);
		
		if(randValue < 50)
		{
			children[0].eval(state, thread, input, stack, individual, problem);
			variable =  rd.x; // rd.x holds the result from children[0] eval
		}
		else
		{
			children[1].eval(state, thread, input, stack, individual, problem);
			variable =  rd.x; // rd.x holds the result from children[1] eval
		}
		
		rd.x = variable;
		
	}
}
