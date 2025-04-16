package pt.ul.fc.css.soccernow.service;

import static pt.ul.fc.css.soccernow.PlayerTestDataUtil.getPlayers;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PlayerServiceIntegrationTest {
  @Autowired private PlayerService underTest;

  @Test
  public void addIfValidPlayerCanBeAdded() {
    Player player = getPlayers().get(0);
    Player addedPlayer = underTest.add(player);

    assert addedPlayer.getId() != null
        && addedPlayer.getName().equals(player.getName())
        && Objects.equals(addedPlayer.getPreferredPosition(), player.getPreferredPosition());

    assert underTest.existsById(addedPlayer.getId());
  }
}
