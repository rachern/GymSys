package team.rjgc.GymSys.entity.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;

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
public class AuthUser extends Model<AuthUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @ApiParam(value = "用户名",required = true)
    private String userName;

    @ApiParam(value = "密码",required = true)
    private String userPassword;

    @Email(message = "邮箱格式不正确!")
    @ApiParam(value = "邮箱",required = true)
    private String userEmail;

    public AuthUser(String userName) {
        this.userName = userName;
    }

    public AuthUser() {
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
