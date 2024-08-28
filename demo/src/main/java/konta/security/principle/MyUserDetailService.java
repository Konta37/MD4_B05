package konta.security.principle;

import konta.model.Users;
import konta.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("username (mail) not found"));
        return MyUserDetails.builder().
                users(u).
                authorities(u.getRoles().stream().map(roles -> new SimpleGrantedAuthority(roles.getRoleName().toString())).toList()).
                build();
    }
}
