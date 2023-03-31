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
public class Predicate extends GPNode {

	public String toString() {
		if(predicate != -1)
		{
			return RuleDiscoveryProblem.predicates[predicate];
		}
		
		return "Predicate";}
	
	public int expectedChildren() {return 0;}
	
	
	int predicate = -1;
	
	public Predicate()
	{
		int randValue;
		Random randomNum = new Random();
		randValue = randomNum.nextInt(RuleDiscoveryProblem.predicates.length);
		predicate = randValue;
	}
	
	
	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	
	{
		
		IntData rd = ((IntData)(input));
		rd.x = predicate; // return random predicate
	}
	
}
