/***************************************
 JADE - Java Agent DEvelopment Framework
 Georgios Kechagias, Prisoner's Dilemma 
 pet educational project
 ***************************************/
package default_pack;

import jade.core.Agent;

import java.text.NumberFormat;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.Behaviour;

import java.util.*;

public class Player1 extends Agent{
    public final static String ACTIONS[]= {"COOPERATE","DEFECT"};
    Random rand = new Random();
    public int selection;
    PD myPD = new PD();
    private MessageTemplate mt; // The template to receive replies
    
    protected NumberFormat m_avgFormat = NumberFormat.getInstance();
	
	protected void setup() {
		try {
	        //play the game
	        addBehaviour(new dilemma_action());
	        //
	        //addBehaviour(new ReceiveResults());
	        
		}
        catch (Exception e) {
            System.out.println( "SetUp Exception " + e );
            e.printStackTrace();
        }
	}
	/*
	 * Custom Behaviour for action selection
	 */
	private class dilemma_action extends CyclicBehaviour{
		private String action=null;
		
		public void action() {
			
			ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			if (msg != null) {
				if (msg.getContent().equals("Prisoner's Dilemma")){
					//select action and send it 
					action=SelectAction();
					ACLMessage reply = msg.createReply();
					reply.setContent(action);
					send(reply);
					System.out.println( "Agent_player :messsage came!!!"+ msg.getContent() );
					System.out.println("Prisoner's Dilemma? : "+msg.getContent().equals("Prisoner's Dilemma") );
				}
			}
			else{
				// if no message is arrived, block the behaviour
				System.out.println( getAID().getName()+" blocked" );
				block();
			}
		}
	}//end of dilemma action behaviour
	
	private class ReceiveResults extends CyclicBehaviour{
		public void action() {
			mt=MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage results = receive(mt);
			if (results != null){
					System.out.println("Final Payoffs : " +results.getContent());
			}
			else{
				
				block();
			}
		}
	}
	/*
	 * Each Agent choose an action : COOPERATE and DEFECT
	 * Future Improvements: Private Information could differentiate action selection
	 */
	private String SelectAction(){
		//random action selection...for now
		selection=rand.nextInt(ACTIONS.length);
		return ACTIONS[selection];
	}
	
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("**********************************************");
		System.out.println("Buyer-agent "+getAID().getName()+" terminating.");
		System.out.println("**********************************************");
	}
}// end of player1
