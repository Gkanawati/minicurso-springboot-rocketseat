package com.gkanawati.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gkanawati.todolist.user.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    var servletPath = request.getServletPath();

    if (servletPath.startsWith("/tasks")) {
      var authorization = request.getHeader("Authorization");
      var authEncoded = authorization.substring("Basic".length()).trim();

      byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

      var authDecodedString = new String(authDecoded);

      String[] credentials = authDecodedString.split(":");
      String username = credentials[0];
      String password = credentials[1];

      var user = this.userRepository.findByUsername(username);
      if (user == null) {
        response.sendError(401);
        return;
      }

      var passwordVerified = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray());
      if (passwordVerified.verified) {
        request.setAttribute("userId", user.getId());
        filterChain.doFilter(request, response);
      } else {
        response.sendError(401);
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }
}
