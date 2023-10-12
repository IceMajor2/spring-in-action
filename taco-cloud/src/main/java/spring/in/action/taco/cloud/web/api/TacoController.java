package spring.in.action.taco.cloud.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.in.action.taco.cloud.data.TacoOrderRepository;
import spring.in.action.taco.cloud.data.TacoRepository;
import spring.in.action.taco.cloud.domain.Taco;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tacos",
        produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
@RequiredArgsConstructor
public class TacoController {

    private final TacoRepository tacoRepository;

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending()
        );
        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optionalTaco = tacoRepository.findById(id);
        if (optionalTaco.isPresent())
            return ResponseEntity.ok(optionalTaco.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }

    // TODO: write mappings for PUT, PATCH & DELETE HTTP requests
}
