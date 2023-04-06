<p align="center"><img src="https://socialify.git.ci/G-Grossi/COSC_5P30/image?description=1&font=Jost&language=1&name=1&owner=1&pattern=Plus&theme=Light" alt="COSC_5P30" width="640" height="320" /></p>

## Rule Discovery with GP

Inspired by the AMIE[1], EDMAR[2], and EVODA[3] systems to mine rules in Knowledge Graphs, this project uses a genetic program (GP) to mine Horn like rules on the YAGO2 dataset. This project investigates if the length of a rule affects the GP's ability to mine rules efficiently (measured in average time of a training run) and to mine rules of quality (measured using the fitness defined in the EDMAR system, see below).

An example of a rule of length 3 mined in this project: 

livesIn(x,y) <= livesIn(z,y) isMarriedTo(x,z)

An example of a rule of length 2 mined in this project: 

isMarriedTo(x,y) <=  isMarriedTo(y,x)

The GP uses confidence scores such as PCA (Partial Completeness Assumption) confidence and Head Coverage(HC) from AMIE[1].
In this project, the sum (normalized) of the PCA confidence and HC is used to measure fitness (rule quality) similar to the EDMAR[2] system. 

## GP Tree Node Structure

Influenced by the EVODA[3] system, this project forms a GP individual using a tree node structure.  For example the rule: livesIn(x,y) <= livesIn(z,y) isMarriedTo(x,z) is represented as a GP tree node structure where each node is an atom containing a subject, predicate, and object: 

<p align="center" ><img src="https://github.com/G-Grossi/COSC_5P30/blob/master/RuleDiscoveryProject/Images/treeExample.PNG" alt="project-screenshot" width="349" height="287/"> </p>


## GP Rule Mining Method
The following image shows the steps involved in using a GP to mine rules.

<p align="center" ><img src="https://github.com/G-Grossi/COSC_5P30/blob/master/RuleDiscoveryProject/Images/GPMethod.PNG" alt="project-screenshot" width="1080" height="600/"> </p>


## Results Showing Rule Mining Efficiency in Training
3 experiments are performed in this project.  The first experiment searches for rules of length 2, the second experiment searches for rules of length 2&3 and the third experiment searches for rules of length 3. The execution time for each training run is tracked and the mean, min, and max averages of all runs for each experiment are shown in the chart below.  It is seen that the efficiency of the GP to mine rules decreases as rule length increases. 
<p align="center" ><img src="https://github.com/G-Grossi/COSC_5P30/blob/master/RuleDiscoveryProject/Images/LearningTimes.PNG" alt="project-screenshot" width="840" height="627/"> </p>

## Preliminary Results in Testing 
Due to time constraints, only some of the rules discovered are manually chosen for testing. It is seen in the chart below that most rules performed consistently in training and testing. There were some rules of both length 2 and length 3 that did not perform consistently in testing (not all rules tested are listed in chart below). This shows that rule length does not have an effect on rule quality. Instead it is seen that the components of the fitness measure (either HC or PCA) have an effect on rule quality. However, not enough trained rules are tested and more thorough testing needs to be completed to verify these preliminary results.


<p align="center"><img src="https://github.com/G-Grossi/COSC_5P30/blob/master/RuleDiscoveryProject/Images/GeneralDiscovered.PNG" alt="COSC_5P30" width="690" height="205" /></p>


## Code Project Description

* RuleDiscoveryProject: used to perform rule mining (using the same YAGO2 training data set that is used in the AMIE[1] system).

* RuleTestProject: used to manually perform testing of rules discovered in testing (using the same YAGO2s testing data set that is used in the AMIE[1] system). 

## Important Source Code Description
Main Package:
* Main.java: loads the GP parameter files and kicks off the GP Evolve system using ECJ.
* RuleManager.java: provides processing functions for rules (handles I/O, creating/reading predicate histogram, creating/writing rule cache, and rule pruning).
* RuleDiscoveryProblem.java: central code for this system. Loads dataset for training, defines the target predicate and predicate lists for rule mining, and overrides the evaluation function (see evaluate() function) for the ECJ system. This function performs a query on the discovered rule (GP tree) to calculate the rule's fitness.

Function Package:
* Contains GP function sets and terminal sets used to form GP Individuals (GP Tree Node Structures)

## Requirements for Running the Code in this Project

The following are required to run the code in this project (see links to resources below): 
* Java >= 8
* ECJ-27 
* Apache Jena 4.7.0
* IDE for Java such as Eclipse 2022-12

## Steps for Running the Code in this Project (Training)
* Install all required libraries
* Download code into an Eclipse workspace direction
* Review the *.param files to set GP parameters (such as max generations, population size, function sets and terminal sets etc...) See ECJ manual (found at ECJ link) for more details. 
* Open the RuleDiscoveryProject workspace in Eclipse for training.
* Set the target predicates in the RuleDiscoveryProblem java class.
* Set the number of jobs (runs) in the main.java file. 
* Run the main.java file to start training. 
* Once training is complete review the job.#.outtabular.stat files to see the fitness values for each generation in each run (found in RuleDiscoveryProject folder). Review the job.#.out.stat files to see examples of GP trees produced in the run (found in results folder). Review the evaluate#.txt files to see the rules found in the run (found in the RawOutput folder).

## Steps for Running the Code in this Project (Testing)
* Once training is complete, open the RuleTestProject workspace in Eclipse for testing. 
* In the RuleTest java class TestRules function, manually enter some of the rules found in training to test (see example code in this file).
* Run the main.java file to start testing. 
* Once testing is complete the test results can be found in the TestRules folder within the workspace of the project. 


## Links for Resources

[ECJ-27](https://cs.gmu.edu/~eclab/projects/ecj/)
[Apache Jena](https://jena.apache.org/download/index.cgi)
[Eclipse](https://www.eclipse.org/downloads/packages/release/2022-12/r/eclipse-ide-java-developers)
[YAGO2_from_AMIE](https://www.mpi-inf.mpg.de/departments/databases-and-information-systems/research/yago-naga/amie)
[AMIE](https://github.com/dig-team/amie)



## References
[1] L. A. Galárraga, C. Teflioudi, K. Hose, F. Suchanek, Amie: Association rule mining under incomplete evidence in ontological knowledge bases, 
in: Proceedings of the 22nd International Conference on World Wide Web, WWW ’13, Association for Computing Machinery, 
New York, NY, USA, 2013, p. 413–422. URL: https://doi.org/10.1145/2488388 doi:10.1145/2488388.2488425.

[2] L. Wu, E. Sallinger, E. Sherkhonov, S. Vahdati, G. Gottlob, Rule learning over knowledge
graphs with genetic logic programming, in: 2022 IEEE 38th International Conference on
Data Engineering (ICDE), 2022, pp. 3373–3385. doi:10.1109/ICDE53745.2022.00318.

[3] M. D. Tran, C. d’Amato, B. T. Nguyen, A. G. B. Tettamanzi, An evolutionary algorithm
for discovering multi-relational association rules in the semantic web, in: Proceedings
of the Genetic and Evolutionary Computation Conference, GECCO ’17, Association for
Computing Machinery, New York, NY, USA, 2017, p. 513–520. URL: https://doi.org/10.1145/
3071178.3079196. doi:10.1145/3071178.3079196.



## License
[MIT](https://choosealicense.com/licenses/mit/)
