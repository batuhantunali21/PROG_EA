package enums;

/**
 * Enum to represent the options available to compare the Postal code or
 * Distance
 *
 * @author Batuhan Tunali
 * @version 1.0
 */
public enum ProgEACompareOption {

    /**
     * PostalCode - option to compare the postal code
     * Distance - option to compare the distance
     */
    PostalCode,
    Distance;
    int START_INDEX = -1;
    String DELIMITER = ";";
    String ITEM_DELIMITER = "; ";
}