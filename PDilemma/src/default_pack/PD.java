package default_pack;

import java.util.Vector;

public class PD {
	
	Vector<Integer>  Cooperation = new Vector<Integer>(); //vector of vectors
	Vector<Integer>  Co_Defect = new Vector<Integer>(); //vector of vectors
	Vector<Integer>  Defect_Co = new Vector<Integer>(); //vector of vectors
	Vector<Integer>  Defect = new Vector<Integer>(); //vector of vectors

	
	public PD(){
		
		//The rules below were taken from Michael Wooldridge's book, An Introduction to MultiAgent Systems
		
		//Agents Cooperation payoffs
		Cooperation.add(1);
		Cooperation.add(1);
		
		//Agent mixed payoffs	
		Co_Defect.add(5);
		Co_Defect.add(0);
		
		//Agent mixed payoffs
		Defect_Co.add(0);
		Defect_Co.add(5);
		
		//Agents Defect payoffs
		Defect.add(3);
		Defect.add(3);
	}


	public PD(Vector<Integer> cooperation, Vector<Integer> co_Defect, Vector<Integer> defect_Co,
			Vector<Integer> defect) {
		super();
		Cooperation = cooperation;
		Co_Defect = co_Defect;
		Defect_Co = defect_Co;
		Defect = defect;
	}

	/**
	 * @return the cooperation
	 */
	public Vector<Integer> getCooperation() {
		return Cooperation;
	}

	/**
	 * @param cooperation the cooperation to set
	 */
	public void setCooperation(Vector<Integer> cooperation) {
		Cooperation = cooperation;
	}


	/**
	 * @return the co_Defect
	 */
	public Vector<Integer> getCo_Defect() {
		return Co_Defect;
	}


	/**
	 * @param co_Defect the co_Defect to set
	 */
	public void setCo_Defect(Vector<Integer> co_Defect) {
		Co_Defect = co_Defect;
	}


	/**
	 * @return the defect_Co
	 */
	public Vector<Integer> getDefect_Co() {
		return Defect_Co;
	}


	/**
	 * @param defect_Co the defect_Co to set
	 */
	public void setDefect_Co(Vector<Integer> defect_Co) {
		Defect_Co = defect_Co;
	}


	/**
	 * @return the defect
	 */
	public Vector<Integer> getDefect() {
		return Defect;
	}


	/**
	 * @param defect the defect to set
	 */
	public void setDefect(Vector<Integer> defect) {
		Defect = defect;
	}
	
	
	
	

	
}
