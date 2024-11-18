package caodong0225.login.controller;

import caodong0225.common.dto.CreateUserDTO;
import caodong0225.common.dto.UserLoginDTO;
import caodong0225.common.entity.Users;
import caodong0225.common.response.BaseDataResponse;
import caodong0225.common.response.BaseResponse;
import caodong0225.login.service.IUserService;
import caodong0225.common.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jyzxc
 * @since 2024-11-14
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户管理相关接口，包括注册和登录，返回jwt token值")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final IUserService userService;

    @PostMapping("/register")
    @Operation(summary = "注册用户信息", description = "注册一个新用户")
    @ApiResponse(responseCode = "500", description = "失败")
    @ApiResponse(responseCode = "200", description = "成功")
    public ResponseEntity<BaseResponse> register(
            @Valid @RequestBody CreateUserDTO createUserDTO
    ) {
        if(userService.getUserByUsername(createUserDTO.getUsername()) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.makeResponse(400,"注册失败，用户名已存在"));
        }
        if(userService.insertUser(createUserDTO))
        {
            return ResponseEntity.ok(BaseResponse.makeResponse(200,"注册成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.makeResponse(500,"注册失败"));
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录")
    @ApiResponse(responseCode = "404", description = "登录失败")
    @ApiResponse(responseCode = "200", description = "成功")
    public ResponseEntity<BaseDataResponse> login(
            @Valid @RequestBody UserLoginDTO user
    ) {
        String username = user.getUsername();
        String password = user.getPassword();
        Users loginUser = userService.login(username,password);
        if (loginUser == null) {
            return ResponseEntity.ok(new BaseDataResponse(404, "用户名或密码错误"));
        }
        String token = JwtUtil.createToken(loginUser);
        return ResponseEntity.ok(new BaseDataResponse(token));
    }
}
