package util;

import java.util.ArrayList;

/**
 * A class representing a charging station.
 *
 * @author Batuhan Tunali
 * @version 1.0
 */
public class ChargingStation {

    /**
     * _operator - the operator of the charging station
     * _street - the street name where the charging station is located
     * _houseNumber - the house number of the charging station
     * _postalCode - the postal code of the charging station
     * _location - the city or location of the charging station
     * _state - the state or province of the charging station
     * _latitude - the latitude of the charging station
     * _longitude - the longitude of the charging station
     * _powerSupply - the power supply of the charging station
     * _chargingStationInRange - a list of charging stations within a
     * certain range of the current station
     */
    private String _operator;
    private String _street;
    private String _houseNumber;
    private int _postalCode;
    private String _location;
    private String _state;
    private double _latitude;
    private double _longitude;
    private double _powerSupply;
    private double _distance;
    private ArrayList<ChargingStation> _chargingStationInRange;

    public ChargingStation(String _operator, String _street,
                           String _houseNumber, int _postalCode,
                           String _location,
                           String _state, double _latitude, double _longitude,
                           double _powerSupply) {

        this._operator = _operator;
        this._street = _street;
        this._houseNumber = _houseNumber;
        this._postalCode = _postalCode;
        this._location = _location;
        this._state = _state;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._powerSupply = _powerSupply;
        this._chargingStationInRange = new ArrayList<>();
    }

    public String get_operator() {
        return _operator;
    }

    public String get_street() {
        return _street;
    }

    public String get_houseNumber() {
        return _houseNumber;
    }

    public int get_postalCode() {
        return _postalCode;
    }

    public String get_location() {
        return _location;
    }

    public double get_latitude() {
        return _latitude;
    }

    public double get_longitude() {
        return _longitude;
    }

    public double get_powerSupply() {
        return _powerSupply;
    }

    public ArrayList<ChargingStation> get_chargingStationInRange() {
        return _chargingStationInRange;
    }

    public double get_distance() {
        return _distance;
    }

    public void set_distance(double _distance) {
        this._distance = _distance;
    }

    /**
     * The method checks if the charging station is located within valid
     * coordinates
     *
     * @return boolean indicating whether the charging station's latitude and
     * longitude are within valid range (between 47 and 55 latitude,
     * and between 6 and 15 longitude)
     */
    public boolean isChargingStationValid() {
        return this.get_latitude() < 55 && this.get_latitude() > 47 &&
                this.get_longitude() < 15 && this.get_longitude() > 6;
    }

    /**
     * Returns a string representation of the charging station.
     * The string includes the operator name, street and house number, postal
     * code and location and their power supply.
     *
     * @return a string representation of the charging station
     */
    @Override
    public String toString() {
        return get_operator() + ", " + get_street() + " " + get_houseNumber() +
                ", " + get_postalCode() + " " + get_location() + ", " +
                get_powerSupply();
    }
}
