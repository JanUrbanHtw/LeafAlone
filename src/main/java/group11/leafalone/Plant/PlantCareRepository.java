package group11.leafalone.Plant;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantCareRepository extends CrudRepository<PlantCare, Long> {
    PlantCare findByScientific(String scientific);
}
