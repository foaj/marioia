/*
 * Copyright (c) 2009-2010, Sergey Karakovskiy and Julian Togelius
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Mario AI nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package ch.idsia.scenarios;

import ch.idsia.agents.Agent;
import ch.idsia.agents.AgentsPool;
import ch.idsia.agents.MLPESLearningAgent;
import ch.idsia.agents.learning.MediumMLPAgent;
import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.benchmark.tasks.BasicTask;
import ch.idsia.benchmark.tasks.LearningTask;
import ch.idsia.tools.MarioAIOptions;
import ch.idsia.utils.wox.serial.Easy;

/**
 * Created by IntelliJ IDEA. User: Sergey Karakovskiy, sergey at idsia dot ch
 * Date: Mar 17, 2010 Time: 8:28:00 AM Package: ch.idsia.scenarios
 */
public final class Main {
	public static void main(String[] args) {
		boolean setLearningAgent = true;
		
		MarioAIOptions marioAIOptions;
		Agent agent;
		BasicTask basicTask;
				
		if(!setLearningAgent){
			String argsString = "-vis on";
			marioAIOptions = new MarioAIOptions(argsString);
			agent = marioAIOptions.getAgent();
			basicTask = new BasicTask(marioAIOptions);
		}
		else{
			String argsString = "-vis off";
			marioAIOptions = new MarioAIOptions(argsString);
			agent = (Agent) Easy.load("evolved-progress-MLPESLearningAgent773-uid-2017-08-14_17-38-22.XML");//new MLPESLearningAgent();
			marioAIOptions.setAgent(agent);
			basicTask = new LearningTask(marioAIOptions);
			/*((MLPESLearningAgent)agent).setLearningTask((LearningTask)basicTask);
			((MLPESLearningAgent)agent).init();
			((MLPESLearningAgent)agent).learn();*/
			
		}
		
		for (int i = 0; i < 1; ++i) {
			int seed = 0;
			do {
				marioAIOptions.setLevelDifficulty(i);
				marioAIOptions.setLevelRandSeed(seed++);
				basicTask.setOptionsAndReset(marioAIOptions);
				basicTask.runSingleEpisode(1);
				basicTask.doEpisodes(1, true, 1);
				System.out.println(basicTask.getEnvironment()
						.getEvaluationInfoAsString());
			} while (basicTask.getEnvironment().getEvaluationInfo().marioStatus != Environment.MARIO_STATUS_WIN);
		}

		System.exit(0);
	}

}
