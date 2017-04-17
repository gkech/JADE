/**
 * 
 */
package default_pack;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.Vector;

/**
 * @author gkech
 *
 */
public class HostBehaviour extends SequentialBehaviour{
	
    public HostBehaviour(Agent a,Vector playersList,int numAgents) {
        super(a);
        
        //addSubBehaviour(new PingHost());
        addSubBehaviour(new SendPD(this.myAgent,playersList,numAgents));
        addSubBehaviour(new SendResults(this.myAgent,playersList,numAgents));
    }
    
    /**
     * Send Prisoner's Dilemma to #numAgents Agents in playersList
     *
     */
    public class SendPD extends Behaviour{
    	private boolean finish=false;
		private Vector playersList;
		private int numAgents;

        public SendPD(Agent a,Vector playersList,int numAgents) {
            super(a);
            this.playersList=playersList;
            this.numAgents=numAgents;
        } 
		public void action() {
			//inform agents about Prisoner's Dilemma
			ACLMessage msg = new ACLMessage( ACLMessage.REQUEST );
			for(int i = 0;  i < numAgents;  i++){
				msg.addReceiver( (AID) playersList.get(i));
			}
            msg.setContent( "Prisoner's Dilemma" );
            msg.setConversationId("PDilemma");
            myAgent.send(msg);
			finish=true;
		}
		public boolean done() {
			return finish;
		}
    }
    
    /**
     * Send results based on each agent's action
     */
    public class SendResults extends Behaviour{
    	private boolean finish=false;
    	private String pg_action[]=new String[2];
    	private int pg_payoffs[]=new int[2];
    	private Vector playersList;
    	private int msgCounter=0;
    	private int numAgents;
    	
        public SendResults(Agent a,Vector playersList,int numAgents) {
            super(a);
            this.playersList=playersList;
            this.numAgents=numAgents;
        } 
    	
		public void action() {
			ACLMessage reply = myAgent.receive(MessageTemplate.MatchConversationId("PDilemma"));
    		if (reply != null){
    			pg_action[msgCounter]=reply.getContent();
    			msgCounter++;
    			System.out.println(msgCounter);
	    		if(msgCounter==numAgents){
					pg_payoffs=CalculatePayoffs(pg_action);
					ACLMessage payoffs = new ACLMessage(ACLMessage.INFORM );
					//fetch payoff vector to different agents
					for(int i = 0;  i < numAgents;  i++){
						payoffs.addReceiver( (AID)playersList.get(i));
						payoffs.setContent( Integer.toString(pg_payoffs[i]) );
						payoffs.setConversationId("PDilemma Results");
						myAgent.send(payoffs);
					}
	    			finish=true;
	    		}
    		}
    		else{
    			// if no message has arrived, block the behaviour
    			block();
    		}
		}

		public boolean done() {
			return finish;
		}
		/**
		 * CalculatePayoffs
		 */
		protected int[] CalculatePayoffs(String[] pg_actions){
			int pg_payoffs[]=new int[2];
			//game rules
			PD myPD=new PD();
			
			if(pg_actions[0].equals("COOPERATE") && pg_actions[1].equals("COOPERATE")){
				pg_payoffs[0]=myPD.Cooperation.elementAt(0);
				pg_payoffs[1]=myPD.Cooperation.elementAt(1);
			}
			else if(pg_actions[0].equals("DEFECT") && pg_actions[1].equals("DEFECT")){
				pg_payoffs[0]=myPD.Defect.elementAt(0);
				pg_payoffs[1]=myPD.Defect.elementAt(1);
			}
			else if(pg_actions[0].equals("COOPERATE") && pg_actions[1].equals("DEFECT")){
				pg_payoffs[0]=myPD.Co_Defect.elementAt(0);
				pg_payoffs[1]=myPD.Co_Defect.elementAt(1);
			}
			else{
				pg_payoffs[0]=myPD.Defect_Co.elementAt(0);
				pg_payoffs[1]=myPD.Defect_Co.elementAt(1);
			}
			return pg_payoffs;
		}
    }
    
    /**
     * Ping subBehaviour for debugging purposes
     *
     */
	private class PingHost extends Behaviour {
		private boolean finish=false;
		
		public void action() {
			ACLMessage  msg = myAgent.receive();
            if(msg!=null){
                if(msg.getPerformative()== ACLMessage.REQUEST){
                	finish=true;
                }
            }
            else{
            	block();
            }
		}
		public boolean done() {
			return finish;
		}
	}
}
