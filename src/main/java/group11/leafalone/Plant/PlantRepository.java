package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends CrudRepository<Plant, Long> {

    List<Plant> findByLeafAloneUser(LeafAloneUser leafAloneUser);
}
