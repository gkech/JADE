/**
 * 
 */
package player.behaviour;

import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;

/**
 * @author gkech
 *
 */
public class PlayBehaviour extends SequentialBehaviour{
	private static final long serialVersionUID = 1L;
    public PlayBehaviour(Agent a) {
        super(a);

        addSubBehaviour(new ReceiveGameSelectAction_Behaviour(this.myAgent));
        addSubBehaviour(new myAgentPrint_Behaviour(this.myAgent));
    }

}
