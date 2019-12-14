package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlantRepository extends CrudRepository<Plant, Long> {

    List<Plant> findByLeafAloneUser(LeafAloneUser leafAloneUser);
    Optional<Plant> findByName(String name);

    default List<String> findNamesByLeafAloneUser (LeafAloneUser leafAloneUser) {
        List<String> names = new ArrayList<>();
        for(Plant plant : this.findByLeafAloneUser(leafAloneUser)) {
            names.add(plant.getName());
        }
        return names;
    }

    @Query(value="SELECT * FROM user_plant ORDER BY id ASC", nativeQuery = true)
    List<Plant> findByLeafAloneUserOrdered(LeafAloneUser user);
}
