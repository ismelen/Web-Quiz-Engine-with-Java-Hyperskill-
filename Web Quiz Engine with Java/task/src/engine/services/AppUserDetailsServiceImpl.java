package engine.services;

import engine.domain.AppUser;
import engine.domain.AppUserAdapter;
import engine.repositories.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {
    private final AuthRepository repo;

    public AppUserDetailsServiceImpl(AuthRepository repo) {this.repo = repo;}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return new AppUserAdapter(user);
    }
}
