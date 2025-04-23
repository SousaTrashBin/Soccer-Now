package pt.ul.fc.css.soccernow.mapper;

import org.mapstruct.*;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerMapper {
    @Mapping(source = "playerStats", target = "playerGameStats")
    Player toEntity(PlayerDTO playerDTO);

    @AfterMapping
    default void linkPlayerGameStats(@MappingTarget Player player) {
        player.getPlayerGameStats().forEach(playerGameStat -> playerGameStat.setPlayer(player));
    }

    @Mapping(source = "playerGameStats", target = "playerStats")
    PlayerDTO toDTO(Player player);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "playerStats", target = "playerGameStats")
    Player partialUpdate(PlayerDTO playerDTO, @MappingTarget Player player);
}
