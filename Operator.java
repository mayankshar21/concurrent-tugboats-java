/**
 * File By: Mayank Sharma Student ID: 936970
 * 
 * An operator responsible for turning the shield on and off
 * after specific number of time
 *
 * It is responsible for:
 *  - turning the shield active;
 *  - deactivating the shield;
 *  
 * @author mayanks1@student.unimelb.edu.au
 * 
 */

public class Operator extends Thread {
	
	/** denotes the berth at which the operator 
	 * activates shield
	 */
	private volatile Berth berth;
	
	// creates an operator
	public Operator (Berth berth) {
		this.berth = berth;
	}
	
	// method to run the operator thread
	public void run() {
		
		while(!isInterrupted()) {
			
			// time before shield is activated
			debrisShieldActivation();
			
			// shield is activated
			activateShield();
			
			// time duration during which shield
			// is active
			debrisTime();
			
			// shield is deactivated
			deactivateShield();
			
		}
	}
	
	// method to activate shield
	private synchronized void activateShield() {
		System.out.println("Shield is activated");
		berth.setShield(true);
	}
	
	// method to deactivate shield
	private synchronized void deactivateShield() {
		System.out.println("Shield is deactivated");
		berth.setShield(false);
	}
	
	// method to run time before shield is active
	private void debrisShieldActivation() {

		try {
			sleep(Params.debrisLapse());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// method to run duration for which shield is active
	private void debrisTime() {
		
		try {
			sleep(Params.DEBRIS_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
