/**
 * 
 */
package pg.agents;

import java.util.Vector;

import host.behaviour.HostBehaviour;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;

/**
 * @author gkech
 *
 */
public class Host extends Agent{
	 private static final long serialVersionUID = 1L;
	 protected Vector playersList = new Vector<Agent>();
	 protected int numAgents=2;
	/**
	 * Ontology Declarations
	 */
	private ContentManager manager = (ContentManager) getContentManager();
	// This behaviour "speaks" the SL language
	private Codec codec = new SLCodec();
	// This behaviour "knows" the Music-Shop ontology
	private Ontology ontology;
	//end declarations
	 
	protected void setup() {
		
        try {
            ontology = GameOntology.getInstance();
        }
        catch (BeanOntologyException e) {
            e.printStackTrace();
        }
        this.getContentManager().registerLanguage(codec);
        this.getContentManager().registerOntology(ontology);
		
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
           	AgentController summon = container.createNewAgent(localName, "pg.agents.Player", null);
           	summon.start();
           	// keep the guest's ID on a local list
           	playersList.add( new AID(localName, AID.ISLOCALNAME) );
           }
       }
       catch (Exception e) {
           System.err.println( "Exception while adding agents: " + e );
           e.printStackTrace();
       }
	}

}