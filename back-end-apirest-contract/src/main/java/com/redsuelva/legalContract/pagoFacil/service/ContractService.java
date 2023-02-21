package com.redsuelva.legalContract.pagoFacil.service;

import com.redsuelva.legalContract.pagoFacil.dto.NewContract;
import com.redsuelva.legalContract.pagoFacil.entity.Contract;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ContractService {

  public Contract findBydni(String dni);
  public String UploadContracts(List<MultipartFile> contracts) throws IOException;

  public String saveContract(NewContract newContract);

  public List<Contract> getContracts();

  public String sendContract(String dni) throws IOException;

  public String verifyDocument(String dni, boolean isAccept) throws IOException;

}
