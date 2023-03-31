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
public class SingletonCopyAtom extends GPNode{
	public String toString() {return "S_BODY ";}
	public int expectedChildren() {return 1;}
	
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
		subject = rd.x; // rd.x holds input subject from parent (head)
		object  = rd.y; // rd.y holds input object from parent (head)
		
		// Predicate
		children[0].eval(state, thread, input, stack, individual, problem);
		predicate =  rd.x; // rd.x holds the result from children[0] eval
	    
		((RuleDiscoveryProblem)problem).ruleToEvaluate.AddAtomToFront(subject, predicate, object); 
		
	}

}
