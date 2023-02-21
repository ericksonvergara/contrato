package com.redsuelva.legalContract.pagoFacil.serviceImpl;

import com.redsuelva.legalContract.pagoFacil.dto.NewContract;
import com.redsuelva.legalContract.pagoFacil.entity.Contract;
import com.redsuelva.legalContract.pagoFacil.repository.ContractRepostitory;
import com.redsuelva.legalContract.pagoFacil.security.entity.Users;
import com.redsuelva.legalContract.pagoFacil.security.service.UserService;
import com.redsuelva.legalContract.pagoFacil.service.ContractService;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class ContractServiceImpl  implements ContractService {

    //private final Path rootContratos = Paths.get("/var/proyecto/backend-contrato/Contratos");
    private final Path rootContratos = Paths.get("Contratos");
    //private final Path rootImagesCedula = Paths.get("/var/proyecto/backend-contrato/imagenesCedula");
    private final Path rootImagesCedula = Paths.get("imagenesCedula");
    //private final Path rootImagesPerson= Paths.get("/var/proyecto/backend-contrato/imagenesPersona");
    private final Path rootImagesPerson= Paths.get("imagenesPersona");


    @Autowired
    ContractRepostitory contractRepostitory;

    @Autowired
    UserService userService;


    @Override
    public Contract findBydni(String dni) {

        Contract contract =contractRepostitory.findByDni(dni);
        if(contract !=null){
            System.out.println(contract.toString());
        }

        return contract;
    }



    @Override
    public String UploadContracts(List<MultipartFile> contracts) throws IOException {

        try {
            if(!Files.exists(rootContratos)){
                Files.createDirectories(rootContratos);
            }
        }catch (Exception io){
            System.err.println("error en la lectura: " + io.getMessage());
            return "Error al carga los archivos";
        }


        for(int i=0; i<contracts.size(); i++) {

            String fileName = contracts.get(i).getOriginalFilename();

            if(!isPdf(fileName)){
                System.out.println("La extension del archivo no es valida"+ "\n");
                return  "Algunos de los archivos seleccionados no cumple con el formato";
            }

            if (!validateStringNumeric(fileName)) {
                System.out.println("el formato tiene letras" + "\n");
                return  "Algunos de los archivos seleccionados no cumple con el formato";
            }

        }

        for(int i=0; i<contracts.size(); i++){

            String fileName= contracts.get(i).getOriginalFilename();
            String dni =convertDni(fileName);
            Contract contract = new Contract();
            contract.setContract(fileName);
            contract.setDni(dni);
            Files.copy(contracts.get(i).getInputStream(), this.rootContratos.resolve(contracts.get(i).getOriginalFilename()));
            contractRepostitory.save(contract);

        }

        this.generateHTMLFromPDF("");

        return "Contratos cargados exitosamente";
    }


    @Override
    public String saveContract(NewContract newContract) {

        Users user = userService.findByDni(newContract.getDni());
        if( user != null){
            String contractFile =user.getDni()+".pfd";
            Contract contract = new Contract(user.getDni(), contractFile, user.getNames(), user.getSurNames(),user.getPhone(),
                                             newContract.getPathImgDniFront(), newContract.getPathImgDniLater(),
                                             newContract.getPathImgPeson(), newContract.getDateAccept(),user.getExpeditionDate());


          //carga de imagenes cedula
            if (!Files.exists(rootImagesCedula)) {
                try {
                    Files.createDirectories(rootImagesCedula);
                } catch (IOException e) {
                    System.out.print("error al crear la carpeta images Cedula");
                    e.printStackTrace();
                }
            }

            if (!Files.exists(rootImagesPerson)) {
                try {
                    Files.createDirectories(rootImagesPerson);
                } catch (IOException e) {
                    System.out.print("error al crear la carpeta images Persona");
                    e.printStackTrace();
                }
            }

            Path pathImages = this.rootImagesCedula.getFileName();
            Path pathImagesPerson = this.rootImagesPerson.getFileName();
            String ruta = pathImages.toFile().getAbsolutePath();
            String rutaPerson = pathImagesPerson.toFile().getAbsolutePath();
            System.out.println(ruta+"__________________________________\n");
            System.out.println(rutaPerson+"___________________________________\n");
            try {

                byte[] imgDniFront = Base64.getDecoder().decode(newContract.getPathImgDniFront());
                byte[] imgDniLater = Base64.getDecoder().decode(newContract.getPathImgDniLater());
                byte[] imgPerson= Base64.getDecoder().decode(newContract.getPathImgPeson());
                Path baseDniFront = Paths.get(ruta + "/" + user.getDni() + "front.jpg");
                Path baseDniLater = Paths.get(ruta + "/" + user.getDni() + "later.jpg");
                Path basePerson = Paths.get(rutaPerson + "/" + user.getDni() + ".jpg");
                System.out.println(baseDniFront+"_____________________\n");
                System.out.println(baseDniLater+"_______________\n");
                System.out.println(basePerson+"__________________________\n");


                Files.write(baseDniFront, imgDniFront);
                Files.write(baseDniLater, imgDniLater);
                Files.write(basePerson, imgPerson);
                contract.setPathImgDniFront(baseDniFront.toString());
                contract.setPathImgDniLater(baseDniLater.toString());
                contract.setPathImgPerson(basePerson.toString());
                System.out.println(contract.getPathImgDniFront()+"\n");

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("excepcion______>\n");
            }
            contract.setAccept(false);
            contractRepostitory.save(contract);
            return  "Contrado registrado exitosamente";

        }else{
            return "Algo salio mal";
        }
    }

    @Override
    public List<Contract> getContracts() {
    	try {
    		this.generateHTMLFromPDF("");
            
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return contractRepostitory.findAll();
    }

    @Override
    public String sendContract(String dni) throws IOException {

        org.apache.tomcat.util.codec.binary.Base64 base64 = new org.apache.tomcat.util.codec.binary.Base64();
        File file = new File("C:/Users/erickson.vergara/IdeaProjects/back-end-apirest-contract/contratos/"+dni+".pdf");
       // File file = new File("/var/proyecto/backend-contrato/Contratos/"+dni+".pdf");

        if(!file.exists()){
            return "No existe el archivo con la cedula ";
        }


        byte[] fileArray = new byte[(int) file.length()];
        InputStream inputStream;

        String encodedFile = "";
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(fileArray);
            encodedFile = base64.encodeToString(fileArray);
        } catch (Exception e) {
            // Manejar Error
        }
        return   encodedFile;
    }

    @Override
    public String verifyDocument(String dni, boolean isAccept) throws IOException {

        String msg = "Los datos cumple las condicciones";
        Contract contract = findBydni(dni);
        if (contract != null) {
            System.out.println(isAccept);
            if (contract.getPathImgDniFront() == null || contract.getPathImgDniLater() == null || contract.getPathImgPerson() == null) {
                return "Este contrato no tiene todos los datos necesarios para ser aceptado";
            }

            if (isAccept) {
               Date today = new Date();
                contract.setAccept(isAccept);
                contract.setDateCheck(today);
                contractRepostitory.save(contract);
                return "Información validada";
            } else {
                Files.delete(this.rootImagesCedula.resolve(contract.getPathImgDniFront()));
                Files.delete(this.rootImagesCedula.resolve(contract.getPathImgDniLater()));
                Files.delete(this.rootImagesCedula.resolve(contract.getPathImgPerson()));
                contract.setPathImgDniFront(null);
                contract.setPathImgDniLater(null);
                contract.setPathImgPerson(null);
                contractRepostitory.save(contract);
                 return "Los datos cumple las condicciones";
            }

        }
        return "Los datos cumple las condicciones";
    }




    public Boolean validateStringNumeric(String fileName){

        String format = "";
        format = fileName.substring(0,fileName.indexOf("."));
        for (int i = 0; i < format.length(); i++) {
            if (!Character.isDigit(format.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean isPdf(String fileName){

        String format = "";
        if (fileName.contains(".")){
            format = fileName.substring(fileName.lastIndexOf(".")+1);
            return  format.equals("pdf");
        }
        return  false;
    }

    public String convertDni(String fileName){
        String dni="";
        dni=fileName.substring(0,fileName.indexOf("."));
        return  dni;
    }
    
    public void generateHTMLFromPDF(String filename) throws IOException {
    	try {
    		filename = "C:/Users/erickson.vergara/IdeaProjects/back-end-apirest-contract/Contratos/1090176036.pdf";
//    		PDDocument pdf = PDDocument.load(new File(filename));
//            Writer output = new PrintWriter("C:/Users/erickson.vergara/IdeaProjects/back-end-apirest-contract/Contratos/pdf.html", "utf-8");
//            new PDFDomTree().writeText(pdf, output);
            
//            output.close();	
            System.out.print("termiando el metodo_______");
            
            File f = new File(filename);
            String parsedText;
            PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
            parser.parse();
            
            COSDocument cosDoc = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
//            parsedText = parsedText.replaceAll("XXX", "Erickson Vergara");
            parsedText = parsedText.replaceFirst("XXX", "Erickson Vergara");
            System.out.print(parsedText);	
            
            PrintWriter pw = new PrintWriter("C:/Users/erickson.vergara/IdeaProjects/back-end-apirest-contract/Contratos/pdf.txt");
            pw.print(parsedText);
            pw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
    }


}

