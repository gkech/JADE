/**
 * 
 */
package host.behaviour;

import java.util.Vector;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;

/**
 * @author gkech
 *
 */
public class HostBehaviour extends SequentialBehaviour {
	private static final long serialVersionUID = 1L;

	public HostBehaviour(Agent a, Vector playersList, int numAgents) {
		super(a);

		// addSubBehaviour(new PingHost());
		addSubBehaviour(new SendPD(this.myAgent, playersList, numAgents));
		addSubBehaviour(new SendResults(this.myAgent, playersList, numAgents));

	}

}
