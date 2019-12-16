package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlantRepository extends CrudRepository<Plant, Long> {

    List<Plant> findByLeafAloneUser(LeafAloneUser leafAloneUser);

    @Query(nativeQuery = true, value = "SELECT * FROM user_plant WHERE user_id = :user_id AND name = :name")
    Optional<Plant> findByName(@Param("user_id") Long user_id, @Param("name") String name);

    default List<String> findNamesByLeafAloneUser (LeafAloneUser leafAloneUser) {
        List<String> names = new ArrayList<>();
        for(Plant plant : this.findByLeafAloneUser(leafAloneUser)) {
            names.add(plant.getName());
        }
        return names;
    }

    @Query(value="SELECT * FROM user_plant WHERE user_id= :user_id ORDER BY id ASC", nativeQuery = true)
    List<Plant> findByLeafAloneUserOrdered(@Param("user_id") Long user_id);
}
