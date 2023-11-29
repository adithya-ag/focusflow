package com.test.focusflow.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    // Define the path where the uploaded files will be saved temporarily
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    @PostMapping("/upload")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // Check if the file is empty
        if (file.isEmpty()) {
            return new ModelAndView("error", "message", "Please select a file to upload");
        }

        // Get the file name and extension
        String fileName = file.getOriginalFilename();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

        // Check if the file is a supported document type
        if (!fileExt.equalsIgnoreCase("pdf") && !fileExt.equalsIgnoreCase("ppt") && !fileExt.equalsIgnoreCase("pptx")) {
            return new ModelAndView("error", "message", "Unsupported file type. Only pdf and ppt files are allowed");
        }

        // Create a temporary file with a unique name
        File tempFile = File.createTempFile("document-", "." + fileExt, new File(TEMP_DIR));

        // Copy the file contents to the temporary file
        Path tempPath = Paths.get(tempFile.getAbsolutePath());
        Files.copy(file.getInputStream(), tempPath);

        // Redirect to the document viewer controller with the file path as a model attribute
        return new ModelAndView("redirect:/view", "filePath", tempPath.toString());
    }
}