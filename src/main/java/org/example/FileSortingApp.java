package org.example;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

public class FileSortingApp {
    public void Run() {
        File[] files;

        File directory = new File(Data.desktopPath);
        List<String> extensions = new ArrayList<>();
        
        files = directory.listFiles();
        if (files == null)
            System.exit(1);
        for (File file : files) {
            boolean exist = false;
            if (file.isFile()) {
                String newExtension = getString(file);
                if (newExtension == null)
                    continue;
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
        if (CreateDirectories(extensions) == 1) {
            System.exit(1);
        }
        SwissArmyKnife.PrintList(files);
        MoveFileInDirectory(files);
        System.exit(0);
    }

    private static String getString(File file) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(file.getName());
        String newExtension = null;
        int subStringIndex;

        if (mimeType != null) {
            subStringIndex = mimeType.lastIndexOf('/');
            newExtension = mimeType.substring(0, subStringIndex);
        } else if (file.getName().contains(".")) {
            subStringIndex = file.getName().lastIndexOf('.');
            newExtension = file.getName().substring(subStringIndex + 1);
        }
        return newExtension;
    }

    private static int CreateDirectories(List<String> extensions) {
        for (String extension : extensions) {
            File dir = new File(Data.sortedFolderPath + extension);
            if (dir.exists()) {
                System.out.println("Dir exists");
            } else if (!Objects.equals(extension, "DS_Store") && !Objects.equals(extension, "localized")) {
                if (dir.mkdirs()) {
                    System.out.println("Directory was been created !");
                } else {
                    System.out.println("Error");
                    return (1);
                }
            }
        }
        File otherDir = new File(Data.sortedFolderPath + "other");
        if (!otherDir.exists()) {
            if (otherDir.mkdirs()) {
                System.out.println("Directory was been created !");
            } else {
                System.out.println("Error");
                return (1);
            }
        }
        return (0);
    }

    private static void MoveFileInDirectory(File[] fileArray) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        int subStringIndex;
        String targetExtension;

        for (File file : fileArray) {
            if (!file.getName().contains(".") && (!Objects.equals(file.getName(), "DS_Store")
                    && !Objects.equals(file.getName(), "localized"))) {
                try {
                    Path path = Path.of(file.getPath());
                    Files.move(path, Path.of(Data.sortedFolderPath + "other/" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.out.println(e);
                }
            } else if (!Objects.equals(file.getName(), ".DS_Store") && !Objects.equals(file.getName(), ".localized")) {
                String mimeType = fileNameMap.getContentTypeFor(file.getName());

                if (mimeType != null) {
                    subStringIndex = mimeType.lastIndexOf('/');
                    targetExtension = mimeType.substring(0, subStringIndex);
                } else {
                    subStringIndex = file.getName().lastIndexOf('.');
                    targetExtension = file.getName().substring(subStringIndex + 1);
                }
                try {
                    Path path = Path.of(file.getPath());
                    Files.move(path, Path.of(Data.sortedFolderPath + targetExtension + "/" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
