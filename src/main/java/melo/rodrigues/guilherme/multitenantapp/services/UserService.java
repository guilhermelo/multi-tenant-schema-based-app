package melo.rodrigues.guilherme.multitenantapp.services;

import melo.rodrigues.guilherme.multitenantapp.entities.User;
import melo.rodrigues.guilherme.multitenantapp.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final TenantService tenantService;

    public UserService(UserRepository repository, TenantService tenantService) {
        this.repository = repository;
        this.tenantService = tenantService;
    }

    public User createUser(User user) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = repository.save(user);
        tenantService.initDatabase(user.getUsername());
        return savedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usuario = repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return usuario;
    }
}
