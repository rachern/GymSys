package team.rjgc.GymSys.entity.auth;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AuthUserRoleTable extends Model<AuthUserRoleTable> {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;

    public AuthUserRoleTable(Long userId) {
        this.userId = userId;
    }

    public AuthUserRoleTable(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public AuthUserRoleTable() {
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
