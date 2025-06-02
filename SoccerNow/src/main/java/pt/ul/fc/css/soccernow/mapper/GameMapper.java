package pt.ul.fc.css.soccernow.mapper;

import org.mapstruct.*;
import pt.ul.fc.css.soccernow.domain.dto.games.GameDTO;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = TournamentMapper.class)
public interface GameMapper {
    Game toEntity(GameDTO gameDTO);

    GameDTO toDTO(Game game);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Game partialUpdate(GameDTO gameDTO, @MappingTarget Game game);
}
