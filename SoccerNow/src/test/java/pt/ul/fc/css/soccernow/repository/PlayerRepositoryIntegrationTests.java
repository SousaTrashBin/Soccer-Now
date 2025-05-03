package pt.ul.fc.css.soccernow.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.utils.PlayerTestDataUtil;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static pt.ul.fc.css.soccernow.utils.UserTestDataUtil.SEED;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PlayerRepositoryIntegrationTests {

    @Autowired
    private PlayerRepository underTest;

    @Test
    public void testThatPlayerCanBeCreatedAndRecalled() {
        Player testPlayer = PlayerTestDataUtil.getPlayers(new Random(SEED))
                                              .get(0);
        Player savedPlayer = underTest.save(testPlayer);

        assert savedPlayer.getName()
                          .equals(testPlayer.getName())
                && savedPlayer.getPreferredPosition() == testPlayer.getPreferredPosition()
               && savedPlayer.getId() != null;

        Optional<Player> recalledPlayer = underTest.findById(savedPlayer.getId());
        assert recalledPlayer.isPresent();
        assert recalledPlayer.get()
                             .equals(savedPlayer);
    }

    @Test
    public void testThatMultiplePlayersCanBeCreatedAndRecalled() {
        List<Player> players = PlayerTestDataUtil.getPlayers(new Random(SEED));
        Player playerA = players.get(0);
        Player playerB = players.get(1);
        Player playerC = players.get(2);

        underTest.saveAll(List.of(playerA, playerB, playerC));
        assertThat(underTest.findAll()).hasSize(3)
                                       .containsExactly(playerA, playerB, playerC);

        playerA.delete();
        underTest.save(playerA);

        assertThat(underTest.findAllNotDeleted()).hasSize(2)
                                                 .containsExactly(playerB, playerC);
    }

    @Test
    public void testThatPlayerCanBeUpdated() {
        Player player = PlayerTestDataUtil.getPlayers(new Random(SEED)).get(0);
        underTest.save(player);

        player.setName("UPDATED");
        underTest.save(player);

        UUID playerId = player.getId();
        Optional<Player> result = underTest.findById(playerId);
        assertThat(result).isPresent();

        assertThat(result.get())
                .matches(
                        resultPlayer ->
                                resultPlayer.getName()
                                            .equals("UPDATED")
                                && resultPlayer.getPreferredPosition()
                                               .equals(player.getPreferredPosition())
                                && resultPlayer.getId()
                                               .equals(playerId));
    }
}
