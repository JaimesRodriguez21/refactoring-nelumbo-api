package com.nelumbo.api.auth;

import com.nelumbo.api.dto.request.LoginRequest;
import com.nelumbo.api.dto.request.RegisterRequest;
import com.nelumbo.api.dto.response.AuthResponse;
import com.nelumbo.api.entity.Rol;
import com.nelumbo.api.entity.Usuario;
import com.nelumbo.api.jwt.JwtService;
import com.nelumbo.api.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
           UserDetails user = userRepository.findByEmail(request.getEmail()).orElseThrow();
           String token = jwtService.getToken(user);
           return AuthResponse.builder()
                   .token(token)
                   .build();
    }

   /* public AuthResponse register(RegisterRequest request) {
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .password(passwordEncoder.encode(request.getPass()))
                .username(request.getEmail())
                .rol(Rol.ADMIN)
                .build();

        userRepository.save(usuario);

        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();

    }
*/
}