package group11.leafalone.Auth;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<LeafAloneUser, Long> {

    LeafAloneUser findByUsername(String username);

    LeafAloneUser findByEmail(String email); //TODO apparently works already?

}