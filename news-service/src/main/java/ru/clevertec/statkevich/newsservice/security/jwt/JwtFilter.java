package ru.clevertec.statkevich.newsservice.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.clevertec.statkevich.newsservice.dto.user.UserAuthorityDto;
import ru.clevertec.statkevich.newsservice.security.client.ValidationClient;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

/**
 * Security filter for api requests.
 */
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final ValidationClient validationClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();

        UserAuthorityDto userAuthorityDto = validationClient.validate(token);
        String username = userAuthorityDto.username();
        String authority = userAuthorityDto.authority();

        UserDetails userDetails = new JwtUserDetails(username, authority);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
