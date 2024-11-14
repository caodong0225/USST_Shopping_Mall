package caodong0225.login.service.impl;

import caodong0225.common.dto.CreateUserDTO;
import caodong0225.common.entity.Users;
import caodong0225.login.mapper.UserMapper;
import caodong0225.login.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author jyzxc
 * @since 2024-11-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, Users> implements IUserService {

    @Override
    public boolean insertUser(CreateUserDTO createUserDTO) {
        createUserDTO.setPassword(BCrypt.hashpw(createUserDTO.getPassword(), BCrypt.gensalt()));
        return baseMapper.insert(createUserDTO.toUser()) > 0;
    }

    @Override
    public Users login(String username, String password) {
        Users user = this.lambdaQuery()
                .eq(Users::getUsername, username)
                .one();
        if (user == null) {
            return null;
        }

        String realPassword = user.getHash();
        boolean isPasswordMatch = BCrypt.checkpw(password, realPassword);

        user.setHash(null);

        return isPasswordMatch ? user : null;
    }

    @Override
    public Users getUserByUsername(String username) {
        return this.lambdaQuery()
                .eq(Users::getUsername, username)
                .one();
    }

}
