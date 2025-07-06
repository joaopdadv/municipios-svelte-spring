package br.com.municipios.service;

import br.com.municipios.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;

}