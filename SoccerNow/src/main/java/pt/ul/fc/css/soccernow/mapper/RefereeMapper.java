package pt.ul.fc.css.soccernow.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeDTO;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RefereeMapper {
    Referee toEntity(RefereeDTO refereeDTO);

    RefereeDTO toDTO(Referee referee);
}
