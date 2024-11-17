package caodong0225.common.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author jyzxc
 * @since 2024-11-17
 */
@Data
public class UserInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String userName;
    private String nickName;
}
