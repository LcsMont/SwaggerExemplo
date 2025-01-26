package com.sis.swaggerexemplo.controller;

import com.sis.swaggerexemplo.model.Atividade;
import com.sis.swaggerexemplo.repository.AtividadeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Operation(
            summary = "Listar",
            description = "Método que retorna todos os dados",
            tags = "Atividades",
            responses = {
                @ApiResponse(responseCode = "200", description = "Atividades listadas"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "404", description = "Atividades não encontradas"),
                @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    @GetMapping
    public ResponseEntity<List<Atividade>> listar() {
        List<Atividade> list = atividadeRepository.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(
            summary = "Listar por Id",
            description = "Método que retorna um registro",
            tags = "Atividades",
            responses = {
                @ApiResponse(responseCode = "200", description = "Atividade listada"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "404", description = "Atividade não encontrada"),
                @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Atividade> listarPorId(@PathVariable int id) {
        return atividadeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Salvar",
            description = "Método que salva um registro",
            tags = "Atividades",
            responses = {
                @ApiResponse(responseCode = "201", description = "Atividade criada"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    @PostMapping
    public ResponseEntity<Atividade> criar(@RequestBody Atividade atividade) {
        Atividade atv = atividadeRepository.save(atividade);
        return ResponseEntity.status(201).body(atv);
    }

    @Operation(
            summary = "Alterar",
            description = "Método que altera um registro",
            tags = "Atividades",
            responses = {
                @ApiResponse(responseCode = "200", description = "Atividade atualizada com sucesso"),
                @ApiResponse(responseCode = "404", description = "Atividade não encontrada"),
                @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
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

    @Operation(
            summary = "Deletar",
            description = "Método que apaga um registro",
            tags = "Atividades",
            responses = {
                @ApiResponse(responseCode = "204", description = "Atividade deletada com sucesso"),
                @ApiResponse(responseCode = "404", description = "Atividade não encontrada"),
                @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (atividadeRepository.existsById(id)) {
            atividadeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
