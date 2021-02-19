/**
 * File By: Mayank Sharma Student ID: 936970
 * 
 * The Class that manages all the responsibilities 
 * of the adding or removing tugs from the ship.
 *
 * It is responsible for:
 *  - giving the ship the tugs to move to berth or depart zone;
 *  - removing the tugs from the ship at berth or depart zone;
 *
 * @author mayanks1@student.unimelb.edu.au
 *
 */

public class Tugs extends Thread{
	
	// defines the total number of tugs 
	private volatile int numberOfTugs;
	
	// creates a tug
	public Tugs(int numberOfTugs) {
		this.numberOfTugs = numberOfTugs;
	}
	
	// synchronized method to give tug to the pilot
	// checks if the number of tugs is not less than 0
	public synchronized int getTugs() {
		while(numberOfTugs <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		numberOfTugs--;
		notifyAll();
		return numberOfTugs;
	}
	
	// synchronized method to remove tugs from the pilot
	// checks if the number of tugs is not less than 0
	public synchronized int putTugs() {
		while(numberOfTugs >= Params.NUM_TUGS) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		numberOfTugs++;
		notifyAll();
		return numberOfTugs;
	}
	
	// method to get number of tugs
	public int getNumberOfTugs() {
		return numberOfTugs;
	}
	
	// method for pilot to obtain the required number of tugs
	public void removeTugs(Tugs tugs, int tugsRequired, int pilotNumber) {
		for (int i = 0; i < tugsRequired; i++) {
			tugs.putTugs();
		}
		
		System.out.println("pilot " + pilotNumber + " releases " + 
		tugsRequired + " tugs (" + tugs.getNumberOfTugs() + " available)");
	}
	
	// method for pilot to release the required number of tugs
	public void addTugs(Tugs tugs, int tugsRequired, int pilotNumber) {
		for (int i = 0; i < tugsRequired; i++) {
			tugs.getTugs();
		}
		
		System.out.println("pilot " + pilotNumber + " acquires " + 
		tugsRequired + " tugs (" + tugs.getNumberOfTugs() + " available)");
	}
}
