package com.sis.swaggerexemplo.controller;

import com.sis.swaggerexemplo.model.Atividade;
import com.sis.swaggerexemplo.repository.AtividadeRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Operation(summary = "Listar", description = "Método que retorna todos os dados", tags = "Atividades")
    @GetMapping
    public List<Atividade> listar() {
        return atividadeRepository.findAll();
    }

    @Operation(summary = "Listar por Id", description = "Método que retorna um registro", tags = "Atividades")
    @GetMapping("/{id}")
    public ResponseEntity<Atividade> listarPorId(@PathVariable int id) {
        return atividadeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar", description = "Método que salva um registro", tags = "Atividades")
    @PostMapping
    public Atividade criar(@RequestBody Atividade atividade) {
        return atividadeRepository.save(atividade);
    }

    @Operation(summary = "Alterar", description = "Método que altera um registro", tags = "Atividades")
    @PutMapping("/{id}")
    public ResponseEntity<Atividade> atualizar(@PathVariable int id, @RequestBody Atividade atividadeAtualizada) {
        return atividadeRepository.findById(id)
                .map(atividade -> {
                    atividade.setNome(atividadeAtualizada.getNome());
                    atividade.setDescricao(atividadeAtualizada.getDescricao());
                    atividade.setPreco(atividadeAtualizada.getPreco());
                    Atividade atualizado = atividadeRepository.save(atividade);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar", description = "Método que apaga um registro", tags = "Atividades")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (atividadeRepository.existsById(id)) {
            atividadeRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}
