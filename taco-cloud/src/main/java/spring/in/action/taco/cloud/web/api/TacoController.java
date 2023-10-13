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
    public Iterable<Taco> getRecent() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending()
        );
        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> getById(@PathVariable("id") Long id) {
        Optional<Taco> optionalTaco = tacoRepository.findById(id);
        if (optionalTaco.isPresent())
            return ResponseEntity.ok(optionalTaco.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco add(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Taco> replace(@PathVariable("id") Long id, @RequestBody Taco taco) {
        Optional<Taco> optionalTaco = tacoRepository.findById(id);
        if (optionalTaco.isEmpty())
            return ResponseEntity.notFound().build();
        taco.setId(id);
        return ResponseEntity.ok(tacoRepository.save(taco));
    }

    // TODO: write mappings for PATCH & DELETE HTTP requests
}
