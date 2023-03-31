package main;

import ec.gp.GPData;

public class IntData extends GPData  {

	public int x;
	public int y;
	public void copyTo(final GPData gpd)
	{
		// Copy this x to the IntData passed in.
		((IntData)gpd).x = x;
		((IntData)gpd).y = y;
	}
	
}
