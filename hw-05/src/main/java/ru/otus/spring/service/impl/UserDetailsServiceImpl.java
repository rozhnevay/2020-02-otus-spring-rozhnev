package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.UserInfo;
import ru.otus.spring.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserInfo userInfo =
        userRepository
            .findUserByLoginIgnoreCase(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("Пользователь " + username + "не найден"));
    return userInfo;
  }
}
