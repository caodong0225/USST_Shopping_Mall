package caodong0225.common.dto;

import caodong0225.common.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author jyzxc
 * @since 2024-11-14
 */
@Getter
@Setter
@Schema(name = "CreateUserDTO", description = "创建用户的请求体")
public class CreateUserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    @Schema(name = "username", description = "用户名，长度必须在3-20之间，只能包含字母、数字和下划线")
    private String username;

    @NotBlank(message = "昵称不能为空")
    @Size(min = 2, max = 20, message = "昵称长度必须在2-20之间")
    // 中英日韩字符、数字、下划线
    @Pattern(regexp = "^[\\u4e00-\\u9fa5\\u3040-\\u30FF\\uAC00-\\uD7AF\\uFF00-\\uFFEF\\u1100-\\u11FF\\u3130-\\u318F\\uAC00-\\uD7A3a-zA-Z0-9_]+$", message = "昵称只能包含中英文、数字和下划线")
    @Schema(name = "nickname", description = "昵称，长度必须在2-20之间，只能包含中英日韩字符、数字和下划线")
    private String nickname;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 32, message = "密码长度必须在6-32之间")
    @Pattern(message = "密码必须包含大小写字母、数字或特殊字符其中至少两种", regexp = "^(?![a-zA-Z]+$)(?!\\d+$)(?![^\\da-zA-Z\\s]+$).{6,32}$")
    @Schema(name = "password", description = "密码，长度必须在6-32之间，必须包含大小写字母、数字或特殊字符其中至少两种")
    private String password;


    public Users toUser() {
        Users user = new Users();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setHash(password);
        return user;
    }
}
