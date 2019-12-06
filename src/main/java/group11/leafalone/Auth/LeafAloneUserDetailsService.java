package group11.leafalone.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class LeafAloneUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        LeafAloneUser leafAloneUser = userRepository.findByUsername(username);
        if (leafAloneUser == null) {
            throw new UsernameNotFoundException(username);
        } else {
            GrantedAuthority authority = new SimpleGrantedAuthority(leafAloneUser.getRole());
            List<GrantedAuthority> authorityList = new LinkedList<>();
            authorityList.add(authority);

            return new org.springframework.security.core.userdetails.User(username, leafAloneUser.getPassword(), true, true, true, true, authorityList);
        }
        //return new LeafAloneUserPrincipal(leafAloneUser);
    }

    public LeafAloneUser loadLAUserbyUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
