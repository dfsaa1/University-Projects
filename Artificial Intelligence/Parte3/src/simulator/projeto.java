package simulator;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class projeto {

	public static void main(String[] args) throws Exception {
		String filename = "projeto.fcl";
		FIS fis = FIS.load(filename, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);

		Simulator s = new Simulator();
		DataSource datasource = new DataSource("mushroom.arff");
		Instances train = datasource.getDataSet();
		train.setClassIndex(train.numAttributes()-1);

		J48 j48 = new J48();
		j48.buildClassifier(train);

		//JFuzzyChart.get().chart(fb);

		// Print ruleSet
		//System.out.println(fb);
		//System.out.println("Angle: " + fb.getVariable("angulo").getValue());
		
		while(true){

			NewInstances i = new NewInstances(train);
			if(s.getMushroomAttributes() != null){
				if(s.getDistanceC() <= 1){
					i.addInstance(s.getMushroomAttributes());
					Instances instances = i.getDataset();
					Instance cogumelo = instances.instance(0);
					double d = j48.classifyInstance(cogumelo);
					fb.setVariable("toxicity",d);
					fb.evaluate();
					if(fb.getVariable("action").defuzzify() == 0){
						s.setAction(Action.NO_ACTION);
					}else if(fb.getVariable("action").defuzzify() == -1){
						s.setAction(Action.DESTROY);
					}else if(fb.getVariable("action").defuzzify() == 1){
						s.setAction(Action.PICK_UP);
					}
				}
			}else
				s.setAction(Action.NO_ACTION);

			// Set inputs
			fb.setVariable("right", s.getDistanceR());
			fb.setVariable("left", s.getDistanceL());
			fb.setVariable("center", s.getDistanceC());
			
			
			double speed = fb.getVariable("velocity").defuzzify();
			s.setRobotSpeed((int)speed);
			System.out.println(s.getRobotSpeed());
			
			// Evaluate
			fb.evaluate();

			double angle = fb.getVariable("angulo").defuzzify();
			s.setRobotAngle(angle);
			s.step();
				
		}
		
	}

}
