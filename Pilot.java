/**
 * File By: Mayank Sharma Student ID: 936970
 * 
 * A pilot responsible for attaching to a ship and guiding 
 * the ship all the way from the arrival zone to the waiting zone
 *
 * It is responsible for:
 *  - attaching to the arrived ship;
 *  - gaining tugs;
 *  - guiding ship to the berth;
 *  - releasing tugs;
 *  - leaving from the ship once arrived at the departing zone;
 *  - manages the pilots gain the ships or leaving the ships;
 *  
 * @author mayanks1@student.unimelb.edu.au
 *
 */

public class Pilot extends Thread {
	// defines the pilot number
	private int pilotNumber;
	
	/** denotes the arrival wait zone. */ 
	private WaitZone arrivalZone;
	
	/** denotes the departure wait zone. */
	private WaitZone departureZone;
	
	/** holds the tugs passed to the pilot. */
	private Tugs tugs;
	
	/** 
	 * holds the berth at which the pilot has to 
	 * bring the ship to.
	 */
	private Berth berth;
	
	/** 
	 * holds the ship passed to from 
	 * the waiting zone to the pilot 
	 */
	private volatile Ship ship;
	
	// boolean to indicate if a ship is acquired by the pilot
	private volatile boolean shipAcquired;
	
	// variable to store number of tugs used in docking
	private int dockingTugs = Params.DOCKING_TUGS;
	
	// stores number of tugs used for undocking
	private int undockingTugs = Params.UNDOCKING_TUGS;
	
	// creates a pilot 
	public Pilot (int pilotNumber, WaitZone arrivalZone, WaitZone departureZone, Tugs tugs, Berth berth) {
		this.pilotNumber = pilotNumber;
		this.arrivalZone = arrivalZone;
		this.departureZone = departureZone;
		this.tugs = tugs;
		this.berth = berth;
		this.shipAcquired = false;
	}
	
	// method to run a pilot thread
	public void run() {	
		try {
			while(!isInterrupted()) {
				
				// help the pilot to get the ship from the zone
				ship = arrivalZone.getShip(pilotNumber, shipAcquired);
				
				// send tugs to the pilot
				tugs.addTugs(tugs, dockingTugs, pilotNumber);
				
				// allow the pilot to leave the waiting zone
				arrivalZone.pilotLeave();
				
				// represent pilot traveling to the berth
				sleep(Params.TRAVEL_TIME);
				
				// allow pilot to check for shield
				checkShield();
				
				// docks ship to the berth
				berth.docking(ship);
				
				// represent the ship undocking
				sleep(Params.UNDOCKING_TIME);
				
				// remove tugs from the ship after docking
				tugs.removeTugs(tugs , dockingTugs, pilotNumber);
				
				// unload ship at berth
				berth.unloading(ship);
				
				// add tugs to ship before undocking
				tugs.addTugs(tugs, undockingTugs, pilotNumber);
				
				// check the shield before undocking
				checkShield();
				
				// undock ship
				berth.undocking(ship);
				
				// represent the ship undocking
				sleep(Params.UNDOCKING_TIME);
				
				// repesent the pilot and the ship traveling
				// to the wait departure zone
				sleep(Params.TRAVEL_TIME);
				
				// pilot arrives the departure zone
				departureZone.pilotArrive(ship);
			
				// remove the tugs from the ship
				tugs.removeTugs(tugs, undockingTugs, pilotNumber);
				
				// pilot leave the ship from the departure zone
				shipAcquired = departureZone.leaveShip(pilotNumber);						
			}		
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	// synchronized method to check for shield if it is active
	public synchronized void checkShield() {
		while(berth.isShield());
	}
	
}
