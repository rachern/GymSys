package team.rjgc.GymSys.service.eq;

import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.eq.EqCompensateDTO;
import team.rjgc.GymSys.entity.eq.EqCompensate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-06-17
 */
public interface EqCompensateService extends IService<EqCompensate> {

    ResultDTO saveCompensate(EqCompensateDTO compensateDTO);
}
