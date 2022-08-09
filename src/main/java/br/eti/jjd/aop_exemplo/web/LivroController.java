package br.eti.jjd.aop_exemplo.web;

import br.eti.jjd.aop_exemplo.model.Livro;
import br.eti.jjd.aop_exemplo.model.LivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LivroController {

    private final LivroRepository livroRepository;

    @GetMapping("/livros")
    public ResponseEntity<List<Livro>> listaContas() {
        return new ResponseEntity<>(livroRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/livros/{id}")
    public ResponseEntity<Livro> getContaById(@PathVariable("id") long id) {
        Optional<Livro> contaData = livroRepository.findById(id);
        return contaData.map(livro -> new ResponseEntity<>(livro, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/livros")
    public ResponseEntity<Livro> createConta(@RequestBody Livro livroDados) {

        try {
            Livro livro = livroRepository.save(
                    new Livro(
                            livroDados.getId(),
                            livroDados.getTitulo(),
                            livroDados.getPreco()
                            )
            );
            return new ResponseEntity<>(livro, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/livros/{id}")
    public ResponseEntity<Livro> updateConta(@PathVariable("id") long id, @RequestBody Livro livroDados) {
        Optional<Livro> conta = livroRepository.findById(id);
        if (conta.isPresent()) {
            Livro livroEncontrada = conta.get();
            livroEncontrada.setTitulo(livroDados.getTitulo());
            livroEncontrada.setPreco(livroDados.getPreco());
            return new ResponseEntity<>(livroRepository.save(livroEncontrada), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/livros/{id}")
    public ResponseEntity<HttpStatus> deleteConta(@PathVariable("id") long id) {
        try {
            livroRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/livros")
    public ResponseEntity<HttpStatus> deleteTudo() {
        try {
            livroRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
