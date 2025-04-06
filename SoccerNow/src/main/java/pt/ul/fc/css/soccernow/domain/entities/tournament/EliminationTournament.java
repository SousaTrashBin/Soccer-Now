package pt.ul.fc.css.soccernow.domain.entities.tournament;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EliminationTournament")
public class EliminationTournament extends Tournament {}
