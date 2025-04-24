package pt.ul.fc.css.soccernow.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import pt.ul.fc.css.soccernow.domain.dto.games.PlayerGameStatsDTO;
import pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerGameStatsMapper {
    PlayerGameStats toEntity(PlayerGameStatsDTO playerGameStatsDTO);
}
