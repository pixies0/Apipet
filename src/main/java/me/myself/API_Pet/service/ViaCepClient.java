package me.myself.API_Pet.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ViaCepClient {
    private final RestTemplate rt = new RestTemplate();
    public Map<String, Object> buscar(String cep) {
        return rt.getForObject("https://viacep.com.br/ws/{cep}/json/", Map.class, cep);
    }
}
