package pt.ul.fc.css.soccernow.mapper;

import org.mapstruct.*;
import pt.ul.fc.css.soccernow.domain.dto.games.CardInfoDTO;
import pt.ul.fc.css.soccernow.domain.entities.game.Card;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {
    @Mapping(source = "playerPlayer", target = "player")
    @Mapping(source = "playerScoredGoals", target = "player.scoredGoals")
    Card toEntity(CardInfoDTO cardInfoDTO);

    @InheritInverseConfiguration(name = "toEntity")
    CardInfoDTO toDto(Card card);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Card partialUpdate(CardInfoDTO cardInfoDTO, @MappingTarget Card card);
}