//package spring.in.action.taco.cloud.security;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
//import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Configuration
//@ConditionalOnProperty(prefix = "security.oauth2", name = "enable", havingValue = "true", matchIfMissing = false)
//@PropertySource("classpath:oauth2.properties")
//public class PreviousOAuth2SocialLogin {
//
//    @Value("${spring.security.oauth2.client.registration.taco-admin-client.client-id}")
//    private String clientId;
//    @Value("${spring.security.oauth2.client.registration.taco-admin-client.client-secret}")
//    private String clientSecret;
//    @Value("${spring.security.oauth2.client.registration.taco-admin-client.provider}")
//    private String provider;
//
//    @Bean
//    @Order(2)
//    public SecurityFilterChain oauth2FilterChain(HttpSecurity http) throws Exception {
//        http
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/design")
//                        .clientRegistrationRepository(clientRegistrationRepository())
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userAuthoritiesMapper(userAuthoritiesMapper())));
//        return http.build();
//    }
//
//    @Bean
//    public OAuth2AuthorizedClientService authorizedClientService() {
//        return new InMemoryOAuth2AuthorizedClientService(
//                clientRegistrationRepository());
//    }
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        ClientRegistration registration = getRegistration(provider);
//        return new InMemoryClientRegistrationRepository(/*registration*/);
//    }
//
//    private ClientRegistration getRegistration(String client) {
//        if (client.equals("google")) {
//            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
//                                              .clientId(clientId).clientSecret(clientSecret).build();
//        }
//        if (client.equals("okta")) {
//            return CommonOAuth2Provider.OKTA.getBuilder(client)
//                                            .clientId(clientId).clientSecret(clientSecret).build();
//        }
//        if (client.equals("github")) {
//            return CommonOAuth2Provider.GITHUB.getBuilder(client)
//                                              .clientId(clientId).clientSecret(clientSecret).build();
//        }
//        if (client.equals("facebook")) {
//            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
//                                                .clientId(clientId).clientSecret(clientSecret).build();
//        }
//        return null;
//    }
//
//    @Bean
//    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
//        return (authorities) -> {
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//            // below is Spring doc's example of GrantedAuthoritiesMapper bean implementation
//            // however, I seem to only need the above "ROLE_USER" authority for my application,
//            // but I'm leaving the following source code for future reference
//            /*
//            authorities.forEach(authority -> {
//                if (OidcUserAuthority.class.isInstance(authority)) {
//                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
//
//                    OidcIdToken idToken = oidcUserAuthority.getIdToken();
//                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
//
//                    // Map the claims found in idToken and/or userInfo
//                    // to one or more GrantedAuthority's and add it to mappedAuthorities
//
//                } else if (OAuth2UserAuthority.class.isInstance(authority)) {
//                    OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;
//
//                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
//
//                    // Map the attributes found in userAttributes
//                    // to one or more GrantedAuthority's and add it to mappedAuthorities
//
//                }
//            });
//            */
//            return mappedAuthorities;
//        };
//    }
//}
