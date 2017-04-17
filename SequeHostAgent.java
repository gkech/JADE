/**
 * 
 */
package default_pack;

import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;

/**
 * @author gkech
 *
 */
public class SequeHostAgent extends Agent{
	 protected Vector playersList = new Vector();    // invites
	 protected int numAgents=2;
	
	protected void setup() {
		try {
	        //summon players
			SummonAgents(numAgents);
			//add host Behaviour
			Behaviour bhost = new HostBehaviour(this,playersList,numAgents);
	        addBehaviour(bhost);
		}
        catch (Exception e) {
            System.out.println( "SetUp Exception " + e );
            e.printStackTrace();
        }
	}
	/**
	 * Summon Agents func
	 */
	protected void SummonAgents(int numAgents){
		
		PlatformController container = getContainerController(); // get a container controller for creating new agents
        // create N player-agents
        try {
            for (int i = 0;  i < numAgents;  i++) {
                // create a new agent
            	String localName = "PgAgent"+i;
            	AgentController summon = container.createNewAgent(localName, "default_pack.Player1Sequential", null);
            	summon.start();
            	// keep the guest's ID on a local list
            	playersList.add( new AID(localName, AID.ISLOCALNAME) );
            }
        }
        catch (Exception e) {
            System.err.println( "Exception while adding guests: " + e );
            e.printStackTrace();
        }
	}

}
