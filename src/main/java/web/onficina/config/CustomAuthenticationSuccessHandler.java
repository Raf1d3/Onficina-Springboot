package web.onficina.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        // Pega a coleção de papéis (autoridades) do usuário autenticado.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Itera sobre os papéis para decidir para onde redirecionar.
        for (GrantedAuthority grantedAuthority : authorities) {
            
            // Se o usuário tem o papel 'ROLE_ADMIN'
            // Lembre-se que o Spring Security adiciona o prefixo "ROLE_"
            if (grantedAuthority.getAuthority().equals("ROLE_admin")) {
                redirectStrategy.sendRedirect(request, response, "/usuario/listar");
                return; // Encerra a execução após o redirect
            } 
            // Se o usuário tem o papel 'ROLE_CLIENTE'
            else if (grantedAuthority.getAuthority().equals("ROLE_cliente")) {
                redirectStrategy.sendRedirect(request, response, "/painel"); // ou /veiculo/listar, etc.
                return;
            }
        }

        // Se o usuário não tiver nenhum papel conhecido, redireciona para uma página padrão.
        redirectStrategy.sendRedirect(request, response, "/");
    }
}
