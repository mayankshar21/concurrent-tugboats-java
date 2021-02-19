/**
 * File By: Mayank Sharma Student ID: 936970
 * 
 * Class containing all the parameters used to run the simulation
 *
 * It is responsible for:
 *  - passing the constant values to the other classes for varying parametric analysis;
 *  
 * @author mayanks1@student.unimelb.edu.au
 * 
 */
import java.util.Random;

class Params {
	
	/** defines number of pilots */
    static final int NUM_PILOTS = 2;

    /** defines total number of tugs */
    static final int NUM_TUGS = 5;

    /** total number of docking tugs */
    static final int DOCKING_TUGS = 3;

    /** total tugs used for undocking */
    static final int UNDOCKING_TUGS = 2;

    /** total docking time for ship docking */
    static final int DOCKING_TIME = 800;

    /** total undocking time for ship undocking */
    static final int UNDOCKING_TIME = 400;

    /** total unloading time */
    static final int UNLOADING_TIME = 1200;

    /** 
     * time taken for the ship to travel to 
     * and from the waiting zones and berth 
     */
    static final int TRAVEL_TIME = 800;

    /** total debris time */
    static final int DEBRIS_TIME = 1800;

    /** total max arrival time */
    private static final int MAX_ARRIVAL_INTERVAL = 400;
    
    /** total max departure time */
    private static final int MAX_DEPARTURE_INTERVAL = 1000;

    /** total max shield active time */
    private static final int MAX_DEBRIS_INTERVAL = 2400;

    // time before shield is active
    static int debrisLapse() {
        Random rnd = new Random();
        return rnd.nextInt(MAX_DEBRIS_INTERVAL);
    }

    // varying arrival time 
    static int arrivalLapse() {
        Random rnd = new Random();
        return rnd.nextInt(MAX_ARRIVAL_INTERVAL);
    }

    // varying departure time
    static int departureLapse() {
        Random rnd = new Random();
        return rnd.nextInt(MAX_DEPARTURE_INTERVAL);
    }
}
