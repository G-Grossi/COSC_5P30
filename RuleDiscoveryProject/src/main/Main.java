package main;
import java.util.List;
import java.util.ArrayList;

import ec.Evolve;
import main.GPRule;

public class Main {

	public static void main(String[] args) {
	
		String pathToFiles = ".\\results\\";
		int numberOfJobs = 3;
		String[] runConfig = new String[] {
				Evolve.A_FILE, "src\\main\\ruleDiscovery.params", 
				"-p", ("stat.file=$"+pathToFiles+"out.stat"), 
				"-p", ("jobs="+numberOfJobs)
				};
		   Evolve.main(runConfig);
		   
	}

}


