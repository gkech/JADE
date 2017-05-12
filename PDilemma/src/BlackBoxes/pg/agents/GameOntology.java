/**
 * 
 */
package pg.agents;

import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;

/**
 * @author gkech
 *
 */
public class GameOntology extends BeanOntology {

	private static final long serialVersionUID = 1L;
	private static Ontology INSTANCE;
	// name
	public static final String ONTOLOGY_NAME = "PDOntology";

	public synchronized final static Ontology getInstance() throws BeanOntologyException {
		if (INSTANCE == null) {
			INSTANCE = new GameOntology();
		}
		return INSTANCE;
	}

	private GameOntology() throws BeanOntologyException {
		super(ONTOLOGY_NAME);

		// add(Game.class); //gamename
		// add(GameAction.class); //defect or cooperate
		// add(PersonalPayoff.class); //action's payoff

		add("pg.ontology.beans"); // all beans in one package
	}

}
