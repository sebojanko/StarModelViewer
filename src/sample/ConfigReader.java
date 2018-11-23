package sample;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ConfigReader {
    public static String getConnectionString() {
        String connString = null;
        try {
            Scanner in = new Scanner(new FileReader("config.txt"));
            connString = in.next();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return connString;
    }
}
