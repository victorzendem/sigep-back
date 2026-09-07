package br.com.sigep.sigep.application.service;

import br.com.sigep.sigep.application.dto.setor.SetorRequest;
import br.com.sigep.sigep.application.dto.setor.SetorResponse;
import br.com.sigep.sigep.infraestructure.persistency.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository SETOR_REPOSITORY;

}



