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
public class AuthRolePowerTable extends Model<AuthRolePowerTable> {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long powerId;

    public AuthRolePowerTable(Long roleId) {
        this.roleId = roleId;
    }

    public AuthRolePowerTable(Long roleId, Long powerId) {
        this.roleId = roleId;
        this.powerId = powerId;
    }

    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

}
