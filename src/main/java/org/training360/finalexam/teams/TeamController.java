package org.training360.finalexam.teams;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.training360.finalexam.players.CreatePlayerCommand;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamDTO> listAllTeams() {
        return teamService.listAllTeams();
    }

    @PostMapping
    public TeamDTO createNewTeam(@RequestBody @Valid CreateTeamCommand command) {
        return teamService.createNewTeam(command);
    }

    @PostMapping("/{id}/players")
    public TeamDTO addNewPlayerToTeam(@PathVariable(name = "id")long id, @RequestBody CreatePlayerCommand command) {
        return teamService.addNewPlayerToTeam(id,command);
    }

    @PutMapping("/{id}/players")
    public TeamDTO addExistingPlayerToTeam(@PathVariable(name = "id")long id, @RequestBody UpdateWithExistingPlayerCommand command) {
        return teamService.addExistingPlayerToTeam(id, command);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(EntityNotFoundException enfe) {
        Problem problem =
                Problem.builder()
                        .withType(URI.create("teams/not-found")).withTitle("Team Not found by id").withStatus(Status.NOT_FOUND)
                        .withDetail(enfe.getMessage()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> handleNotValidParameters(MethodArgumentNotValidException manve) {
        Problem problem =
                Problem.builder()
                        .withType(URI.create("teams/bad-request")).withTitle("Can not create team with given parameters").withStatus(Status.BAD_REQUEST)
                        .withDetail(manve.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> handleNotValidRequest(IllegalArgumentException iae) {
        Problem problem =
                Problem.builder()
                        .withType(URI.create("teams/bad-request")).withTitle("Can not create team with given parameters").withStatus(Status.BAD_REQUEST)
                        .withDetail(iae.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
