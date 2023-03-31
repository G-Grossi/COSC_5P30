package main;
import ec.util.*;
import ec.*;
import ec.gp.*;

public class FloatData extends GPData 
{
	public float x;
	public void copyTo(final GPData gpd)
	{
		// Copy this x to the FloatData passed in.
		((FloatData)gpd).x = x;
	}
}
