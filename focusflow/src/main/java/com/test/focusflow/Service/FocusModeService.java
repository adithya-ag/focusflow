package com.test.focusflow.Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FocusModeService {

    public void disableApp(String exePath) {
        FileAttributeUtils.setReadOnly(exePath, true);
        System.out.println("Disabled " + exePath);
    }

    public void enableApp(String exePath) {
        FileAttributeUtils.setReadOnly(exePath, false);
    }

}