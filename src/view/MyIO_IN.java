package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The class is responsible for reading inputs from the user. It uses a
 * BufferedReader to read input from the System.in stream.
 *
 * @author Batuhan Tunali
 * @version 1.0
 */
public class MyIO_IN {

    private final BufferedReader br;

    public MyIO_IN() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * This method is used to get the minimum and maximum range of the
     * charging stations.
     * The user is prompted to input the range through the console.
     * The method checks if the input is valid, if not it will prompt the
     * user again to enter the correct value, as long as the value is invalid.
     *
     * @return int[] containing minimum range at index 0 and maximum range at
     * index 1
     * @throws IOException if the input from the console cannot be read
     */
    public int[] getInputs() throws IOException {
        int minRange;
        int maxRange;
        boolean validInputs;
        do {
            minRange = Integer.MAX_VALUE;
            maxRange = Integer.MIN_VALUE;
            validInputs = true;
            do {
                MyIO_OUT.inputMinimumRange();
                try {
                    minRange = Integer.parseInt(br.readLine());
                } catch (Exception e) {
                    MyIO_OUT.inputMinimumRangeError();
                }
            } while (minRange >= Integer.MAX_VALUE);
            do {
                MyIO_OUT.inputMaximumRange();
                try {
                    maxRange = Integer.parseInt(br.readLine());
                } catch (Exception e) {
                    MyIO_OUT.inputMaximumRangeError();
                }
            } while (maxRange <= Integer.MIN_VALUE);


            if (minRange >= maxRange) {
                MyIO_OUT.inputMaximumRangeIsLowerThanMinimum();
                validInputs = false;
            }
        } while (!validInputs);

        return new int[]{
                minRange, maxRange};
    }

    /**
     * This method takes in an array of valid postal codes and prompts the
     * user to input a start and end postal code.
     * It checks if the input postal codes are valid and exist in the array
     * of valid postal codes, if the input postal code is invalid or does not
     * exist in the array, the user will be prompted to enter a new postal code.
     *
     * @param validPostalCodes an array of valid postal codes
     *
     * @return an array of int containing the start and end postal codes
     * @throws IOException if there is an error in reading user input
     */
    public int[] checkPLZ(int[] validPostalCodes) throws IOException {
        int startPostalCode = 0;
        int endPostalCode = 0;
        do {
            do {
                MyIO_OUT.inputStartPostalCode();
                try {
                    startPostalCode = Integer.parseInt(br.readLine());
                } catch (NumberFormatException nfe) {
                    MyIO_OUT.postalCodeError();
                }
            } while (startPostalCode != 0 && !postalCodeExists(validPostalCodes,
                    startPostalCode));
            do {
                MyIO_OUT.inputDestinationPostalCode();
                try {
                    endPostalCode = Integer.parseInt(br.readLine());
                } catch (Exception e) {
                    MyIO_OUT.postalCodeError();
                    MyIO_OUT.postalCodeEquals();
                }
            } while (endPostalCode != 0 && !postalCodeExists(validPostalCodes,
                    endPostalCode));
        } while (endPostalCode == startPostalCode);
        if (endPostalCode == startPostalCode)
            return new int[]{
                    startPostalCode, endPostalCode};
    }

    /**
     * This method takes in  an array of valid postal codes and a postal
     * code, and checks if the postal code exists in the array.
     *
     * @param validPostalCodes an array of valid postal codes
     * @param postalCode       postalCode a postal code to check against the array
     *
     * @return true if the postal code exists in the array, otherwise false
     */
    private boolean postalCodeExists(int[] validPostalCodes, int postalCode) {
        for (int validPostalCode : validPostalCodes) {
            if (validPostalCode == postalCode) {
                return true;
            }
        }
        return false;
    }
}