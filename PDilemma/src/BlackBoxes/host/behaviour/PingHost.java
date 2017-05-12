/**
 * 
 */
package host.behaviour;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

/**
 * @author gkech
 * Ping subBehaviour for debugging purposes
 *
 */
public class PingHost extends Behaviour {
	private static final long serialVersionUID = 1L;
	private boolean finish=false;
	
	public void action() {
		ACLMessage  msg = myAgent.receive();
        if(msg!=null){
            if(msg.getPerformative()== ACLMessage.REQUEST){
            	finish=true;
            }
        }
        else{
        	block();
        }
	}
	
	public boolean done() {
		return finish;
	}
}
