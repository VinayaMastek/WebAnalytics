package com.mastek.domain;

import java.util.HashMap;
import com.mastek.domain.DecisionStateEnum;

public class LogicalDecision {
		static HashMap<String, DecisionStateEnum> transition = new HashMap<String, DecisionStateEnum>();
		static HashMap<Integer, DecisionStateEnum> currentState = new HashMap<Integer, DecisionStateEnum>();
		
		public static void newTransaction(int tranid)
		{
			if (!currentState.containsKey(tranid))
				currentState.put(tranid, DecisionStateEnum.START);
		}
		
		public static void populateTransistion()
		{
			transition.put(DecisionStateEnum.START.name()+DecisionEventEnum.TIMESPENTONAMT.name(), DecisionStateEnum.UNCERTAIN);
			transition.put(DecisionStateEnum.START.name()+DecisionEventEnum.NAMECHANGEAFTEREMAIL.name(), DecisionStateEnum.WINDOWSHOPPER);
			transition.put(DecisionStateEnum.START.name()+DecisionEventEnum.TOOMANYNAMECHANGES.name(), DecisionStateEnum.PRANKSTER);
			transition.put(DecisionStateEnum.START.name()+DecisionEventEnum.PREMIUMDEVICE.name(), DecisionStateEnum.GOODCASE);
			
			transition.put(DecisionStateEnum.UNCERTAIN.name()+DecisionEventEnum.TOOMANYNAMECHANGES.name(), DecisionStateEnum.PRANKSTER);
			transition.put(DecisionStateEnum.UNCERTAIN.name()+DecisionEventEnum.COMMIT.name(), DecisionStateEnum.MANUAL);
			
			transition.put(DecisionStateEnum.WINDOWSHOPPER.name()+DecisionEventEnum.TOOMANYNAMECHANGES.name(), DecisionStateEnum.PRANKSTER);
			transition.put(DecisionStateEnum.WINDOWSHOPPER.name()+DecisionEventEnum.COMMIT.name(), DecisionStateEnum.REJECT);
			
			transition.put(DecisionStateEnum.PRANKSTER.name()+DecisionEventEnum.COMMIT.name(), DecisionStateEnum.REJECT);
			transition.put(DecisionStateEnum.PRANKSTER.name()+DecisionEventEnum.PASSPORTDTLPROVIDED.name(), DecisionStateEnum.UNCERTAIN);
			
			transition.put(DecisionStateEnum.GOODCASE.name()+DecisionEventEnum.TOOMANYNAMECHANGES.name(), DecisionStateEnum.PRANKSTER);
			transition.put(DecisionStateEnum.GOODCASE.name()+DecisionEventEnum.COMMIT.name(), DecisionStateEnum.ACCEPT);
		}
		
		public static DecisionStateEnum transit(DecisionEventEnum event, Integer tran)
		{
			DecisionStateEnum currentState = LogicalDecision.currentState.get(tran);
			DecisionStateEnum targetState = null;
			if (LogicalDecision.transition.containsKey(currentState.name()+event.name()))
			{
				targetState = transition.get(currentState.name()+event.name());
				LogicalDecision.currentState.put(tran,targetState);

			} else
				targetState =currentState;	
			return targetState;
		}

}
