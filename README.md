<p align="center"><img src="https://socialify.git.ci/G-Grossi/COSC_5P30/image?description=1&font=Jost&language=1&name=1&owner=1&pattern=Plus&theme=Light" alt="COSC_5P30" width="640" height="320" /></p>

## Rule Discovery with GP

Inspired by AIME[1], this system uses GP to mine Horn rules on the YAGO2 dataset. 

Examples of a rule that it has discovered are:

livesIn(x,y) <= livesIn(z,y) isMarriedTo(x,z)

isMarriedTo(x,y) <=  isMarriedTo(y,x)

The GP uses confidence scores such as PCA (Partial Completeness Assumption) confidence and Head Coverage from AMIE[1] to measure fitness (quality) of rules discovered.
The sum of the PCA confidence and HC is used to measure fitness similar to the EDMAR system[]. 
This repository contains the latest version of this project. 

<p align="center" ><img src="https://imgur.com/26rqKeJ.png" alt="project-screenshot" width="268" height="268/"> </p>


![Model](https://github.com/G-Grossi/COSC_5P30/blob/master/RuleDiscoveryProject/Images/GPMethod.PNG)

## Running this Project

The following is required to run this project: 
* Java >= 8
* ECJ-27 
* Apache Jena 4.7.0
* IDE for Java such as Eclipse 2022-12

## Links for Resources

[ECJ-27](https://cs.gmu.edu/~eclab/projects/ecj/)
[Apache Jena](https://jena.apache.org/download/index.cgi)
[Eclipse](https://www.eclipse.org/downloads/packages/release/2022-12/r/eclipse-ide-java-developers)
[YAGO2_from_AMIE](https://www.mpi-inf.mpg.de/departments/databases-and-information-systems/research/yago-naga/amie)
[AMIE](https://github.com/dig-team/amie)

## Important Notes
* RuleManager.java - need to change proper path variables in code for output files
* Main.java  	 - need set "pathToFiles" for ECJ output files

## References
[1]L. A. Galárraga, C. Teflioudi, K. Hose, F. Suchanek, Amie: Association rule mining under incomplete evidence in ontological knowledge bases, 
in: Proceedings of the 22nd International Conference on World Wide Web, WWW ’13, Association for Computing Machinery, 
New York, NY, USA, 2013, p. 413–422. URL: https://doi.org/10.1145/2488388 doi:10.1145/2488388.2488425.

## License
[MIT](https://choosealicense.com/licenses/mit/)
