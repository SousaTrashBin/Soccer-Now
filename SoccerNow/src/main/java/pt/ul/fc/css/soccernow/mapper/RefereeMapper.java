package pt.ul.fc.css.soccernow.mapper;

import org.mapstruct.*;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeDTO;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TournamentMapper.class, CardMapper.class})
public interface RefereeMapper {
    Referee toEntity(RefereeDTO refereeDTO);

    @AfterMapping
    default void linkPrimaryRefereeGames(@MappingTarget Referee referee) {
        referee.getPrimaryRefereeGames().forEach(primaryRefereeGame -> primaryRefereeGame.setPrimaryReferee(referee));
    }

    @AfterMapping
    default void linkIssuedCards(@MappingTarget Referee referee) {
        referee.getIssuedCards().forEach(issuedCard -> issuedCard.setReferee(referee));
    }

    RefereeDTO toDTO(Referee referee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Referee partialUpdate(RefereeDTO refereeDTO, @MappingTarget Referee referee);
}
