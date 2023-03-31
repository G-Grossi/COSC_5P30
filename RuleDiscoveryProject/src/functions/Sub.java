package functions;
import ec.*;
import ec.gp.*;
import ec.util.*;
import main.FloatData;

public class Sub extends GPNode {
	
	public String toString() {return "-";}
	public int expectedChildren() {return 2;}
	
	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	
	{
		float result;
		FloatData rd = ((FloatData)(input));

		children[0].eval(state, thread, input, stack, individual, problem);
		result = rd.x; // rd.x holds the result from children[0] eval
		
		children[1].eval(state, thread, input, stack, individual, problem);
		rd.x = result - rd.x; // rd.x holds the result from children[1] eval
		
	}

}
