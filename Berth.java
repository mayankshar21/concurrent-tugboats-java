/**
 * File By: Mayank Sharma Student ID: 936970
 * 
 * A berth class responsible for docking, undocking 
 * and unloading the ship.
 * 
 * It is responsible for:
 *  - docking the ship on berth;
 *  - undocking the ship from berth;
 *  - unloading the ship on berth;
 *  
 * @author mayanks1@student.unimelb.edu.au
 * 
 */

public class Berth extends Thread {
	
	// defines berth
	private String berth;
	
	// flag used to activate and deactivate shield
	private volatile boolean shield;
	
	// flag used for docking and undocking shield
	private volatile boolean docking;
	
	// creates berth
	public Berth (String berth) {
		this.berth = berth;
		this.shield = false;
		this.docking = true;
		
	}
	
	// method to dock the ship when the berth is empty
	// else make the ship wait
	public synchronized void docking(Ship ship) {
		while(!docking) {	
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		docking = false;
		System.out.println(ship.toString() + " docks at berth");
		notifyAll();
		
	}
	
	// method to undocking the ship when the unloading is done
	public synchronized void undocking(Ship ship) {
		while(docking) {
			try {	
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		docking = true;
		System.out.println(ship.toString() + " undocks from berth");
		notifyAll();
		
	}
	
	// method to unload the ship when the ship is loaded and on berth
	public synchronized void unloading(Ship ship) {
		if(!ship.loaded) {
			try {
				sleep(Params.UNLOADING_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ship.loaded = false;
		System.out.println(ship.toString() + " being unloaded.");
		notifyAll();
	}

	// method to check shield flag
	public boolean isShield() {
		return shield;
	}

	// method to set shield flag
	public void setShield(boolean shield) {
		this.shield = shield;
	}
}
