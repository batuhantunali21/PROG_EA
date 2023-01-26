import control.FileHelper;
import control.Timer;
import enums.ProgEACompareOption;
import util.ChargingStation;
import util.ChargingStationPathFinder;
import util.CustomComparator;
import view.MyIO_IN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static util.MyConstant.*;


/**
 * @author Batuhan Tunali
 * @version 1.0
 */
public class Main {
    static final String filePath =
            "C:/Users/batuh/IdeaProjects/PRG_EA/src/res/";
    static final String fileName = "csv";


    public static void main(String[] args) throws IOException {

        FileHelper fileHelper = new FileHelper(filePath, fileName);
        if (!fileHelper.checkIfFileExists()) {
            System.err.println("Fehler: Die Datei " + fileName +
                    " wurde in dem Pfad: " + filePath + " nicht gefunden.");
            return;
        }

        Timer.start();
        ArrayList<ChargingStation> validChargingStations =
                loadChargingStationFromFile(fileHelper);
        long elpasedTime = Timer.getElapsedTimeInMilliseconds();

        System.out.println(AMOUNT_VALID +
                validChargingStations.size()
                + " ( " + elpasedTime + "ms ) ");


        Timer.start();
        sortChargingStationByPostalCodeAndPower(validChargingStations);
        long pasedTime = Timer.getElapsedTimeInMilliseconds();
        System.out.println(AMOUNT_VALID + " ( " + elpasedTime + "ms ) ");


        MyIO_IN in = new MyIO_IN();
        int[] inputs = in.getInputs();
        CustomComparator comparator =
                new CustomComparator(ProgEACompareOption.Distance);
        removeChargingStationsWithinMinRange(validChargingStations,
                inputs[0], comparator);

        System.out.println(RUN_ARGUMENTS + inputs[0] + AND + inputs[1]);
        System.out.println(LOAD_PATH_CONTENT + filePath + fileName);
        System.out.println(CHECK_EPSILON + inputs[0]);
        System.out.println(CONSTRUCT_GRAPH_MAX + inputs[1]);
        System.out.println(AMOUNT_CHARGINGSTATION +
                validChargingStations.size() + " " + " ( " + elpasedTime + " " +
                "ms )");
        for (ChargingStation station : validChargingStations) {
            System.out.println(station.toString());
        }
        saveChargingStationInRange(validChargingStations, inputs[1],
                comparator);

        int[] postalCodes =
                in.checkPLZ(validChargingStations.stream()
                        .mapToInt(m -> m.get_postalCode()).toArray());
        ArrayList<ChargingStation> path =
                findPathBetweenPostalCodes(postalCodes, validChargingStations);
        for (ChargingStation station : path) {
            System.out.println(station.toString());
        }
    }


    /**
     * This method creates util.ChargingStation object by parsing the values
     * from a string
     *
     * @param line string containing the values for the util.ChargingStation
     *
     * @return util.ChargingStation object created from the values in the string
     */
    private static ChargingStation
    createChargingStation(String line) {
        try {
            String[] colums = line.split(DELIMITER);
            return new ChargingStation(colums[0],
                    colums[1], colums[2], Integer.parseInt(colums[3]),
                    colums[4], colums[5], Double.parseDouble(colums[6]),
                    Double.parseDouble(colums[7]),
                    Double.parseDouble(colums[8]));

        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    private static ArrayList<ChargingStation> loadChargingStationFromFile
            (FileHelper fileHelper) {

        /**
         * @param filePath The file path where the file is located
         * @param fileName The name of the file to be checked for existence
         */
        System.out.println("Dateifpad: " + filePath);
        Timer.start();
        int amountOfLines = fileHelper.getAmountOfLines();
        long elpasedTime = Timer.getElapsedTimeInMilliseconds();
        System.out.println(READ_IN_DATA + amountOfLines +
                " ( " + elpasedTime + "ms ) ");


        /**
         * This method reads all the lines from the file and process them to
         * create a list of valid charging stations.
         * It first reads all the lines from the file using the "filerHelper
         * .processFile()" method, then it creates an empty list of valid
         * charging stations.
         * It creates a new charging station object using the
         * "createChargingStation(line)" method, if the created charging
         * station is valid, it will be added into the list of valid
         * charging stations.
         *
         * @return list of valid charging stations
         */
        Timer.start();

        //Read all the lines from the file and process them
        ArrayList<String> lines = fileHelper.processFile();
        //Create a list to store the valid charging stations
        ArrayList<ChargingStation> validChargingStations = new ArrayList<>();
        // Iterate through each line and create a charging station object
        for (String line : lines) {
            ChargingStation station = createChargingStation(line);
            // Check if the station is valid, if not print a message and skip it
            if (!station.isChargingStationValid()) {
                System.out.println("Trage Ladestation nicht ein. " +
                        "Breiten- " + "oder "
                        + "Längengrad nicht korrekt: "
                        + station.get_latitude()
                        + " und " + station.get_longitude());
                continue;
            }
            validChargingStations.add(station);
        }
        return validChargingStations;
    }

    /**
     * This method sorts the input list of ChargingStation objects by postal
     * code and power supply.
     * Its uses the CustomComparator class to perform the sorting, with the
     * compare option set to PostalCode
     *
     * @param validChargingStations
     */
    public static void sortChargingStationByPostalCodeAndPower
    (ArrayList<ChargingStation> validChargingStations) {
        Timer.start();
        Collections.sort(validChargingStations,
                new CustomComparator(ProgEACompareOption.PostalCode));
        long elpasedTime = Timer.getElapsedTimeInMilliseconds();
        System.out.println(AMOUNT_VALID + validChargingStations.size() +
                " (in " + elpasedTime + " ms sortiert)");
        for (ChargingStation station : validChargingStations) {
            System.out.println(station.toString());
        }
    }

    /**
     * This method removes all charging stations that are within the
     * specidifed minimum range from the list of valid charging stations.
     * It uses a custom comparator to compare the distance between each
     * charging station pair and determine if they are within the minimum range.
     *
     * @param validChargingStations the list of valid charging stations that
     *                              will have station removed if they are
     *                              within the minimum range
     * @param minimumRangeInput     the minimum range that is used to determine
     *                              which charging stations should be removed
     *                              from the list
     * @param comparator            the custom comparator that is used to compare the
     *                              distance between each charging station pair
     */
    public static void removeChargingStationsWithinMinRange
    (ArrayList<ChargingStation>
             validChargingStations, int minimumRangeInput,
     CustomComparator comparator) {
        comparator.set_distance(minimumRangeInput);
        Timer.start();
        for (int i = 0; i
                < validChargingStations.size(); i++) {
            for (int j = i + 1; j < validChargingStations.size();
                 j++) {
                int result =
                        comparator.compare(validChargingStations.get(i),
                                validChargingStations.get(j));
                if (result == -1) {
                    validChargingStations
                            .remove(validChargingStations.get(j));
                    j--;
                }
            }
            if (validChargingStations.get(i).get_postalCode() == 86153) {
                /**
                 * real
                 * System.out.print(validChargingStations.get(i)
                 .get_postalCode()
                 + " " + validChargingStations.get(i).get_location() +
                 " -> "); */
                for (ChargingStation station :
                        validChargingStations.get(i)
                                .get_chargingStationInRange()) {
                    System.out.print(station.get_postalCode() + " "
                            + station.get_location() + ", ");
                }
                /** System.out.print(validChargingStations.get(i)
                 .get_postalCode()
                 + " " + validChargingStations.get(i).get_location() +
                 " -> "); */
            }
        }
    }

    /**
     * This method saves the charging stations that are in range of each
     * charging station in the validChargingStations list.
     * It uses a custom comparator to compare the distance between each
     * charging station.
     *
     * @param validChargingStations list of charging stations to find the
     *                              stations in range of
     * @param maximumRangeInput     maximum range to search for other stations in
     * @param comparator            custom comparator to use to compare the distance
     *                              between charging stations.
     */
    public static void saveChargingStationInRange(ArrayList<ChargingStation>
                                                          validChargingStations,
                                                  int maximumRangeInput,
                                                  CustomComparator comparator) {
        comparator.set_distance(maximumRangeInput);
        for (int i = 0; i < validChargingStations.size(); i++) {
            for (int j = 0; j < validChargingStations.size(); j++) {
                int result = comparator.compare(validChargingStations.get(i),
                        validChargingStations.get(j));
                if (result == -1) {
                    validChargingStations.get(i)
                            .get_chargingStationInRange()
                            .add(validChargingStations.get(j));
                }
            }
            Collections.sort(validChargingStations.get(i)
                            .get_chargingStationInRange(),
                    (stationOne, stationTwo) -> {
                        if (stationOne.get_distance()
                                > stationTwo.get_distance()) {
                            return -1;
                        }
                        if (stationOne.get_distance()
                                < stationTwo.get_distance()) {
                            return 1;
                        }
                        return 0;
                    });
        }
    }

    /**
     * This method finds the path between the given postal codes by filtering
     * the valid charging stations by starting postal code.
     *
     * @param postalCodes           an array of integers containing the starting and
     *
     *                              destination postal codes
     * @param validChargingStations an Arraylist of ChargingStation objects
     *                              representing the valid charging stations
     *
     * @return an ArrayList of ChargingStation objects representing the path
     * between the given postal codes, or an empty ArrayList if no path is found
     */
    public static ArrayList<ChargingStation> findPathBetweenPostalCodes
    (int[] postalCodes, ArrayList<ChargingStation> validChargingStations) {
        Optional<ChargingStation> start =
                validChargingStations.stream()
                        .filter(f -> f.get_postalCode()
                                == postalCodes[0]).findFirst();
        if (start.isPresent()) {
            ChargingStationPathFinder finder =
                    new ChargingStationPathFinder(postalCodes[1]);
            return finder.findPath(start.get());

        }
        return new ArrayList<>();
    }
}