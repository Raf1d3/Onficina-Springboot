package web.onficina.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer -> configurer
                        // Qualquer um pode fazer requisições para essas URLs
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/", "/home.html", "/login", "/cadastrar").permitAll()
                        // Um usuário autenticado e com o papel ADMIN pode fazer requisições para essas
                        // URLs
                        .requestMatchers("/veiculo/**").hasAnyRole("cliente", "admin")
                        .requestMatchers("/manutencao/**").hasAnyRole("cliente", "admin")
                        .requestMatchers("/oficina/**").hasAnyRole("cliente", "admin")
                        .requestMatchers("/perfil/**").hasAnyRole("cliente", "admin")
                        .requestMatchers("/avaliacao/**").hasAnyRole("cliente", "admin")
                        .requestMatchers("/usuario/alterar/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/usuario/alterar").authenticated()
                        .requestMatchers("/usuario/**").hasRole("admin")
                         .requestMatchers("/relatorios/**").hasAnyRole("cliente", "admin")
                          .requestMatchers("/relatorios/**").hasAnyRole("cliente", "admin")

                        // .requestMatchers("URL").hasAnyRole("ADMIN", "USUARIO")
                        .anyRequest().authenticated())
                        
                .formLogin(form -> form
                        // Uma página de login customizada
                        .loginPage("/login")
                        .usernameParameter("email")
                        // Define a URL para onde o usuário será redirecionado após o login
                        //.defaultSuccessUrl("/painel.html")
                        .successHandler(authenticationSuccessHandler)
                        // Define a URL para o caso de falha no login
                        // .failureUrl("/login-error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(
                                (request, response, authentication) -> response.addHeader("HX-Redirect", "/"))
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("select email, senha, ativo from usuario where email = ?");

        manager.setAuthoritiesByUsernameQuery(
                "SELECT u.email, 'ROLE_' || p.nome " +
                "FROM usuario u " +
                "INNER JOIN papel p ON u.id_papel = p.id " +
                "WHERE u.email = ?");
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
		// return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
		
		// Usar um PasswordEncoder que aceite todos os esquemas disponiveis no Spring Security
		// mas escolhendo quais vamos aceitar. As senhas comecam dizendo qual o esquema usado {noop}, {bcrypt}, {argon2}
		String idEnconder = "argon2";
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("noop", NoOpPasswordEncoder.getInstance());
//		encoders.put("bcrypt", new BCryptPasswordEncoder());
//		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
//		encoders.put("scrypt", new SCryptPasswordEncoder());
//		encoders.put("sha256", new StandardPasswordEncoder());
		PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idEnconder, encoders);
		return passwordEncoder;
	}

}
