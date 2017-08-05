package ch.idsia.benchmark.tasks;

import ch.idsia.agents.Agent;
import ch.idsia.tools.MarioAIOptions;

public class PredictionStepTask extends BasicTask{

	private static final int DEFAULT_STEPS_TO_PREDICTION = 3;
	
	private int stepsToPredction;
	private int currentStep;
	
	public PredictionStepTask(MarioAIOptions marioAIOptions) {
		this(marioAIOptions, DEFAULT_STEPS_TO_PREDICTION);
	}
	
	public PredictionStepTask(MarioAIOptions marioAIOptions, int stepsToPredction) {
		super(marioAIOptions);
		this.stepsToPredction = stepsToPredction;
		currentStep = 0;
	}

	@Override
	public int evaluate(Agent agent) {
		this.runSingleEpisode(1);
	    return this.getEvaluationInfo().computeWeightedFitness();
	}

	/**
	 * @param repetitionsOfSingleEpisode
	 * @return boolean flag whether controller is disqualified or not
	 */
	public boolean runSingleEpisode(final int repetitionsOfSingleEpisode)
	{
		currentStep++;
		//boolean[] action = agent.getAction();
		//environment.performAction(action);
		
//	    long c = System.currentTimeMillis();
//	    for (int r = 0; r < repetitionsOfSingleEpisode; ++r)
//	    {
//	        this.reset();
//	        while (!environment.isLevelFinished())
//	        {
//	            environment.tick();
//	            if (!GlobalOptions.isGameplayStopped)
//	            {
//	                c = System.currentTimeMillis();
//	                agent.integrateObservation(environment);
//	                agent.giveIntermediateReward(environment.getIntermediateReward());
//
//	                boolean[] action = agent.getAction();
//	                if (System.currentTimeMillis() - c > COMPUTATION_TIME_BOUND)
//	                    return false;
////	                System.out.println("action = " + Arrays.toString(action));
////	            environment.setRecording(GlobalOptions.isRecording);
//	                environment.performAction(action);
//	            }
//	        }
//	        environment.closeRecorder(); //recorder initialized in environment.reset
//	        environment.getEvaluationInfo().setTaskName(name);
//	        this.evaluationInfo = environment.getEvaluationInfo().clone();
//	    }

	    return true;
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return currentStep < stepsToPredction;
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}
}
