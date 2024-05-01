package com.babyTalk.user.utils;


import com.alibaba.fastjson.JSON;
import com.babyTalk.base.http.ResponseStatus;
import com.babyTalk.base.utils.WrapperUtil;
import com.babyTalk.user.exception.JWTException;
import com.babyTalk.user.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Setter
@Getter
//@Component
//@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {

//    @Autowired
//    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private UserMapper userMapper;


    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    /**
     * token存活时间
     */
    private Long ACCESS_TOKEN_EXPIRATION = 24 * 3600L * 1000;
    /**
     * refreshToken存活时间
     */
    private Long REFRESH_TOKEN_EXPIRATION = 15 * 24 * 3600L * 1000;
    /**
     * jwt所有者
     */
//    secret: afweoiowiufherfyh9q8hf2151456
//    subject: cst
//    jwt_iss: cst
//    @Value("${jwt.subject}")
    private String SUBJECT = "cst";
    /**
     * jwt签发者
     */
//    @Value("${jwt.jwt_iss}")
    private String JWT_ISS = "cst";
    /**
     * jwt密钥
     */
//    @Value("${jwt.secret}")
    String secret ="afweoiowiufherfyh9q8hf2151456afweoiowiufherfyh9q8hf2151456";


    /**
     * 生成accessToken
     *
     * @param ：用户ID
     * @return accessToken
     */
    public String getAccessToken(String username, List<Integer> authorization) {

        System.out.println("查看生成accessToken信息:");
        final String permissions = authorization.toString();
        System.out.println("username: "+username);
        System.out.println("permissions: "+permissions);
        System.out.println("SUBJECT: "+SUBJECT);
        System.out.println("JWT_ISS: "+JWT_ISS);
        System.out.println("secret: "+secret);
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("permissions", permissions);
        return Jwts.builder().setClaims(claims)
                .setHeader(header)
                .setIssuer(JWT_ISS)
                .setSubject(SUBJECT)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    /**
     * 生成refreshToken
     */
    public String getRefreshToken(String userId, List<Integer> authorization) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("authorization", authorization);
        return Jwts.builder().setClaims(claims)
                .setHeader(header)
                .setIssuer(JWT_ISS)
                .setSubject(SUBJECT)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }
    /**
     * 解密Token，得到UserDetails
     *
     * @param token：token
     * @param request：httpServletRequest,存储用户信息
     * @return UserDetails
     */
    public UserDetails getUserDetails(String token, HttpServletRequest request)  {
        System.out.println("解密Token，得到UserDetails：");
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        String username = (String) claims.get("username");
        String permissions = (String) claims.get("permissions");
        final com.babyTalk.user.entity.User checkLogin = userMapper.selectOne(WrapperUtil.getQueryWrapper("username", username));
        if (!checkLogin.getToken().equals(token)){
            throw new JWTException(ResponseStatus.TOKEN_IS_EXPIRED.getStatus(),ResponseStatus.TOKEN_IS_EXPIRED.getMessage());
//            throw new UsernameNotFoundException("用户名不存在");
        }
        //这里设置用户id为请求头是干啥   为了设置当前登录用户吗  不过感觉直接用token就能识别
//        com.operative.user.entity.User user = new com.operative.user.entity.User();
//        user.setId(Integer.parseInt("11"));
//        request.setAttribute("activeUser", user);

        //把token中的权限字符串转换为数组
        final List<String> authorities = JSON.parseArray(permissions, String.class);

        //把权限放入security中
        Collection<GrantedAuthority> authList = new ArrayList<>();
        for (String authority : authorities) {
            authList.add(new SimpleGrantedAuthority(authority));
        }
        System.out.println("根据token获取到的当前用户的权限：");
        System.out.println(authList.toString());
        return User.withUsername(username).password("123456").authorities(authList).build();
    }

    /**
     * 获取当前登录用户信息
     * @param token
     * @return com.operative.user.entity.User
     */
    public com.babyTalk.user.entity.User getLoginUser(String token){
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        String username = (String) claims.get("username");
        final com.babyTalk.user.entity.User user = this.userMapper.selectOne(WrapperUtil.getQueryWrapper("username", username));
        if (user == null) {
            throw new JWTException(ResponseStatus.TOKEN_IS_EXPIRED.getStatus(),ResponseStatus.TOKEN_IS_EXPIRED.getMessage());
        }
        return user;
    }

    /**
     * 获取当前登录用户信息
     * @param request
     * @return com.operative.user.entity.User
     */
    public com.babyTalk.user.entity.User getLoginUserByRequest(HttpServletRequest request){
        final String authorization = request.getHeader("Authorization");
        final String token = authorization.split(" ")[1];
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        String username = (String) claims.get("username");
        final com.babyTalk.user.entity.User user = this.userMapper.selectOne(WrapperUtil.getQueryWrapper("username", username));
        if (user == null) {
            throw new JWTException(ResponseStatus.TOKEN_IS_EXPIRED.getStatus(),ResponseStatus.TOKEN_IS_EXPIRED.getMessage());
        }
        return user;
    }



}
