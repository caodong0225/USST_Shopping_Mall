package caodong0225.common.util;

import caodong0225.common.entity.Users;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jyzxc
 * @since 2024-11-14
 */
public class JwtUtil {
    public static final String SECRET_KEY = "caodong0225";

    public static String createToken(Users userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("username", userDetails.getUsername());
        claims.put("nickname", userDetails.getNickname());
        //claims.put("authorities", userDetails.getAuthorities().toString());
        return JWT.create()
                .withClaim("user", claims)
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public static boolean isTokenExpired(String token) {
        try {
            DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
            Date expiration = decodedJwt.getExpiresAt();
            return expiration.before(new Date());
        } catch (Exception e) {
            // 如果验证过程出现异常，则认为Token无效或已过期
            return true;
        }
    }


}
