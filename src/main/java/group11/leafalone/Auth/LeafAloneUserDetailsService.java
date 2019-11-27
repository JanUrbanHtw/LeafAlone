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
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }else{
                GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
                List<GrantedAuthority> authorityList = new LinkedList<>();
                authorityList.add(authority);

                return new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true, authorityList);
        }
        //return new LeafAloneUserPrincipal(user);
    }
}
