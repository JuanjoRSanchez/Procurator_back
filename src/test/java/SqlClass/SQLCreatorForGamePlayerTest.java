package SqlClass;

import com.example.procurator.Repository.CollectiveRepository;
import com.example.procurator.Repository.GameRepository;
import com.example.procurator.Repository.Game_PlayerRepository;
import com.example.procurator.Repository.UserRepository;
import com.example.procurator.model.Collective;
import com.example.procurator.model.Game;
import com.example.procurator.model.Game_Player;
import com.example.procurator.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.procurator.model.Role.MANAGER;
import static com.example.procurator.model.Role.PLAYER;
@DataJpaTest
@AllArgsConstructor
public class SQLCreatorForGamePlayerTest {


    @Autowired
    private GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private CollectiveRepository collectiveRepository;

    @Autowired
    private Game_PlayerRepository game_playerRepository;

    @Autowired
    TestEntityManager entityManager;

    public void initializeUsersManagerSQLDataBase(){
        User user = new User();
        user.setName("Juan");
        user.setEmail("juan@gmail.com");
        user.setCreationDate(new Date());
        user.setAge(32);
        user.setAddress("Calle los Cobos");
        user.setPhone("555 45 55 55");
        user.setRole(MANAGER);
        user.setPassword("juan");
        entityManager.persist(user);
    }
    public void initializeCollectivesSQLDataBase(){
        Collective collective = new Collective();
        collective.setName("Los Carracas");
        collective.setCreationDate(new Date());
        collective.setUser(userRepository.findById(1L).orElseThrow());
        // collective.setUsers(userRepository.findAllPlayers().stream().collect(Collectors.toSet()));
        entityManager.persist(collective);

    }
    public void initializePlayersToCollectiveSQLDataBase(){
        User user0 = new User();
        user0.setName("Pedro");
        user0.setEmail("pedro@gmail.com");
        user0.setCreationDate(new Date());
        user0.setAge(32);
        user0.setAddress("Calle los Cobos");
        user0.setPhone("555 55 55 55");
        user0.setRole(PLAYER);
        user0.setPassword("pedro");
        user0.setCollective(collectiveRepository.findById(1L).orElseThrow());
        entityManager.persist(user0);

        User user01 = new User();
        user01.setName("Carlos");
        user01.setEmail("carlos@gmail.com");
        user01.setCreationDate(new Date());
        user01.setAge(32);
        user01.setAddress("Calle los Cobos");
        user01.setPhone("555 55 55 53");
        user01.setRole(PLAYER);
        user01.setPassword("carlos");
        user01.setCollective(collectiveRepository.findById(1L).orElseThrow());
        entityManager.persist(user01);

    }
    public void initializeGameSQLDataBase(){
        Game game = new Game();
        game.setBlackScore(10);
        game.setWhiteScore(8);
        game.setCreationDate(new Date());
        game.setCollective(collectiveRepository.findById(1L).orElseThrow());
        entityManager.persist(game);

    }

    public void initializePlayersToGameSQLDataBase(){
        Game_Player game_player = new Game_Player();
        game_player.setGame(gameRepository.findById(1L).orElseThrow());
        game_player.setUser(userRepository.findById(1L).orElseThrow());
        game_player.setAddedToGame(true);
        game_player.setRate(10);
        game_player.setAddedToGame(true);
        entityManager.persist(game_player);

    }


    /*
    public void initializeSQLDataBase(){
        User user = new User();
        user.setName("Pedro");
        user.setEmail("pedro@gmail.com");
        user.setCreationDate(new Date());
        user.setAge(32);
        user.setAddress("Calle los Cobos");
        user.setPhone("555 55 55 55");
        user.setRole(PLAYER);
        user.setPassword("pedro");
        userRepository.save(user);
    }
*/
    /*
        public void initializeCollectivesSQLDataBase(){
            Collective collective = new Collective();
            collective.setName("Los Carracas");
            collective.setCreationDate(new Date());
            collective.setUser(userRepository.findById(1L).orElseThrow());
            collective.setUsers((Set<User>) userRepository.findAllPlayers());
            collectiveRepository.save(collective);

        }

        public void initializeGameSQLDataBase(){
            Game game = new Game();
            game.setBlackScore(10);
            game.setWhiteScore(8);
            game.setCreationDate(new Date());
            game.setCollective(collectiveRepository.findById(1L).orElseThrow());
            gameRepository.save(game);

        }

        public void initializePlayersToGameSQLDataBase(){
            Game_Player game_player = new Game_Player();
            game_player.setGame(gameRepository.findById(1L).orElseThrow());
            game_player.setUser(userRepository.findById(1L).orElseThrow());
            game_player.setAddedToGame(true);
            game_player.setRate(10);
            game_player.setAddedToGame(true);
            game_playerRepository.save(game_player);

        }


        public void initializeUsersSQLDataBase(){
            User user = new User();
            user.setName("Juan");
            user.setEmail("juan@gmail.com");
            user.setCreationDate(new Date());
            user.setAge(32);
            user.setAddress("Calle los Cobos");
            user.setPhone("555 45 55 55");
            user.setRole(MANAGER);
            user.setPassword("juan");
            userRepository.save(user);

            User user0 = new User();
            user0.setName("Pedro");
            user0.setEmail("pedro@gmail.com");
            user0.setCreationDate(new Date());
            user0.setAge(32);
            user0.setAddress("Calle los Cobos");
            user0.setPhone("555 55 55 55");
            user0.setRole(PLAYER);
            user0.setPassword("pedro");
            userRepository.save(user0);

            User user01 = new User();
            user01.setName("Carlos");
            user01.setEmail("carlos@gmail.com");
            user01.setCreationDate(new Date());
            user01.setAge(32);
            user01.setAddress("Calle los Cobos");
            user01.setPhone("555 55 55 53");
            user01.setRole(PLAYER);
            user01.setPassword("carlos");
            userRepository.save(user01);
        }
     */
}
