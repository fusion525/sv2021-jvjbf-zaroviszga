package org.training360.finalexam.players;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class PlayerService {

    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;
    private PlayerRepository playerRepository;

    public PlayerService(ModelMapper modelMapper, ObjectMapper objectMapper, PlayerRepository playerRepository) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.playerRepository = playerRepository;
    }


    public List<PlayerDTO> listAllPlayers() {

        Type targetListType = new TypeToken<List<PlayerDTO>>(){}.getType();

        return modelMapper.map(playerRepository.findAll(),targetListType);
    }

    public PlayerDTO createNewPlayer(CreatePlayerCommand command) {
        Player newPlayer = new Player(command.getName(),command.getBirthDate(),
                command.getPosition());

        playerRepository.save(newPlayer);

        return modelMapper.map(newPlayer, PlayerDTO.class);
    }

    public void deletePlayerById(long id) {
        playerRepository.deleteById(id);
    }
}
