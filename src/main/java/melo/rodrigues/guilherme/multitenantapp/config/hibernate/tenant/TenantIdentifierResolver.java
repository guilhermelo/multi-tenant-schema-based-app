package melo.rodrigues.guilherme.multitenantapp.config.hibernate.tenant;

import java.security.Principal;
import java.util.Optional;
import java.util.function.Predicate;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    public static final String DEFAULT_TENANT = "default";

    @Override
    public String resolveCurrentTenantIdentifier() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .filter(Predicate.not(authentication -> authentication instanceof AnonymousAuthenticationToken))
            .map(Principal::getName)
            .orElse(DEFAULT_TENANT);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
