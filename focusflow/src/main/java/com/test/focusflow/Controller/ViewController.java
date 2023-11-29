package com.test.focusflow.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ViewController {

    @GetMapping("/view")
    public ModelAndView viewFile(@ModelAttribute("filePath") String filePath) throws IOException {
        // Check if the file path is valid
        if (filePath == null || filePath.isEmpty()) {
            return new ModelAndView("error", "message", "Invalid file path");
        }

        // Get the file name and extension
        File file = new File(filePath);
        String fileName = file.getName();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

        // Check if the file exists and is readable
        Path path = Paths.get(filePath);
        if (!Files.exists(path) || !Files.isReadable(path)) {
            return new ModelAndView("error", "message", "File not found or not accessible");
        }

        // Create a model and view object with the file name and path as model attributes
        ModelAndView mav = new ModelAndView("view");
        mav.addObject("fileName", fileName);
        mav.addObject("filePath", filePath);

        // Delete the temporary file after displaying it
        file.deleteOnExit();

        // Return the model and view object
        return mav;
    }
}