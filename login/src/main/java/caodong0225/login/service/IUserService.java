package caodong0225.login.service;

import caodong0225.common.dto.CreateUserDTO;
import caodong0225.common.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author jyzxc
 * @since 2024-11-14
 */
public interface IUserService extends IService<Users> {
    boolean insertUser(CreateUserDTO createUserDTO);
    Users login(String username, String password);
    Users getUserByUsername(String username);
}
