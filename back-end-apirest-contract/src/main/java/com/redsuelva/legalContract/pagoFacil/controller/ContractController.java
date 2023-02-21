package com.redsuelva.legalContract.pagoFacil.controller;

import com.redsuelva.legalContract.pagoFacil.dto.NewContract;
import com.redsuelva.legalContract.pagoFacil.entity.Contract;
import com.redsuelva.legalContract.pagoFacil.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Path http://localhost:8080/api/
@RestController
@RequestMapping("/api/contract")
public class ContractController {

    public final ResourceLoader resourceLoader;

    @Autowired
    ContractService contractService;

    public ContractController(ResourceLoader resourceLoader) {

        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveContracts(@RequestBody NewContract newContract) {

        Map<String, Object> response = new HashMap<>();
        String msg = contractService.saveContract(newContract);

        if (msg.equals("Contrado registrado exitosamente")) {
            response.put("ok:", true);
            response.put("msg:", msg);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("ok:", false);
            response.put("msg:", msg);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadContracts(@RequestParam List<MultipartFile> contracts) throws IOException {

        Map<String, Object> response = new HashMap<>();
        String msg = contractService.UploadContracts(contracts);

        if (msg.equals("Contratos cargados exitosamente")) {

            response.put("ok:", true);
            response.put("msg:", msg);
            response.put("data:", null);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("ok:", false);
            response.put("msg:", msg);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/admin_contracts")
    public ResponseEntity<Map<String, Object>> getContracts() {

        Map<String, Object> response = new HashMap<>();
        List<Contract> contracts = new ArrayList<Contract>();

        contractService.getContracts().forEach(contracts::add);

        response.put("ok", true);
        response.put("msg", "lista de contratos");
        response.put("data", contracts);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/showImgFront")
    public  HttpEntity<byte[]> showFront( HttpServletResponse response, @RequestParam String dni) throws IOException {

        File fileImg = new File("C:/Users/erickson.vergara/IdeaProjects/back-end-apirest-contract/imagenesCedula/"+dni+"front.jpg");
        //File fileImg = new File("/imagenesCedula/"+dni+"front.jpg");
        byte[] byteImg = null;
        HttpHeaders headers = null;

        try {
            byteImg = Files.readAllBytes(fileImg.toPath());
            headers = new HttpHeaders();

            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(byteImg.length);

        }catch(IOException e) {

            return null;
        }
        return new HttpEntity<byte[]>(byteImg,headers);
    }

    @GetMapping("/showImgLater")
    public  HttpEntity<byte[]> showLater( HttpServletResponse response, @RequestParam String dni) throws IOException {

        File fileImg = new File("C:/Users/erickson.vergara/IdeaProjects/back-end-apirest-contract/imagenesCedula/"+dni+"later.jpg");
        //File fileImg = new File("/imagenesCedula/"+dni+"later.jpg");

        byte[] byteImg = null;
        HttpHeaders headers = null;
        try {
            byteImg = Files.readAllBytes(fileImg.toPath());
            headers = new HttpHeaders();

            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(byteImg.length);

        }catch(IOException e) {

            return null;
        }

        return new HttpEntity<byte[]>(byteImg,headers);
    }

    @GetMapping("/showPerson")
    public  HttpEntity<byte[]> showPerson( HttpServletResponse response, @RequestParam String dni) throws IOException {

        File fileImg = new File("C:/Users/erickson.vergara/IdeaProjects/back-end-apirest-contract/imagenesPersona/"+dni+".jpg");
        //File fileImg = new File("/imagenesPersona/"+dni+".jpg");

        byte[] byteImg = null;
        HttpHeaders headers = null;
        try {
            byteImg = Files.readAllBytes(fileImg.toPath());
            headers = new HttpHeaders();

            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(byteImg.length);

        }catch(IOException e) {

            return null;
        }

        return new HttpEntity<byte[]>(byteImg,headers);
    }

    @GetMapping("/send_contract")
    public ResponseEntity<Map<String, Object>> sendContract( @RequestParam String dni) throws IOException {

        Map<String, Object> response = new HashMap<>();
        String  resp= contractService.sendContract(dni);
         if(resp.equals("No existe el archivo con la cedula ")){
             response.put("ok", false);
             response.put("msg", "Contracto no encontrado");
             response.put("data", resp + dni);
             return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
         }

        response.put("ok", true);
        response.put("msg", "Contrato enviado");
        response.put("data", resp);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/accept")
    public ResponseEntity<Map<String, Object>> validateContrac( @RequestParam String dni, @RequestParam boolean isAccept) throws IOException {
    	
        Map<String, Object> response = new HashMap<>();
        String  resp= contractService.verifyDocument(dni, isAccept);
        if(resp.equals("Los datos cumple las condicciones")){
            response.put("ok", false);
            response.put("msg", "datos no validos");
            response.put("data", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(resp.equals("Este contrato no tiene todos los datos necesarios para ser aceptado")){
            response.put("ok", false);
            response.put("msg", resp);
            response.put("data", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }


        response.put("ok", true);
        response.put("msg", resp);
        response.put("data", null);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
