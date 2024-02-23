package selex.intern.test.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import selex.intern.test.model.Role;
import selex.intern.test.model.UserEntity;
import selex.intern.test.repository.UserRepository;
import selex.intern.test.shared.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(Message.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }
    //Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
    private Collection<GrantedAuthority> mapRolesToAuthorities(Role role) {
        List<String> roles = new ArrayList<>();
        roles.add(role.name());

        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r))
                .collect(Collectors.toList());
    }
}
