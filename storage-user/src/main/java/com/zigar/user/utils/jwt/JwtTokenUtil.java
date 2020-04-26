package com.zigar.user.utils.jwt;

import com.zigar.api.entity.UserEntity;
import com.zigar.user.service.IUserService;
import com.zigar.zigarcore.properties.JwtProperties;
import com.zigar.zigarcore.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtTokenUtil {

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    IUserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    private Clock clock = DefaultClock.INSTANCE;

    public String getTokenFromBearerToken(String bearerToken) {
        if (StringUtils.startsWith(bearerToken, jwtProperties.getJwtPrefix())) {
            return StringUtils.substring(bearerToken, jwtProperties.getJwtPrefix().length());
        }
        return null;
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        return false;
    }


    /**
     * 生成token
     *
     * @param userName
     * @param userId
     * @return
     */
    public JwtToken generateToken(String userName, String userId) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        Map<String, Object> claims = new HashMap<>();
        String jwtId = IdUtils.nextStrId();
        // 基本 security 信息
        claims.put(Claims.ID, jwtId);
        claims.put(Claims.SUBJECT, userName);
        claims.put(Claims.ISSUED_AT, createdDate.getTime());
        claims.put(Claims.EXPIRATION, expirationDate.getTime());
        // 其它自定义信息
        claims.put("userId", userId);
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret()).compact();
        return new JwtToken(jwtId, token, claims);
    }

    /**
     * 计算过期时间
     *
     * @param createdDate
     * @return
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + jwtProperties.getTokenExpiredTime());
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public JwtToken refreshToken(String token) {

        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        final Claims claims = getAllClaimsFromToken(token);

        claims.put(Claims.ISSUED_AT, createdDate.getTime());
        claims.put(Claims.EXPIRATION, expirationDate.getTime());

        String jwtId = claims.getId();
        String newToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret()).compact();

        return new JwtToken(jwtId, newToken, claims);
    }

    /**
     * 检查token是否有效
     *
     * @param token
     * @param userEntity
     * @return
     */
    public Boolean validateToken(String token, UserEntity userEntity) {
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        return (username.equals(userEntity.getUsername()) && !isTokenExpired(token))
                && !isCreatedBeforeLastPasswordReset(created, DateUtils.localDateTime2Date(userEntity.getPwdResetTime()));
    }


//    jwt不需要存储在缓存。


//    public boolean checkRedisToken(String userId, String token) {
//        List<String> redisTokenList = (List<String>) redisTemplate.opsForValue().get(userId);
//        return redisTokenList != null && redisTokenList.contains(token);
//    }


    /**
     * 判断当前redis是否具备保存当前用户token的条件
     * 条件1：配置无法踢出最早登陆用户，未达到最大用户登录点数量
     * 条件2：配置允许踢出最早登录用户。
     * 如果满足条件则把当前token放进用户token数组
     *
     * @param userId
     * @param token
     * @return
     */
//    public boolean setTokenToRedis(String userId, String token) {
//        Integer maxLoginUsers = jwtProperties.getMaxLoginUsers();
//        List<String> redisTokenList = (List<String>) redisTemplate.opsForValue().get(userId);
//        if (!CollectionUtils.isEmpty(redisTokenList) && redisTokenList.size() == maxLoginUsers) {
//            if (jwtProperties.getCanLoginOverflow()) {
//                redisTokenList.set(0, token);
//                redisTemplate.opsForValue().set(userId, redisTokenList);
//                return true;
//            }
//            return false;
//        } else {
//            redisTokenList.add(token);
//            redisTemplate.opsForValue().set(userId, redisTokenList);
//            return true;
//        }
//    }


}
