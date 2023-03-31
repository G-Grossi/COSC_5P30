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
public class BodyAtom extends GPNode{
	public String toString() {return "BODY ";}
	public int expectedChildren() {return 3;}
	
	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	
	{
		int subject;
		int predicate;
		int object;
	
		
		IntData rd = ((IntData)(input));
		
		// Subject 
		children[0].eval(state, thread, input, stack, individual, problem);
		subject = rd.x; // rd.x holds the result from children[0] eval
		
		// Predicate
		children[1].eval(state, thread, input, stack, individual, problem);
		predicate =  rd.x; // rd.x holds the result from children[1] eval
		
		// Object
		children[2].eval(state, thread, input, stack, individual, problem);
		object = rd.x; // rd.x holds the result from children[2] eval
		
	    ((RuleDiscoveryProblem)problem).ruleToEvaluate.AddAtomToFront(subject, predicate, object); 
	    
	   
	}

}
