/**
 * 
 */
package pg.agents;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import player.behaviour.PlayBehaviour;

/**
 * @author gkech
 *
 */
public class Player extends Agent {
	private static final long serialVersionUID = 1L;
	/**
	 * Ontology Declarations
	 */
	private ContentManager manager = (ContentManager) getContentManager();
	// This behaviour "speaks" the SL language
	private Codec codec = new SLCodec();
	// This behaviour "knows" the Music-Shop ontology
	private Ontology ontology;
	// end declarations

	protected void setup() {
		try {
			ontology = GameOntology.getInstance();
		} catch (BeanOntologyException e) {
			e.printStackTrace();
		}
		this.getContentManager().registerLanguage(codec);
		this.getContentManager().registerOntology(ontology);

		try {
			// play the game
			Behaviour bplayer1 = new PlayBehaviour(this);
			addBehaviour(bplayer1);
		} catch (Exception e) {
			System.out.println("SetUp Exception " + e);
			e.printStackTrace();
		}
	}
}