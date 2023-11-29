package com.test.focusflow.Controller;

//import com.test.focusflow.Service.ChatService;
import com.test.focusflow.Service.CommunicationService;
import com.test.focusflow.Service.FocusModeService;
import com.test.focusflow.Service.Killexe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.MimeTypeUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class MainController {


    private final CommunicationService communicationService;

    @Autowired
    public MainController(CommunicationService communicationService) {
        this.communicationService = communicationService;
    }
    @Autowired
    private Killexe killexe;


    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/full")
    public String fullscreen() {
        return "fullscreen1";
    }

    @RequestMapping(value = "/exec", method = {RequestMethod.GET, RequestMethod.POST})
    public String exec() {
        Logger log = Logger.getLogger("MyLogger");
        log.info("exec");
        killexe.main();
        return "fullscreen1";
    }

    @GetMapping("/openDocument")
    public String openDocumentPage() {
        return "docView";
    }


    @PostMapping("/openDocument")
    public String openDocument(@RequestParam("document") MultipartFile document, Model model) throws IOException {
        Logger log = Logger.getLogger("MyLogger");
        log.info("Selected document: " + document.getOriginalFilename());

        byte[] documentContent = document.getBytes();
        model.addAttribute("documentContent", documentContent);

        return "docView";
    }

    @GetMapping("/viewDocument")
    public ResponseEntity<byte[]> viewDocument(Model model) {
        byte[] documentContent = (byte[]) model.getAttribute("documentContent");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "document.pdf");

        return new ResponseEntity<>(documentContent, headers, HttpStatus.OK);
    }

    @GetMapping("/docs")
    public ResponseEntity<InputStreamResource> getDoc(String doc) throws IOException {

        File file = new File("/path/to/user/documents/" + doc);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + file.getName());

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }




//    @RequestMapping(value = "/communication", method = {RequestMethod.GET, RequestMethod.POST})
//    public String loadpage(Model model) {
//        model.addAttribute("receivedMessages", communicationService.getReceivedMessages());
//        return "communication";
//    }
//    @GetMapping("/communication")
//    public String communicationPage(Model model) {
//        model.addAttribute("receivedMessages", communicationService.getReceivedMessages());
//        return "communication";
//    }
//
//    @PostMapping("/communication")
//    public String sendMessage(String message) {
//        communicationService.sendMessage(message);
//        return "redirect:/communication";
//    }

    // CommunicationController class
//    @Autowired
//    private CommunicationService communicationService;

    @GetMapping("/communicate")
    public String communicatePage(Model model) {
        model.addAttribute("receivedMessages", communicationService.getReceivedMessages());
        return "communicate";
    }

    @PostMapping("/send-message")
    public String sendMessage(@RequestParam String message) {
        InetAddress broadcastAddress;
        try {
            broadcastAddress = communicationService.getBroadcastAddress();
        } catch (SocketException e) {
            e.printStackTrace();
            // Handle the exception as needed
            return "error-page"; // You might want to redirect to an error page
        }

        communicationService.sendMessage(message, broadcastAddress);

        return "redirect:/communicate";
    }

}