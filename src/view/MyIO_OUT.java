package view;

/**
 * This class represents different methods for the console's output,
 * depending on the context.
 *
 * @author Batuhan Tunali
 * @version 1.0
 * @precondition
 */
public class MyIO_OUT {

    public static void inputMinimumRange() {
        System.out.println("Bitte Epsilonumgebung angeben");
    }

    public static void inputMinimumRangeError() {
        System.out.println("Bitte eine natürliche Zahl eingeben");
    }

    public static void inputMaximumRange() {
        System.err.println("Bitte maximale Reichweite angeben");
    }

    public static void inputMaximumRangeError() {
        System.err.println("Bitte eine natürliche Zahl eingeben");
    }

    public static void inputMaximumRangeIsLowerThanMinimum() {
        System.err.println("Die maximale Reichweite muss größer sein als die " +
                "Epsilonumgebung!!");
    }

    public static void inputStartPostalCode() {
        System.out.println("Bitte geben Sie einen Startpunkt an");
    }

    public static void inputDestinationPostalCode() {
        System.out.println("Bitte geben Sie einen Zielpunkt an");
    }

    public static void PostalCodeEquals() {
        System.err.println("Es wurde eine Postleitzahl ausgewählt, die dem " +
                "Startpunkt gleich ist!");
    }

    public static void postalCodeError() {
        System.err.println("Falsches Format bei der Eingabe der Postleitzahl. " +
                "Musterpostleitzahl 12345 oder 1234");
    }
}