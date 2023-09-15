package org.example;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileSortingApp {
    public void Run() {
        File[] files;

        List<String> extensions = new ArrayList<>();
        File directory = new File("C:\\Users\\theo2\\OneDrive\\Bureau");
        files = directory.listFiles();

        if (files == null)
            return;
        for (File file : files) {
            boolean exist = false;
            if (file.isFile()) {
                String newExtension = getString(file);
                for (String extension : extensions) {
                    if (Objects.equals(extension, newExtension)) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    extensions.add(newExtension);
                }
            }
        }
        PrintList(extensions);
    }

    private static String getString(File file) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(file.getName());
        String newExtension;
        int subStringIndex;

        if (mimeType != null) {
            subStringIndex = mimeType.lastIndexOf('/');
            newExtension = mimeType.substring(0, subStringIndex);
        } else {
            subStringIndex = file.getName().lastIndexOf('.');
            newExtension = file.getName().substring(subStringIndex + 1);
        }
        return newExtension;
    }

    public static void PrintList(List<String> listToPrint) {
        for (String element : listToPrint) {
            System.out.println(element);
        }
    }
}
