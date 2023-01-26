package control;

/**
 * The Timer class provides a simple utility for measuring elapsed time in
 * milliseconds.
 *
 * @author Batuhan Tunali
 * @version 1.0
 */
public class Timer {

    private static final long NANO_2_MILLI = 1000000;
    private static long startTime = System.nanoTime();


    /**
     * Private constructor to prevent instantiation of Timer class
     */
    private Timer() {
    }

    /**
     * This method starts the time measurement
     */
    public static void start() {
        startTime = System.nanoTime();
    }

    /**
     * This method returns the elapsed time in milliseconds
     *
     * @return elapsed time in milliseconds
     */
    public static long getElapsedTimeInMilliseconds() {

        return (System.nanoTime() - startTime) / NANO_2_MILLI;
    }
}