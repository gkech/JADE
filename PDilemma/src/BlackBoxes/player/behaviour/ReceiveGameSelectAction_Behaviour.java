/**
 * 
 */
package player.behaviour;

import java.util.Random;

import pg.ontology.beans.*;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * 
 * SubBehaviour for game(Prisoner's dilemma) receiving and action sending
 * @author gkech
 *
 */
public class ReceiveGameSelectAction_Behaviour extends Behaviour{
	private static final long serialVersionUID = 1L;
	private boolean finish=false;
	private String action=null;
	
    public ReceiveGameSelectAction_Behaviour(Agent a) {
        super(a);
    }

	public void action(){
		ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if (msg != null){
			try {
				//transform msg string into Game Object with extractContent
				ContentElement ce = myAgent.getContentManager().extractContent(msg);
				Game g = (Game) ce;
				if (g.getGameName().equals("Prisoner's Dilemma")){
					//select action...
					action=SelectAction();
					System.out.println("Agent : "+myAgent.getName()+" plays "+action);
					
					GameAction ga = new GameAction();
					ga.setAgentAction(action);
					ACLMessage reply = msg.createReply();
					myAgent.getContentManager().fillContent(reply, ga);
					//...send ontology based reply 
					myAgent.send(reply);
					finish=true; //when done with this message, go done()
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			//if no message has arrived, block this behaviour (and save resources)
			System.out.println( myAgent.getAID().getName()+" doesnt have any request message!" );
			block();
		}
	}
	public boolean done() {	//here done
		return finish;
	}
	
    /**
     * Select an action method
     */
	private String SelectAction(){
		String ACTIONS[]= {"COOPERATE","DEFECT"};
		Random rand = new Random();
		int selection;
		
		//random action selection...for now...
		selection=rand.nextInt(ACTIONS.length);
		return ACTIONS[selection];
	}
	
}