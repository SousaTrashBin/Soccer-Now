package pt.ul.fc.css.soccernow.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static pt.ul.fc.css.soccernow.PlayerTestDataUtil.getPlayers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PlayerServiceIntegrationTest {
    @Autowired
    private PlayerService underTest;

    @Test
    public void testIfValidPlayerCanBeAdded() {
        Player player = getPlayers().get(0);
        Player addedPlayer = underTest.add(player);

        assert addedPlayer.getId() != null
               && addedPlayer.getName()
                             .equals(player.getName())
               && Objects.equals(addedPlayer.getPreferredPosition(), player.getPreferredPosition());

        underTest.findById(addedPlayer.getId());
    }

    @Test
    public void testIfPlayerCanBeRemoved() {
        Player player = getPlayers().get(0);
        Player addedPlayer = underTest.add(player);
        underTest.softDelete(addedPlayer.getId());

        assertThrowsExactly(ResourceDoesNotExistException.class, () -> underTest.findNotDeletedById(addedPlayer.getId()));
    }

    @Test
    public void testIfFindAllWorks() {
        Player playerA = getPlayers().get(0);
        Player playerB = getPlayers().get(1);
        Player playerC = getPlayers().get(2);

        List<Player> players = List.of(playerA, playerB, playerC);
        List<Player> savedPlayers = players.stream()
                                           .map(underTest::add)
                                           .toList();
        assert underTest.findAllNotDeleted()
                        .size() == 3;

        underTest.softDelete(savedPlayers.get(0)
                                         .getId());
        assert underTest.findAllNotDeleted()
                        .size() == 2;
    }

    @Test
    public void testIfPlayerCanBeUpdated() {
        Player player = getPlayers().get(0);
        Player addedPlayer = underTest.add(player);

        Player newPlayerInfo = new Player();
        newPlayerInfo.setId(addedPlayer.getId());
        newPlayerInfo.setName("UPDATED");
        newPlayerInfo.setPreferredPosition(FutsalPositionEnum.SWEEPER);
        Player updatedPlayer = underTest.update(newPlayerInfo);

        assert updatedPlayer.getName()
                            .equals("UPDATED")
               && updatedPlayer.getPreferredPosition()
                               .equals(FutsalPositionEnum.SWEEPER);
    }
}
