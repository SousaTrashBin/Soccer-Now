package pt.ul.fc.css.soccernow.mapper;

import org.mapstruct.*;
import pt.ul.fc.css.soccernow.domain.dto.tournament.TournamentDTO;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;
import pt.ul.fc.css.soccernow.domain.entities.tournament.point.PointTournament;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TournamentMapper {
    Tournament toEntity(TournamentDTO tournamentDTO);

    @AfterMapping
    default void linkGames(@MappingTarget Tournament tournament) {
        tournament.getGames().forEach(game -> game.setTournament(tournament));
    }

    TournamentDTO toDTO(Tournament tournament);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tournament partialUpdate(TournamentDTO tournamentDTO, @MappingTarget Tournament tournament);

    default Tournament createTournament() {
        return new PointTournament();
    }
}
