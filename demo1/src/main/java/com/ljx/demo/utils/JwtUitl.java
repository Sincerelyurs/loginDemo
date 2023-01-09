package com.ljx.demo.utils;

import com.ljx.demo.mapper.UserMapper;
import com.ljx.demo.pojo.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Component
public class JwtUitl {
    /**
     * 过期时间5分钟
     */
    private static final long EXPIRE_TIME=5*60*1000;
    /**
     * 加密密钥
     */
    private static final String KEY = "liujunxiaodfaijdfha9sdgfdsagasdgasdgadsfgasdgasd";
    @Autowired
    private static UserMapper userMapper;

    /**
     * 生成token
     * @param user_name  用户名
     * @return
     */
    public static String createToken(String user_name){
        Map<String,Object> header = new HashMap();
        header.put("typ","JWT");
        header.put("alg","HS256");
        //setID:用户ID
        //setExpiration:token过期时间  当前时间+有效时间
        //setSubject:用户名
        //setIssuedAt:token创建时间
        //signWith:加密方式
        JwtBuilder builder = Jwts.builder().setHeader(header)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                .setSubject(user_name)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,KEY);
        return builder.compact();
    }
    /**
     * 验证token是否有效
     * @param token  请求头中携带的token
     * @return  token验证结果  2-token过期；1-token认证通过；0-token认证失败
     */
    public static int verify(String token){
        Claims claims = null;
        try {
            //token过期后，会抛出ExpiredJwtException 异常，通过这个来判定token过期，1验证成功，0验证失败
            claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return 2;
        }
//        //从token中获取用户id，查询该Id的用户是否存在，存在则token验证通过
//        int res = userMapper.findUser(claims.getSubject());
//        if(res == 1){
//            return 1;
//        }
//        return 0;
        return 1;
    }
    public static String returnUsername(String token){
        Claims claims = null;
        try {
            //token过期后，会抛出ExpiredJwtException 异常，通过这个来判定token过期，1验证成功，0验证失败
            claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return null;
        }
        return claims.getSubject();
    }

}
