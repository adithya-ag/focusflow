package com.test.focusflow.Service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class Killexe {


    public static void main() {
        Timer timer = new Timer();

        // Schedule a task to run every 10 seconds.
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Ask the user for the app's path to close.
                System.out.println("Enter the path of the app to close (e.g., C:\\Path\\To\\App.exe):");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                try {
//                    String appPath = reader.readLine();
                    String path = "Notepad.exe";
//                    String path = "GitHubDesktop.exe";
                    String appPath = path;
                    // Execute the tasklist command to list running processes.
                    Process process = Runtime.getRuntime().exec("tasklist");
                    BufferedReader taskListReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;

                    // Check each line to find the app to close.
                    while ((line = taskListReader.readLine()) != null) {
                        System.out.println("Closed the app at path: " + line);
                        if (line.contains(appPath)) {
                            String[] parts = line.split("\\s+");
                            if (parts.length >= 2) {
                                String pid = parts[1];
                                // Kill the process by PID.
                                killProcess(pid);
                                System.out.println("Closed the app at path: " + appPath);
                            }
                        }
                    }

                    // Close the readers.
                    taskListReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2000); // Run every 2 seconds.
    }

    private static void killProcess(String pid) {
        try {
            // Execute the taskkill command to terminate the process by PID.
            Process process = Runtime.getRuntime().exec("taskkill /F /PID " + pid);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
