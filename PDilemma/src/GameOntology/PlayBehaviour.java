/**
 * 
 */
package GameOntology;

import java.util.Random;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.core.Agent;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;

/**
 * The Basic Player Behaviour for Prisoner's Dilemma Players.
 * This Behaviour(SequentialBehaviour) is a set of other subBehaviours:
 * 		-ReceiveGameSelectAction_Behaviour
 * 		-myAgentPrint_Behaviour
 * Each of them could be completely autonomous
 * 
 * Action Selection is random at the moment, but it can further improve.
 * 
 * @author gkech
 *
 */
public class PlayBehaviour extends SequentialBehaviour{
	public MessageTemplate mt; // The template to receive replies
	
	/**
	 * Ontology Declarations
	 */
	private ContentManager manager = (ContentManager) myAgent.getContentManager();
	// This behaviour "speaks" the SL language
	private Codec codec = new SLCodec();
	// This behaviour "knows" the Music-Shop ontology
	private Ontology ontology;
	//end declarations
	
    public PlayBehaviour(Agent a) {
        super(a);
       //incase we dont have a correct GameOntology, we catch
		try {
			ontology = GameOntology.getInstance();
		} catch (BeanOntologyException e) {
			e.printStackTrace();
		}
		
		manager.registerLanguage(codec);
		manager.registerOntology(ontology);
        
        addSubBehaviour(new ReceiveGameSelectAction_Behaviour(this.myAgent));
        addSubBehaviour(new myAgentPrint_Behaviour(this.myAgent));
    }
    /**
     * SubBehaviour for game(Prisoner's dilemma) receiving and action sending  
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
					ContentElement ce = manager.extractContent(msg);
					Game g = (Game) ce;
					if (g.getGameName().equals("Prisoner's Dilemma")){
						//select action...
						action=SelectAction();
						System.out.println("Agent : "+myAgent.getName()+" plays "+action);
						
						GameAction ga = new GameAction();
						ga.setAgentAction(action);
						ACLMessage reply = msg.createReply();
						manager.fillContent(reply, ga);
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
    }
    /**
     * Each Agent prints his own payoffs
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
				System.out.println( myAgent.getAID().getName()+" doesnt have any inform message!" );
				block();
			}
		}
		public boolean done() {
			return finish;
		}
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
