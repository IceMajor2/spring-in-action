package spring.in.action.taco.cloud.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.in.action.taco.cloud.data.TacoOrderRepository;
import spring.in.action.taco.cloud.data.TacoRepository;
import spring.in.action.taco.cloud.domain.Taco;

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
}
