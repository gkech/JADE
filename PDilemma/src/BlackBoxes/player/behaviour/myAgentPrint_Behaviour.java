/**
 * 
 */
package player.behaviour;

import pg.ontology.beans.PersonalPayoff;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author uheq
 *
 */
public class myAgentPrint_Behaviour extends Behaviour{
	private static final long serialVersionUID = 1L;
	private boolean finish=false;
	public MessageTemplate mt; // The template to receive replies
	private ContentManager manager = (ContentManager) myAgent.getContentManager();
	
    public myAgentPrint_Behaviour(Agent a) {
        super(a);
      }
	
	public void action() {
		//another notation for messagetemplate
		mt=MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), 
				MessageTemplate.MatchConversationId("PDilemma Results"));
		ACLMessage results = myAgent.receive(mt);
		if (results != null){
				try {
					//transform results string into Game Object with extractContent
					ContentElement ce = manager.extractContent(results);
					PersonalPayoff pof = (PersonalPayoff) ce;
					System.out.println(myAgent.getName()+"'s"+" Final Payoff : " +pof.getPpayoff());
					finish=true;
				} catch (CodecException | OntologyException e) {
					e.printStackTrace();
				}
		}
		else{
			//if no message has arrived, block this behaviour (and save resources)
			System.out.println( myAgent.getAID().getName()+" doesnt have any inform message to consume!" );
			block();
		}
	}
	
	public boolean done() {
		//loops until finish=true
		return finish;
	}
}
