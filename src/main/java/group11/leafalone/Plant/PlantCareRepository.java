package group11.leafalone.Plant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantCareRepository extends CrudRepository<PlantCare, Long> {
    PlantCare findByScientific(String scientific);

    @Query(value="SELECT * FROM plant_care WHERE contributor_id = :user_id ORDER BY id ASC", nativeQuery = true)
    List<PlantCare> findByLeafAloneUserOrdered(@Param("user_id") Long user_id);
}
