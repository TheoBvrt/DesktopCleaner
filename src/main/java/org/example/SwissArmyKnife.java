package org.example;

import java.io.File;

public class SwissArmyKnife {
    public static String GetCurrentOs () {
        if (System.getProperty("os.name", "generic").contains("Mac"))
            return ("Mac");
        return ("Window");
    }
    public static void PrintList(File[] listToPrint) {
        for (File element : listToPrint) {
            System.out.println(element.getName());
        }
    }
}
