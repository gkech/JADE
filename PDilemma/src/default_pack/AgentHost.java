package default_pack;

import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;

import java.lang.Object; 

public class AgentHost extends Agent{
	
	 protected Vector playersList = new Vector();    // invitees
	 protected int numAgents=2;
	 public final static String ACTIONS[]= {"COOPERATE","DEFECT"};
	 public String pg_action[]=new String[2];
	 int pg_payoffs[]=new int[2];
	 private MessageTemplate mt; // The template to receive replies
	 
	protected void setup() {
		try{
            // create the agent descrption of itself
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName( getAID() );
            DFService.register( this, dfd );
            //summon @numAgents agents
            SummonAgents(numAgents);
            //PingTriggered calls HostLogic
            addBehaviour(new PingTriggered());
		}
		catch (Exception e) {
			System.out.println( "Saw exception in setup function: " + e );
			e.printStackTrace();
	    }
	}
	/*
	 * PingTriggered behaviour in order to view sniffers UI information
	 */
	private class PingTriggered extends Behaviour {
		@Override
		public void action() {
			ACLMessage  msg = receive();
            if(msg!=null){
                if(msg.getPerformative()== ACLMessage.REQUEST){
    				String content = msg.getContent();
    				if ((content != null) && (content.indexOf("ping") != -1)){
    					addBehaviour(new HostLogic());
    				}
                }
            }
            else{
            	block();
            }
		}
		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return false;
		}
	}
	
	private class PingTriggered1 extends Behaviour {
		@Override
		public void action() {
			
			addBehaviour(new HostLogic());

		}
		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return true;
		}
	}
	/*
	 * Host Response Logic
	 */
	private class HostLogic extends Behaviour {
		
		private int step = 0;
		private int msgCounter=0;
		
		public void action() {
			switch (step){
			case 0:
    			//inform agents about Prisoner's Dilemma
    			ACLMessage msg = new ACLMessage( ACLMessage.INFORM );
				for(int i = 0;  i < numAgents;  i++){
					msg.addReceiver( (AID)playersList.get(i));
				}
	            msg.setContent( "Prisoner's Dilemma" );
	            msg.setConversationId("PDilemma");
	            send(msg);
				step=1;
				break;
			case 1:
				ACLMessage reply = receive(MessageTemplate.MatchConversationId("PDilemma"));
	    		if (reply != null){
	    			if(reply.getContent().equals(ACTIONS[0]) || reply.getContent().equals(ACTIONS[1])){
	    				System.out.println("Reply received from :"+reply.getSender().getName()+" and the action is "+reply.getContent());
	        		}
	    			pg_action[msgCounter]=reply.getContent();
	    			msgCounter++;
		    		if(msgCounter==numAgents){
		    			step=2;
		    		}
	    		}
	    		else{
	    			// if no message is arrived, block the behaviour
	    			block();
	    		}
	    		break;
			case 2:
				pg_payoffs=CalculatePayoffs(pg_action);
				ACLMessage payoffs = new ACLMessage(ACLMessage.INFORM );
				for(int i = 0;  i < numAgents;  i++){
					payoffs.addReceiver( (AID)playersList.get(i));
					payoffs.setContent( Integer.toString(pg_payoffs[i]) );
				}
				payoffs.setConversationId("PDilemma");
				send(payoffs);
				mt= MessageTemplate.and(MessageTemplate.MatchConversationId("PDilemma"), 
		                	MessageTemplate.MatchInReplyTo(payoffs.getReplyWith()));
				step=3;
				//print results
	            System.out.println("Payoff for"+playersList.get(0)+" is "+pg_payoffs[0]);
	            System.out.println("Payoff for"+playersList.get(1)+" is "+pg_payoffs[1]);
				//System.out.println("Payoff for Player1 "+pg_payoffs[1]);
				break;
			}
		}
		public boolean done() {
			return step==3;
		}
	}
	/*
	 * Summon Agents func
	 */
	protected void SummonAgents(int numAgents){
		
		PlatformController container = getContainerController(); // get a container controller for creating new agents
        // create N player-agents
        try {
            for (int i = 0;  i < numAgents;  i++) {
                // create a new agent
            	String localName = "PgAgent"+i;
            	AgentController summon = container.createNewAgent(localName, "default_pack.Player1", null);
            	summon.start();
            	// keep the guest's ID on a local list
            	playersList.add( new AID(localName, AID.ISLOCALNAME) );
            }
        }
        catch (Exception e) {
            System.err.println( "Exception while adding guests: " + e );
            e.printStackTrace();
        }
	}
	/*
	 * CalculatePayoffs
	 */
	protected static int[] CalculatePayoffs(String[] pg_actions){
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
