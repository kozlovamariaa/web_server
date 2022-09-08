package com.springapp.controllers;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.springapp.model.User;
import com.springapp.dao.UserDAO;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/login1")
public class UserController  {
    @Value("${SECRET}")
    private String secret;

    @Autowired
    UserDAO userDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserController(){
    }
    @PostMapping()
    public String login(@RequestParam("email") String email, @RequestParam("password") String pwd) throws UnsupportedEncodingException {
        System.out.println("user controller");
        User selectedUser = userDAO.getByEmail(email);
        if(passwordEncoder.matches(pwd, selectedUser.getPassword())){
            String token = getJWTToken(email);
            selectedUser.setToken(token);
            userDAO.saveUserToken(selectedUser.getId(), selectedUser.getToken());
            return selectedUser.getToken();
        }
        else {
            return null;
        }
    }

    private String getJWTToken(String username) throws UnsupportedEncodingException {
        System.out.println(secret.toString() + " secret");
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(key)
                .compact();
        return "Bearer " + token;
    }
}
