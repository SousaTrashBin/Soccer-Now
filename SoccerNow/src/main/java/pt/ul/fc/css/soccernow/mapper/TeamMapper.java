package pt.ul.fc.css.soccernow.mapper;

import org.mapstruct.*;
import pt.ul.fc.css.soccernow.domain.dto.TeamDTO;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;
import pt.ul.fc.css.soccernow.domain.entities.tournament.point.PointTournament;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamMapper {
    Team toEntity(TeamDTO teamDTO);

    @AfterMapping
    default void linkGameTeams(@MappingTarget Team team) {
        team.getGameTeams().forEach(gameTeam -> gameTeam.setTeam(team));
    }

    TeamDTO toDTO(Team team);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Team partialUpdate(TeamDTO teamDTO, @MappingTarget Team team);

    default Tournament createTournament() {
        return new PointTournament();
    }
}
