/**
 * 
 */
package host.behaviour;

import java.util.Vector;

import pg.agents.GameOntology;
import pg.ontology.beans.*;
import jade.content.ContentManager;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import pg.agents.*;
/**
 * @author gkech
 *
 */
/**
 * Send Prisoner's Dilemma to #numAgents Agents in playersList
 *
 */
public class SendPD extends Behaviour{
	private static final long serialVersionUID = 1L;
	private boolean finish=false;
	private Vector playersList;
	private int numAgents;
	private ContentManager manager = myAgent.getContentManager();
	// This behaviour "speaks" the SL language
	private Codec codec = new SLCodec();
	// This behaviour "knows" the Music-Shop ontology
	private Ontology ontology;
	//end new declarations
	
    public SendPD(Agent a,Vector playersList,int numAgents) {
        super(a);
        this.playersList=playersList;
        this.numAgents=numAgents;
        
        try {
            ontology = GameOntology.getInstance();
        }
        catch (BeanOntologyException e) {
            e.printStackTrace();
        }
    } 
    
	public void action() {
		try{
			//inform agents about Prisoner's Dilemma
			ACLMessage msg = new ACLMessage( ACLMessage.REQUEST );
			for(int i = 0;  i < numAgents;  i++){
				msg.addReceiver( (AID) playersList.get(i));
			}
			msg.setConversationId("PDilemma");
			msg.setLanguage(codec.getName());
			msg.setOntology(ontology.getName());
			Game gm = new Game();
			gm.setGameName("Prisoner's Dilemma");
            //we use fillcontent() in order to transform java object into strings
            myAgent.getContentManager().fillContent(msg,gm);
            myAgent.send(msg);
			finish=true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean done() {
		return finish;
	}
}
