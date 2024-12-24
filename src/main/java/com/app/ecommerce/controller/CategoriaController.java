package com.app.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecommerce.model.Categoria;
import com.app.ecommerce.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(categoria -> ResponseEntity.ok(categoria))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/descricao/{desc}")
    public ResponseEntity<List<Categoria>> buscarPorDescricao(@PathVariable String desc) {
        return ResponseEntity.ok(categoriaRepository.findAllByDescricaoContainingIgnoreCase(desc));
    }

    @PostMapping("/cadastrar")
    public Categoria cadastrarCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Categoria> atualizarCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.findById(categoria.getCod_cat())  // Usando o ID do corpo da requisição
                .map(existingCategoria -> {
                    existingCategoria.setDescricao(categoria.getDescricao());
                    categoriaRepository.save(existingCategoria);
                    return ResponseEntity.ok(existingCategoria);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarCategoria(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoriaRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}