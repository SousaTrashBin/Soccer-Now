package pt.ul.fc.css.soccernow.mapper;

import org.mapstruct.*;
import pt.ul.fc.css.soccernow.domain.dto.games.PlayerGameStatsDTO;
import pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;
import pt.ul.fc.css.soccernow.domain.entities.tournament.point.PointTournament;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerGameStatsMapper {
    PlayerGameStats toEntity(PlayerGameStatsDTO playerGameStatsDTO);

    PlayerGameStatsDTO toDTO(PlayerGameStats playerGameStats);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PlayerGameStats partialUpdate(PlayerGameStatsDTO playerGameStatsDTO, @MappingTarget PlayerGameStats playerGameStats);

    default Tournament createTournament() {
        return new PointTournament();
    }
}
