package com.security.security.config;


import com.security.security.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

//Criado só para dizer se é válido ou não
public class TokenUtil {
   //Tem que imporar o Authentication direto do core do security
   //Tudo classe do security por enquanto

    //algumas constantes utilitarias
    //Emissor devolve quem enviou o token, ou seja na hora de descriptografar ele vai devolver o nome, interessante para inserir mais informações
    private static final String EMISSOR = "Rudge";
    //Passando no BEARER, só para concatenar com o token
    private static final String TOKEN_HEADER = "Bearer ";

    //Chave do token
    private static final String TOKEN_KEY = "01234567890123456789012345678901";

    //Tempo de expiração
    private static final long UM_Segundo = 1000;
    private static final long UM_MINUTO = 60 * UM_Segundo; //Duração do token de 1 minuto


    //Aqui codifica para gerar o token
    public static AuthToken encodeToken(Usuario u){
        //vai transformar o TOKEN_KEY EM UMA CHAVE DE CRIPTOGRAFIA
        Key secretKey = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes()); //Aqui passamos a chave secreta que nós criamos/ escolhemos ou geramos em qualquer lugar
        //tem que ser uma string
        String tokenJWT = Jwts.builder() //aqui começa as coisas do jwt
                .setSubject(u.getLogin()) //ele vai setar o login
                .setIssuer(EMISSOR) //associado ao emissor
                .setExpiration(new Date(System.currentTimeMillis() + UM_MINUTO)) //seta a sua expiração
                .signWith(secretKey, SignatureAlgorithm.HS256) //Aqui ele associa a chave secreta da gente e criptografa tudo para HS256
                .compact();
        AuthToken token = new AuthToken(TOKEN_HEADER + tokenJWT); // aqui ele concatena o token com o header
        return token;

    };

    //Aqui que ele vai validar o acesso do "token"
    public static Authentication decodeToken(HttpServletRequest request){
      try{
          String jwtToken = request.getHeader("Authorization");
          jwtToken = jwtToken.replace(TOKEN_HEADER,"");

          //Agora vamos extrair as informações do token

          //Aqui é feito a leitura do token
          Jws<Claims> jwsClaims = Jwts.parserBuilder()
                  .setSigningKey(TOKEN_KEY.getBytes())
                  .build().parseClaimsJws(jwtToken);

          //Começando a extrair as informações
          String usuario = jwsClaims.getBody().getSubject();
          String emissor = jwsClaims.getBody().getIssuer();
          Date validade = jwsClaims.getBody().getExpiration();
          System.out.println(emissor);
          //testando
          if(usuario.length() > 0 && emissor.equals(EMISSOR) && validade.after(new Date(System.currentTimeMillis())) ){

              //if(request.getHeader("Authorization").equals("Bearer Rudge123")){ implementação antes do jwt


              System.out.println(request.getHeader("Authorization"));
              return new UsernamePasswordAuthenticationToken("user",null, Collections.emptyList());
              //Onde tem user, poderia ser o nome do usuário etc
              //onde tem null é passado as credenciais, Admin, usuário padrão blabla
              //Na collection é passado os endpoints
          }

      }
      catch (Exception e){
          System.out.println("DEBUG - Error ao decodificar o token");
          System.out.println(e.getMessage());
      }
        return null;
    }
}
