package util;

import enums.ProgEACompareOption;

import java.util.Comparator;

/**
 * A class representing compare methods.
 *
 * @author Batuhan Tunali
 * @version 1.0
 */

public class CustomComparator implements Comparator<ChargingStation> {

    private ProgEACompareOption _option;
    private int _distance;

    public CustomComparator(ProgEACompareOption option) {
        this._option = option;
    }

    public CustomComparator(ProgEACompareOption _option, int _minimumDistance) {
        this._option = _option;
        this._distance = _minimumDistance;
    }

    /**
     * This method is used to compare two ChargingStation objects. Compares two
     * charging stations based on the specified option,
     * "PostalCode" and "Distance".
     *
     * @param stationOne the first object to be compared.
     * @param stationTwo the second object to be compared.
     *
     * @return an int value indicating the relative order of the two stations.
     */
    @Override
    public int compare(ChargingStation stationOne,
                       ChargingStation stationTwo) {
        if (this._option == ProgEACompareOption.PostalCode) {
            return this.comparePostalCode(stationOne, stationTwo);
        } else {
            return this.compareDistance(stationOne, stationTwo);
        }

    }

    /**
     * This method is used to compare the distance between two
     * ChargingStation objects with the parameters "stationOne" and "stationTwo"
     * while using the haversine formula of class "Haversine"
     *
     * @param stationOne the first ChargingStation object to be compared
     * @param stationTwo the second ChargingStation object to be compared
     *
     * @return -1 if distance is less than or equal to the _distance field, 1
     * if the distance is grater, 0 if the distance is equal.
     */
    private int compareDistance(ChargingStation stationOne,
                                ChargingStation stationTwo) {
        double haversine = Haversine.haversine(stationOne.get_latitude(),
                stationOne.get_longitude(), stationTwo.get_latitude(),
                stationTwo.get_longitude());
        stationTwo.set_distance(haversine);
        if (haversine <= this._distance) {
            return -1;
        } else if (haversine > this._distance) {
            return 1;
        }
        return 0;
    }

    /**
     * This method is used to compare the postal code of two ChargingStation
     * objects.
     * The method takes in two ChargingStation objects as parameters,
     * "stationOne" and "station Two".
     * If the postal code of stationOne is greater than stationTwo, the
     * method returns 1, if it's less, the method returns -1.
     * If the postal codes are equal, the method calls the
     * "comparePowerSupply" method to compare their power supplies.
     *
     * @param stationOne the first ChargingStation object to be compared
     * @param stationTwo the second ChargingStation object to be compared
     *
     * @return 1 if the postal code of stationOne is greater than that of
     * stationTwo, -1 if the postal code of stationOne is less than that of
     * stationTwo
     */
    private int comparePostalCode(ChargingStation stationOne,
                                  ChargingStation stationTwo) {
        if (stationOne.get_postalCode() == stationTwo.get_postalCode()) {
            return this.comparePowerSupply(stationOne.get_powerSupply(),
                    stationTwo.get_powerSupply());
        }

        if (stationOne.get_postalCode() > stationTwo.get_postalCode()) {
            return 1;
        }
        return -1;
    }

    /**
     * This method compared the power Supply of two ChargingStations.
     * The method takes two double values as parameters, "powerSupplyOne" and
     * "powerSupplyTwo".
     * If the power supply of powerSupplyOne is less than that of
     * powerSupplyTwo, the method returns 1, if it's greater, the method
     * returns -1, if it's equal, the method returns 0.
     *
     * @param powerSupplyOne the first power supply to be compared
     * @param powerSupplyTwo the second power supply to be compared
     *
     * @return 1 if the power supply of powerSupplyOne is less than that of
     * powerSupplyTwo -1 if it's greater, 0 if it's equal
     */
    private int comparePowerSupply(double powerSupplyOne,
                                   double powerSupplyTwo) {
        if (powerSupplyOne < powerSupplyTwo) {
            return 1;
        }
        if (powerSupplyOne > powerSupplyTwo) {
            return -1;
        }
        return 0;
    }

    public void set_distance(int _distance) {
        this._distance = _distance;
    }
}