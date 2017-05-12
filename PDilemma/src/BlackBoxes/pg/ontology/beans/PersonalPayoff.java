/**
 * 
 */
package pg.ontology.beans;

import jade.content.Predicate;

/**
 * @author gkech
 *
 */
public class PersonalPayoff implements Predicate{
	private static final long serialVersionUID = 1L;
	private Integer ppayoff;
	
	/**
	 * @return the ppayoff
	 */
	public Integer getPpayoff() {
		return ppayoff;
	}

	/**
	 * @param ppayoff the ppayoff to set
	 */
	public void setPpayoff(Integer ppayoff) {
		this.ppayoff = ppayoff;
	}
}
