package team.rjgc.GymSys.mapper.auth;

import org.apache.ibatis.annotations.Param;
import team.rjgc.GymSys.dto.auth.UserDTO;
import team.rjgc.GymSys.entity.auth.AuthUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-06-08
 */
public interface AuthUserMapper extends BaseMapper<AuthUser> {

    Set<String> findAuthority(@Param("name") String userName);

    List<UserDTO> findAuthorityInfo(@Param("name") String userName);

}
