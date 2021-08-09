package org.training360.finalexam.players;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<PlayerDTO> listAllPlayers() {
        return playerService.listAllPlayers();
    }

    @PostMapping
    public PlayerDTO createNewPlayer(@RequestBody @Valid CreatePlayerCommand command) {
        return playerService.createNewPlayer(command);
    }

    @DeleteMapping("/{id}")
    public void deletePlayerById(@PathVariable(name = "id") long id) {
        playerService.deletePlayerById(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> handleNotValidParameters(MethodArgumentNotValidException manve) {
        Problem problem =
                Problem.builder()
                        .withType(URI.create("api/player")).withTitle("Can not create player with given parameters").withStatus(Status.BAD_REQUEST)
                        .withDetail(manve.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(EntityNotFoundException enfe) {
        Problem problem =
                Problem.builder()
                        .withType(URI.create("player/not-found")).withTitle("Player Not found by id").withStatus(Status.NOT_FOUND)
                        .withDetail(enfe.getMessage()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
