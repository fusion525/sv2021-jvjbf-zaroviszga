package org.training360.finalexam.teams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.training360.finalexam.players.CreatePlayerCommand;
import org.training360.finalexam.players.Player;
import org.training360.finalexam.players.PlayerRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;
    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;

    public TeamService(ModelMapper modelMapper, ObjectMapper objectMapper, TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public List<TeamDTO> listAllTeams() {
        Type targetListType = new TypeToken<List<TeamDTO>>(){}.getType();

        return modelMapper.map(teamRepository.findAll(),targetListType);
    }

    public TeamDTO createNewTeam(CreateTeamCommand command) {
        Team newTeam = new Team(command.getName());

        return modelMapper.map(newTeam, TeamDTO.class);
    }

    public TeamDTO addNewPlayerToTeam(long id, CreatePlayerCommand command) {
        Team team = teamRepository.getById(id);

        Player player = new Player(command.getName(),command.getBirthDate(),command.getPosition());
        playerRepository.save(player);

        team.getPlayers().add(player);
        teamRepository.save(team);

        return modelMapper.map(team,TeamDTO.class);
    }

    public TeamDTO addExistingPlayerToTeam(long id, UpdateWithExistingPlayerCommand command) {
        Player player = playerRepository.getById(command.getPlayerId());

        Team team = teamRepository.getById(id);

        if (player.getTeam() != null) {
            throw new IllegalArgumentException("Player already has a team");
        }

        List<Boolean> playersOnPosition = team.getPlayers().stream().map(p-> p.getPosition().equals(player.getPosition()))
                .collect(Collectors.toList());

        if (playersOnPosition.size() > 2) {
            throw new IllegalArgumentException("Team already has two players for this position");
        }

        player.setTeam(team);
        team.getPlayers().add(player);
        playerRepository.save(player);
        teamRepository.save(team);

        return modelMapper.map(team,TeamDTO.class);

    }
}
