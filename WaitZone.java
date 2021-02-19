/**
 * File By: Mayank Sharma Student ID: 936970
 * 
 * The Class that manages all the responsibilities 
 * of the arrival and departure waiting zones.
 *
 * It is responsible for:
 *  - holding new ships that arrive or depart from the zones;
 *  - manages the pilots gain the ships or leaving the ships;
 *
 * @author mayanks1@student.unimelb.edu.au
 *
 */

public class WaitZone extends Thread{
	
	// defines the type of waiting zone
	private volatile String zoneType;
	
	// a flag indicating if the ship as arrived to the zone
	private volatile boolean shipArrived;
	
	// a flag indicating if the ship has been acquired by the pilot
	private volatile boolean shipAcquired;
	
	/**
	 * instance variable of Ship class to store
	 * ship object from the Producer
	 */
	private volatile Ship ship;
	
	// create new waitzone 
	public WaitZone(String zoneType) {
		this.zoneType = zoneType;
		this.shipArrived = true;
		this.shipAcquired = false;
	}
	
	// wait for a ship to arrive at arrive zone otherwise, notify all 
	// that ship has arrived
	public synchronized void arrive(Ship ship) { 
		while(!shipArrived) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
		this.ship = ship;
		shipArrived = false;
		System.out.println(ship.toString() + " arrives at arrival zone");	
		notifyAll();			
	}	
		
	// wait for a ship to arrive at the departure zone otherwise
	// depart the ship and notify others that ship has left
	public synchronized void depart() {		
		while(shipArrived) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 
		shipArrived = true;
		System.out.println(ship.toString() + " departs departure zone");
		notifyAll();
	}
	
	// wait for the pilot to get ship
	public synchronized Ship getShip(int pilotNumber, boolean shipPilot) {
		while(shipArrived || shipPilot) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		shipPilot = true;
		this.shipAcquired = shipPilot;
		System.out.println("pilot " + pilotNumber + " acquires " + ship.toString());
		return ship;
	}
	
	// wait for the pilot to leave the arrival zone
	public synchronized void pilotLeave() {
		while(!shipAcquired) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.ship = ship;
		shipArrived = true;
		notifyAll();
	}
	
	// wait for the pilot to arrive the departure zone
	// when the pilot arrives with the ship the ship arrived 
	// is made false so that the ship is about to leave
	public synchronized void pilotArrive(Ship ship) {
		while(!shipArrived) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 
		
		shipArrived = false;
		this.ship = ship;
		notifyAll();
	}
	
	// allow the pilot to leave the ship from the departure zone
	public synchronized boolean leaveShip(int pilotNumber) {
		System.out.println("pilot " + pilotNumber + " releases " + ship.toString());
		shipAcquired = false;
		
		return shipAcquired;
	}
}

