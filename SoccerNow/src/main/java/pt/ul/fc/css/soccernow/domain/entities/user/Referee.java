package pt.ul.fc.css.soccernow.domain.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Referee")
public class Referee extends User {
  @Column(name = "HAS_CERTIFICATE", nullable = false)
  private Boolean hasCertificate = false;

  public Boolean getHasCertificate() {
    return hasCertificate;
  }

  public void setHasCertificate(Boolean hasCertificate) {
    this.hasCertificate = hasCertificate;
  }
}
