package nl.kmartin.parcelorganizer.api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> customCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildCorsConfiguration());

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));

        // Load CORS before OAuth2 filters and apply it to all endpoints.
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.addUrlPatterns("*");

        return bean;
    }

    private CorsConfiguration buildCorsConfiguration() {
        CorsConfiguration config = new CorsConfiguration() {
            @Override
            public String checkOrigin(String requestOrigin) {
                if (requestOrigin != null && isAllowedOrigin(requestOrigin)) {
                    return requestOrigin;
                }

                return null;
            }
        };

        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setExposedHeaders(Collections.singletonList("WWW-Authenticate"));

        return config;
    }

    private boolean isAllowedOrigin(String origin) {
        return origin.startsWith("http://localhost:") ||
                origin.startsWith("http://127.0.0.1:") ||
                origin.startsWith("http://192.168.1.") ||
                origin.equals("https://parcelorganizer.kmartin.nl");
    }
}