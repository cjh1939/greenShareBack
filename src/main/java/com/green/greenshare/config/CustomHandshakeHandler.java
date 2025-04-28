package com.green.greenshare.config;

import com.green.greenshare.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomHandshakeHandler extends DefaultHandshakeHandler {
  private final JwtUtil jwtUtil;



  @Override
  protected Principal determineUser(
          ServerHttpRequest request,
          WebSocketHandler wsHandler,
          Map<String, Object> attributes
  ) {
    String userEmail = (String) attributes.get("userEmail"); // ✅ 여기! token 말고 userEmail
    if (userEmail != null) {
      return new StompPrincipal(userEmail);
    }
    return null;
  }

}