package com.sis.swaggerexemplo.controller;

import com.sis.swaggerexemplo.model.Atividade;
import com.sis.swaggerexemplo.repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @GetMapping
    public List<Atividade> listar() {
        return atividadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atividade> listarPorId(@PathVariable int id) {
        return atividadeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Atividade criar(@RequestBody Atividade atividade) {
        return atividadeRepository.save(atividade);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (atividadeRepository.existsById(id)) {
            atividadeRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}
