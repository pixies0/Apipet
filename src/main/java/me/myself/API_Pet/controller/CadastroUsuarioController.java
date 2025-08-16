package me.myself.API_Pet.controller;

import me.myself.API_Pet.facade.CadastroUsuarioFacade;
import me.myself.API_Pet.dto.UsuarioCadastroRequest;
import me.myself.API_Pet.dto.UsuarioCadastroResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/design/facade/usuarios")
public class CadastroUsuarioController {

    private final CadastroUsuarioFacade facade;

    public CadastroUsuarioController(CadastroUsuarioFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<UsuarioCadastroResponse> criar(@RequestBody UsuarioCadastroRequest req) {
        var resp = facade.cadastrar(req);
        return ResponseEntity.ok(resp);
    }
}
