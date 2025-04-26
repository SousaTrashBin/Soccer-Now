package pt.ul.fc.css.soccernow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.mapper.TeamMapper;
import pt.ul.fc.css.soccernow.utils.TeamTestDataUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static pt.ul.fc.css.soccernow.utils.PlayerTestDataUtil.getPlayers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TeamServiceIntegrationTest {
    List<Player> players;

    @Autowired
    private TeamService underTest;

    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamMapper teamMapper;

    @BeforeEach
    void setUp() {
        players = getPlayers();
        players = players.stream().map(playerService::add).toList();
    }

    @Test
    @Transactional
    public void testIfTeamCanBeCreatedAndRecalled() {
        Set<Player> teamPlayers = players.stream().limit(10)
                .collect(Collectors.toSet());
        Team randomTeam = TeamTestDataUtil.createRandomTeam();
        Team savedTeam = underTest.add(randomTeam);
        assert savedTeam.getPlayers().stream().allMatch(player -> player.hasTeam(savedTeam));
        assert underTest.findNotDeletedById(savedTeam.getId()).equals(savedTeam);
        teamPlayers.forEach(player -> underTest.addPlayerToTeam(player, savedTeam));
        System.out.println(teamMapper.toDTO(savedTeam));
    }

    @Test
    @Transactional
    public void testIfTeamCannnotBeCreated() {
        Player player = new Player();
        player.setId(UUID.randomUUID());
        Set<Player> teamPlayers = Set.of(player);
        Team randomTeam = TeamTestDataUtil.createRandomTeam();
        assertThrows(ResourceDoesNotExistException.class, () -> underTest.add(randomTeam));
    }

    @Test
    @Transactional
    public void testIfTeamCanBeUpdatedAndRecalled() {
        Set<Player> teamPlayers = players.stream().limit(10)
                .collect(Collectors.toSet());
        Team randomTeam = TeamTestDataUtil.createRandomTeam();
        Team savedTeam = underTest.add(randomTeam);

        Team teamToUpdate = new Team();
        teamToUpdate.setId(savedTeam.getId());
        teamToUpdate.setPlayers(players.stream().skip(9).limit(10).collect(Collectors.toSet()));
        teamToUpdate.setName("UPDATED");

        Team update = underTest.update(teamToUpdate);
        assert update.getPlayers().stream().allMatch(player -> player.hasTeam(savedTeam))
                && update.getName().equals("UPDATED");

    }

    @Test
    public void testIfFindAllWorks() {
        Set<Player> teamPlayers = players.stream().limit(10)
                .collect(Collectors.toSet());
        List<Team> savedTeams = new ArrayList<>();
        IntStream.range(0, 3).forEach(index -> {
            Team randomTeam = TeamTestDataUtil.createRandomTeam();
            savedTeams.add(underTest.add(randomTeam));
        });
        assert underTest.findAllNotDeleted().size() == 3;
        underTest.softDelete(savedTeams.get(0).getId());
        assert underTest.findAllNotDeleted().size() == 2;
    }
}
