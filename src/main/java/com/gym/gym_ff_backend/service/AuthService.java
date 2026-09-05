package com.gym.gym_ff_backend.service;

import com.gym.gym_ff_backend.dto.LoginRequest;
import com.gym.gym_ff_backend.dto.RegisterRequest;
import com.gym.gym_ff_backend.entity.Member;
import com.gym.gym_ff_backend.entity.Role;
import com.gym.gym_ff_backend.entity.User;
import com.gym.gym_ff_backend.repository.MemberRepository;
import com.gym.gym_ff_backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Email is already registered");
        }

        Member member = new Member();

        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setJoinDate(LocalDate.now());
        member.setActive(true);

        Member savedMember =
                memberRepository.save(member);

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.MEMBER);
        user.setEnabled(true);
        user.setMember(savedMember);

        userRepository.save(user);
    }

    public String login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user =
                userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "User not found"));

        return jwtService.generateToken(user);
    }
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"));
    }
    public User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return getUserByEmail(email);
    }
}