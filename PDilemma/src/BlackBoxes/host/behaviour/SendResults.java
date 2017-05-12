/**
 * 
 */
package host.behaviour;

import java.util.Vector;
import pg.ontology.beans.*;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author gkech
 *
 */
/**
 * Send results based on each agent's action
 */
public class SendResults extends Behaviour {
	private static final long serialVersionUID = 1L;
	private ContentManager manager = (ContentManager) myAgent.getContentManager();
	// This behaviour "speaks" the SL language
	private Codec codec = new SLCodec();
	// This behaviour "knows" the Music-Shop ontology
	private Ontology ontology;
	// end new declarations
	private boolean finish = false;
	private String pg_action[] = new String[2];
	private int pg_payoffs[] = new int[2];
	private Vector playersList;
	private int msgCounter = 0;
	private int numAgents;

	public SendResults(Agent a, Vector playersList, int numAgents) {
		super(a);
		this.playersList = playersList;
		this.numAgents = numAgents;
	}

	public void action() {
		ACLMessage reply = myAgent.receive(MessageTemplate.MatchConversationId("PDilemma"));
		if (reply != null) {
			try {
				ContentElement ce = manager.extractContent(reply);
				GameAction ga = (GameAction) ce;
				pg_action[msgCounter] = ga.getAgentAction();
				msgCounter++;

				if (msgCounter == numAgents) {
					pg_payoffs = CalculatePayoffs(pg_action);
					PersonalPayoff pof = new PersonalPayoff();
					ACLMessage payoffs = new ACLMessage(ACLMessage.INFORM);
					payoffs.setConversationId("PDilemma Results");
					payoffs.setOntology(reply.getOntology());
					payoffs.setLanguage(reply.getLanguage());
					// fetch payoff vector to different agents
					for (int i = 0; i < numAgents; i++) {
						payoffs.addReceiver((AID) playersList.get(i));
						pof.setPpayoff(pg_payoffs[i]);
						manager.fillContent(payoffs, pof);
						myAgent.send(payoffs);
						payoffs.removeReceiver((AID) playersList.get(i));
					}
					finish = true;
				}
			} catch (CodecException | OntologyException e) {
				e.printStackTrace();
			}

		} else {
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
	protected int[] CalculatePayoffs(String[] pg_actions) {
		int pg_payoffs[] = new int[2];
		// game rules
		PayOffs myPD = new PayOffs();

		if (pg_actions[0].equals("COOPERATE") && pg_actions[1].equals("COOPERATE")) {
			pg_payoffs[0] = myPD.Cooperation.elementAt(0);
			pg_payoffs[1] = myPD.Cooperation.elementAt(1);
		} else if (pg_actions[0].equals("DEFECT") && pg_actions[1].equals("DEFECT")) {
			pg_payoffs[0] = myPD.Defect.elementAt(0);
			pg_payoffs[1] = myPD.Defect.elementAt(1);
		} else if (pg_actions[0].equals("COOPERATE") && pg_actions[1].equals("DEFECT")) {
			pg_payoffs[0] = myPD.Co_Defect.elementAt(0);
			pg_payoffs[1] = myPD.Co_Defect.elementAt(1);
		} else {
			pg_payoffs[0] = myPD.Defect_Co.elementAt(0);
			pg_payoffs[1] = myPD.Defect_Co.elementAt(1);
		}
		return pg_payoffs;
	}
}