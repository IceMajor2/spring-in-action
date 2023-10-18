package spring.in.action.taco.cloud.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import spring.in.action.taco.cloud.data.UserRepository;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@PropertySource("classpath:oauth2.properties")
public class SecurityConfig {

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()))
                // SECURITY
                .csrf(CsrfConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                // RISK
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(mvc.pattern("/data-api/users")).denyAll()
                        .requestMatchers(mvc.pattern("/data-api/users/**")).denyAll()
                        .requestMatchers(mvc.pattern("/data-api/taco-orders")).denyAll()
                        .requestMatchers(mvc.pattern("/data-api/taco-orders/**")).denyAll()
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/data-api/tacos")).authenticated()
                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/data-api/tacos/**")).denyAll()
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/data-api/ingredients")).hasAuthority("SCOPE_writeIngredients")
                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/data-api/ingredients/**")).hasAuthority("SCOPE_deleteIngredients")
                        .requestMatchers(mvc.pattern("/design"), mvc.pattern("/orders")).hasRole("USER")
                        .requestMatchers(mvc.pattern("/"), mvc.pattern("/**")).permitAll()
                        .requestMatchers(toH2Console()).permitAll()
                        .anyRequest().permitAll())
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/design"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User '%s' not found".formatted(username)));
    }
}
