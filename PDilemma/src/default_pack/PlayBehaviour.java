/**
 * 
 */
package default_pack;

import java.util.Random;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.core.Agent;

/**
 * @author gkech
 *
 */
public class PlayBehaviour extends SequentialBehaviour{
	public MessageTemplate mt; // The template to receive replies
    
    public PlayBehaviour(Agent a) {
        super(a);
        addSubBehaviour(new ReceiveGameSelectAction_Behaviour(this.myAgent));
        addSubBehaviour(new myAgentPrint_Behaviour(this.myAgent));
    }
    /**
     * SubBehaviour for game(Prisoner's dilemma) receiving and action sending  
     */
    public class ReceiveGameSelectAction_Behaviour extends Behaviour{
    	private boolean finish=false;
    	private String action=null;
        public ReceiveGameSelectAction_Behaviour(Agent a) {
            super(a);
        }
   
		public void action(){
			ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			if (msg != null){
				if (msg.getContent().equals("Prisoner's Dilemma")){
					//select action and send it 
					action=SelectAction();
					ACLMessage reply = msg.createReply();
					reply.setContent(action);
					myAgent.send(reply);
					finish=true; //when done with this message, go done()
				}
			}
			else{
				//if no message has arrived, block this behaviour (save resources)
				System.out.println( myAgent.getAID().getName()+" doesnt have any request message!" );
				block();
			}
		}
		public boolean done() {
			return finish;
		}
    }
    /**
     * 
     */
    public class myAgentPrint_Behaviour extends Behaviour{
    	private boolean finish=false;
    	
        public myAgentPrint_Behaviour(Agent a) {
            super(a);
          }
    	
		public void action() {
			//another notation for messagetemplate
			mt=MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), 
					MessageTemplate.MatchConversationId("PDilemma Results"));
			ACLMessage results = myAgent.receive(mt);
			if (results != null){
					System.out.println(myAgent.getName()+"'s"+" Final Payoff : " +results.getContent());
					finish=true;
			}
			else{
				//if no message has arrived, block this behaviour (save resources)
				System.out.println( myAgent.getAID().getName()+" doesnt have any inform message!" );
				block();
			}
		}
		public boolean done() {
			return finish;
		}
    }
    /**
     * 
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
