package pt.ul.fc.css.soccernow.domain.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CredentialsRepository extends JpaRepository<Credentials, UUID> {
    List<Credentials> findByUsername(String username);
}
