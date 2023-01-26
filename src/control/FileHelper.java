package control;

import java.io.*;
import java.util.ArrayList;

/**
 * This class provides helper methods to work with files, including reading
 * and checking the file existence.
 *
 * @author Batuhan Tunali
 * @version 1.0
 */
public class FileHelper {
    public String path;
    public String fileName;

    public FileHelper(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * This method checks if a file exists or not
     *
     * @return true if the file exists, false otherwise
     */
    public boolean checkIfFileExists() {
        return new File(this.path + this.fileName).exists();
    }

    /**
     * This method reads a file and returns the number of lines of the file
     *
     * @return number of lines in the file.
     * IOException is thrown by method getFile() if there is an
     * error
     */
    public int getAmountOfLines() {
        int lines = 0;
        try (BufferedReader reader = getFile()) {
            while (reader.readLine() != null) lines++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * This method reads a file and returns the content in an ArrayList of
     * Strings
     *
     * @return ArrayList of strings containing the content of the file
     * IOException is thrown by method getFile() if there is an
     * error
     * reading the file
     */
    public ArrayList<String> processFile() {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = getFile()) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * This method returns a BufferedReader for a file
     *
     * @return BufferedReader for the file
     * @throws FileNotFoundException if the file is not found
     */
    private BufferedReader getFile() throws FileNotFoundException {
        return new BufferedReader(new FileReader
                (this.path + this.fileName));
    }
}