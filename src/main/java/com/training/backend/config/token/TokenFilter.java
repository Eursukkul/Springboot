package com.training.backend.config.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.training.backend.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenFilter extends GenericFilterBean {   // สร้าง filter ทุก request ของ Spring

    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;   // Servlet request convert to HTTPServlet
        String authorization = request.getHeader("Authorization");  // check Header name Authorization
        if (ObjectUtils.isEmpty(authorization)) {
            filterChain.doFilter(servletRequest, servletResponse);     // ไม่มีออกไปเลย
            return;
        }

        if (!authorization.startsWith("Bearer ")) {                     // check Authorization ประเภท Bearer ถ้ามีทำงานต่อ ถ้าไม่มีออกเป็นเลย
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // ใช้ Substring
        String token = authorization.substring(7); // ดึง token มา verify ถ้า verify ผ่าน จะได้ JWT ที่ decoded แล้วเพื่อใช้งานต่อ
        DecodedJWT decoded = tokenService.verify(token);

        if (decoded == null) {  // ถ้า decode เป็นออกจากฟังชั่น
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // user id
        // if ture
        String principal = decoded.getClaim("principal").asString();
        String role = decoded.getClaim("role").asString();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        // ดึงค่าออกเป็น AuthenticationToken // UserID , password(credentials) , สิทธิของ user
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, "(protected)", authorities);

        // set authentication security ว่า authentication login เข้ามา
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
