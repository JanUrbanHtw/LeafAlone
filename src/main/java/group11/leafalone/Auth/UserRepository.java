package group11.leafalone.Auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<LeafAloneUser, Long> {

    LeafAloneUser findByUsername(String username);

}