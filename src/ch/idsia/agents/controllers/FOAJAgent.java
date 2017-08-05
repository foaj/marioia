package ch.idsia.agents.controllers;

import java.util.Random;

import ch.idsia.agents.Agent;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.benchmark.tasks.PredictionStepTask;
import ch.idsia.evolution.Evolvable;
import ch.idsia.evolution.ea.ES;

public class FOAJAgent extends BasicMarioAIAgent implements Agent, Evolvable{

	//Constants
	private static final String NAME = "foaj_agent";
	private static final int ES_POPULATION_SIZE = 4;
	private static final int STEPS_BEFORE_CHOOSE_ACTION = 3;
	private static final float PROBABILITY_TO_KEEP_IN_THE_SAME_MOVEMENT = 0.5f;
		
	//Local Variables
	private PredictionStepTask predictionStepTask;
	private float predictionFitness;
	
	public FOAJAgent(PredictionStepTask predictionStepTask) {
		super(NAME);
		zLevelEnemies = 2;
		zLevelScene = 2;
		reset();
		this.predictionStepTask = predictionStepTask;
	}

	@Override
	public boolean[] getAction() {
		chooseBestAction(); 
		return action;
	}
	
	private boolean[] chooseBestAction(){
		ES evalutionStrategiesAlgorithm = new ES(predictionStepTask, this, ES_POPULATION_SIZE);
		
		for (int i = 0; i < STEPS_BEFORE_CHOOSE_ACTION; i++) {
			evalutionStrategiesAlgorithm.nextGeneration();
		}
		
		FOAJAgent nextState = ((FOAJAgent)evalutionStrategiesAlgorithm.getBests()[0]); 
		
		predictionFitness = evalutionStrategiesAlgorithm.getBestFitnesses()[0];
		
		printAction();
		printPredictionFitness();
		
		return nextState.getAction();
	}
	
	@Override
	public void integrateObservation(Environment environment) {
		super.integrateObservation(environment);
		//printEnvironment(environment);
	}
	
	@Override
	public void setObservationDetails(int rfWidth, int rfHeight, int egoRow,
			int egoCol) {
		super.setObservationDetails(rfWidth, rfHeight, egoRow, egoCol);
		//printObservationDetails(rfWidth, rfHeight, egoRow, egoCol);
	}
	
	private void printEnvironment(Environment environment){
		if(environment != null){
			System.out.print("isMarioAbleToJump "+environment.isMarioAbleToJump());
			System.out.print("isMarioAbleToShoot "+environment.isMarioAbleToShoot());
			System.out.print("isMarioCarrying "+environment.isMarioCarrying());
			System.out.print("isMarioOnGround "+environment.isMarioOnGround());
		}
	}
	
	private void printObservationDetails(int rfWidth, int rfHeight, int egoRow,
			int egoCol){
		System.out.print("receptiveFieldWidth "+rfWidth
				+"\nreceptiveFieldHeight "+rfHeight
				+"\nmarioEgoRow "+egoRow
				+"\nmarioEgoCol "+egoCol);
		
		printLevelSceneObservation();
		printEnemiesObservation();
	}
	
	private void printLevelSceneObservation(){
		System.out.print("");
	}
	
	private void printEnemiesObservation(){
		System.out.print("");
	}
	
	private void printAction(){
		System.out.print("KEY_LEFT "+action[Mario.KEY_LEFT]
				+"\nKEY_RIGHT "+action[Mario.KEY_RIGHT]
				+"\nKEY_DOWN "+action[Mario.KEY_DOWN]
				+"\nKEY_JUMP "+action[Mario.KEY_JUMP]
				+"\nKEY_SPEED "+action[Mario.KEY_SPEED]
				+"\nKEY_UP "+action[Mario.KEY_UP]);
	}
	
	private void printPredictionFitness(){
		System.out.println("CurrentFitness "+predictionFitness);
	}
	
	@Override
	public Evolvable getNewInstance() {
		FOAJAgent newInstance = (FOAJAgent) copy();
		Random randomizer = new Random();
		boolean change_movement = randomizer.nextFloat() >= PROBABILITY_TO_KEEP_IN_THE_SAME_MOVEMENT;
		if(change_movement){
			newInstance.mutate();
		}
		
		return copy();
	}

	@Override
	public Evolvable copy() {
		FOAJAgent copy = new FOAJAgent(predictionStepTask);
	
		copy.action = this.action.clone();
		
		copy.setObservationDetails(receptiveFieldWidth, receptiveFieldHeight, marioEgoRow, marioEgoCol);

		copy.levelScene = this.levelScene.clone();
		copy.enemies = this.enemies;
		copy.mergedObservation = this.mergedObservation;

		copy.marioFloatPos = this.marioFloatPos.clone();
		copy.enemiesFloatPos = this.enemiesFloatPos.clone();
		copy.marioState = this.marioState.clone();

		copy.marioStatus = marioState[0];
		copy.marioMode = marioState[1];
		copy.isMarioOnGround = marioState[2] == 1;
		copy.isMarioAbleToJump = marioState[3] == 1;
		copy.isMarioAbleToShoot = marioState[4] == 1;
		copy.isMarioCarrying = marioState[5] == 1;
		copy.getKillsTotal = marioState[6];
		copy.getKillsByFire = marioState[7];
		copy.getKillsByStomp = marioState[8];
		copy.getKillsByShell = marioState[9];
		
		return copy;
	}

	@Override
	public void mutate() {
		Random randomizer = new Random();
		for (int i = 0; i < action.length; i++) {
			action[i] = randomizer.nextBoolean();
		}
	}
	
	@Override
	public void reset() {
	}
}
