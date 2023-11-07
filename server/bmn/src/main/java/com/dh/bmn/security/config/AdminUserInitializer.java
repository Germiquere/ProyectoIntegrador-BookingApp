package com.dh.bmn.security.config;

import com.dh.bmn.entity.Usuario;
import com.dh.bmn.repositories.IUsuarioRepository;
import com.dh.bmn.security.user.Rol;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminUserInitializer {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createInitialAdminUser() {
        if (usuarioRepository.count() == 0) {
            Usuario adminUser = new Usuario();
            adminUser.setNombre("Admin");
            adminUser.setApellido("Admin");
            adminUser.setEmail("admin@example.com");
            String rawPassword = "contraseña";
            String encodedPassword = passwordEncoder.encode(rawPassword);
            adminUser.setPassword(encodedPassword);
            adminUser.setRol(Rol.ADMIN);
            usuarioRepository.save(adminUser);
        }
    }
}

