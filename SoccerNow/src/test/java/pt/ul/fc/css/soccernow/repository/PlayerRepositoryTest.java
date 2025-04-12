package pt.ul.fc.css.soccernow.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.ul.fc.css.soccernow.PlayerTestDataUtil;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PlayerRepositoryTest {

  @Autowired private PlayerRepository underTest;

  @Test
  public void testThatPlayerCanBeCreatedAndRecalled() {
    Player testPlayer = PlayerTestDataUtil.getPlayers().get(0);
    Player savedPlayer = underTest.save(testPlayer);

    assert savedPlayer.getName().equals(testPlayer.getName())
        && savedPlayer.getPreferredPosition().equals(testPlayer.getPreferredPosition())
        && savedPlayer.getId() != null;

    Optional<Player> recalledPlayer = underTest.findById(savedPlayer.getId());
    assert recalledPlayer.isPresent();
    assert recalledPlayer.get().equals(savedPlayer);
  }

  @Test
  public void testThatMultipleBooksCanBeCreatedAndRecalled() {
    Player playerA = PlayerTestDataUtil.getPlayers().get(0);
    Player playerB = PlayerTestDataUtil.getPlayers().get(1);
    Player playerC = PlayerTestDataUtil.getPlayers().get(2);

    underTest.saveAll(List.of(playerA, playerB, playerC));
    assertThat(underTest.findAll()).hasSize(3).containsExactly(playerA, playerB, playerC);
  }
}
