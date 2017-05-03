/**
 * 
 */
package GameOntology;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
/**
 * @author gkech
 *
 */
public class Player1Sequential extends Agent{
	
	protected void setup() {
		try {
	        //play the game
			Behaviour bplayer1 = new PlayBehaviour(this);
	        addBehaviour(bplayer1);
		}
        catch (Exception e) {
            System.out.println( "SetUp Exception " + e );
            e.printStackTrace();
        }
 	}
}
