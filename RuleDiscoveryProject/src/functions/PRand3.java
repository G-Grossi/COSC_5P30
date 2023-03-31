package functions;

import java.util.Random;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPNode;
import ec.gp.GPIndividual;
import main.IntData;
import main.RuleDiscoveryProblem;


@SuppressWarnings("serial")
public class PRand3 extends GPNode {

	public String toString() {
		if(predicate != -1)
		{
			return RuleDiscoveryProblem.predicates[predicate];
		}
		
		return "PRand3";}
	public int expectedChildren() {return 3;}
	int predicate = -1;
	
	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	
	{
		
		int randValue;
		Random randomNum = new Random();
		
		IntData rd = ((IntData)(input));
		
		randValue = randomNum.nextInt(100);
		
		if(randValue < 33)
		{
			children[0].eval(state, thread, input, stack, individual, problem);
			predicate =  rd.x; // rd.x holds the result from children[0] eval
		}
		else if(randValue < 66)
		{
			children[1].eval(state, thread, input, stack, individual, problem);
			predicate =  rd.x; // rd.x holds the result from children[1] eval
		}
		else
		{
			children[2].eval(state, thread, input, stack, individual, problem);
			predicate =  rd.x; // rd.x holds the result from children[2] eval
		}
		
		
		rd.x = predicate; // return chosen predicate
	}
	
}
