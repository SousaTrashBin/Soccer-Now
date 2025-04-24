package pt.ul.fc.css.soccernow.mapper;

import org.mapstruct.*;
import pt.ul.fc.css.soccernow.domain.dto.games.GameDTO;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GameMapper {
    Game toEntity(GameDTO gameDTO);

    @AfterMapping
    default void linkGameTeamOne(@MappingTarget Game game) {
        GameTeam gameTeamOne = game.getGameTeamOne();
        if (gameTeamOne != null) {
            gameTeamOne.setGame(game);
        }
    }

    @AfterMapping
    default void linkGameTeamTwo(@MappingTarget Game game) {
        GameTeam gameTeamTwo = game.getGameTeamTwo();
        if (gameTeamTwo != null) {
            gameTeamTwo.setGame(game);
        }
    }

    GameDTO toDTO(Game game);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Game partialUpdate(GameDTO gameDTO, @MappingTarget Game game);
}
