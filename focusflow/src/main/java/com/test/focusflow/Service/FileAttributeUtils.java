package com.test.focusflow.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;

public class FileAttributeUtils {

    public static void setReadOnly(String filePath, boolean readOnly) {
        try {
            Path path = FileSystems.getDefault().getPath(filePath);
            DosFileAttributeView dosView = Files.getFileAttributeView(path, DosFileAttributeView.class);

            if (dosView != null) {
                DosFileAttributes dosAttributes = dosView.readAttributes();
                dosView.setReadOnly(readOnly);
            } else {
                throw new UnsupportedOperationException("DosFileAttributeView is not supported on this file system.");
            }
        } catch (IOException e) {
            // Handle the IOException (e.g., log the error or notify the user)
            e.printStackTrace();
        }
    }
}
