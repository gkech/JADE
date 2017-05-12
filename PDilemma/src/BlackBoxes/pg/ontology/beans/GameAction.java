/**
 * 
 */
package pg.ontology.beans;

import jade.content.Predicate;

/**
 * @author gkech
 *
 */
public class GameAction implements Predicate {
	private static final long serialVersionUID = 1L;
	private String AgentAction;

	/**
	 * @return the agentAction
	 */
	public String getAgentAction() {
		return AgentAction;
	}

	/**
	 * @param agentAction
	 *            the agentAction to set
	 */
	public void setAgentAction(String agentAction) {
		AgentAction = agentAction;
	}

}
