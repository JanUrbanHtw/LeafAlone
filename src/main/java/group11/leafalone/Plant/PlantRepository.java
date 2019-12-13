package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PlantRepository extends CrudRepository<Plant, Long> {

    List<Plant> findByLeafAloneUser(LeafAloneUser leafAloneUser);

    default List<String> findNamesByLeafAloneUser (LeafAloneUser leafAloneUser) {
        List<String> names = new ArrayList<>();
        for(Plant plant : this.findByLeafAloneUser(leafAloneUser)) {
            names.add(plant.getName());
        }
        return names;
    }
}
