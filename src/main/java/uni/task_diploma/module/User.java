package uni.task_diploma.module;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class User implements UserDetails {

    private final String name;

    // Коллекция тех прав, которые есть у пользователя (роли)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));

    }

    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public String getUsername() {
        return getName();
    }

    // Аккаунт действительный
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Аккаунт не заблокированный
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Пароль не просрочен
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Аккаунт работает(включён)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
