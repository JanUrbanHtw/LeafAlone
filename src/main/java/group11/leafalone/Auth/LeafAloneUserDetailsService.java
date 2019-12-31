package group11.leafalone.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.LinkedList;
import java.util.List;

@Service
public class LeafAloneUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LeafAloneUserDetailsService(){}

    public LeafAloneUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

    public LeafAloneUser getCurrentUser() {
        SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    public LeafAloneUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public BindingResult validateUser(LeafAloneUser user, BindingResult bindingResult) {
        if (!(user.getPassword().equals(user.getConfirmPassword()))) {
            FieldError error = new FieldError("user", "confirmPassword", "Required to be identical to password");
            bindingResult.addError(error);
        }
        LeafAloneUser repoUser = userRepository.findByUsername(user.getUsername());
        if (repoUser != null) {
            FieldError error = new FieldError("user", "username", "Username already taken");
            bindingResult.addError(error);
        }
        LeafAloneUser emailUser = userRepository.findByEmail(user.getEmail()); //TODO does this ever work
        if (emailUser != null) {
            FieldError error = new FieldError("user", "email", "Email already in use");
            bindingResult.addError(error);
        }
        return bindingResult;
    }

    public void save(LeafAloneUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
