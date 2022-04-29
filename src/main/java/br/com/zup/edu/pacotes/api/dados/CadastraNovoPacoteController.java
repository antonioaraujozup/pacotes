package br.com.zup.edu.pacotes.api.dados;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CadastraNovoPacoteController {

    private final PacoteRepository repository;

    public CadastraNovoPacoteController(PacoteRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/pacotes")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid NovoPacoteRequest request, UriComponentsBuilder uriComponentsBuilder) {

        CPF cpf = new CPF(request.getCpf());
        if (repository.existsByCpfHash(cpf.getHash())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "CPF já cadastrado");
        }

        Pacote pacote = request.toModel();

        repository.save(pacote);

        URI location = uriComponentsBuilder.path("/pacotes/{id}")
                .buildAndExpand(pacote.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> erros = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Map<String, Object> body = Map.of(
                "status", 422,
                "error", "Unprocessable Entity",
                "path", request.getDescription(false).replace("uri=", ""),
                "timestamp", LocalDateTime.now(),
                "message", "CPF já cadastrado"
        );

        return ResponseEntity.unprocessableEntity().body(body);
    }
}
