package pt.ul.fc.css.soccernow.mapper;

import org.mapstruct.*;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;
import pt.ul.fc.css.soccernow.domain.entities.tournament.point.PointTournament;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerMapper {
    Player toEntity(PlayerDTO playerDTO);

    @AfterMapping
    default void linkPlayerGameStats(@MappingTarget Player player) {
        player.getPlayerGameStats().forEach(playerGameStat -> playerGameStat.setPlayer(player));
    }

    PlayerDTO toDTO(Player player);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Player partialUpdate(PlayerDTO playerDTO, @MappingTarget Player player);

    default Tournament createTournament() {
        return new PointTournament();
    }
}
