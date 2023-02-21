package com.redsuelva.legalContract.pagoFacil.repository;

import com.redsuelva.legalContract.pagoFacil.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ContractRepostitory extends JpaRepository <Contract, Integer>{

    @Transactional
    public Contract findByDni(String dni);

}
