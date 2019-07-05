package team.rjgc.GymSys.service.eq.impl;

import com.baomidou.mybatisplus.core.injector.methods.DeleteBatchByIds;
import org.springframework.transaction.annotation.Transactional;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.eq.EqCompensateDTO;
import team.rjgc.GymSys.entity.eq.EqCompensate;
import team.rjgc.GymSys.mapper.eq.EqCompensateMapper;
import team.rjgc.GymSys.service.eq.EqCompensateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-06-17
 */
@Service
public class EqCompensateServiceImpl extends ServiceImpl<EqCompensateMapper, EqCompensate> implements EqCompensateService {

    @Override
    @Transactional
    public ResultDTO saveCompensate(EqCompensateDTO compensateDTO) {
        List<Long> oldId = list().stream().map(EqCompensate::getCompensateId).collect(Collectors.toList());
        if (oldId != null && !oldId.isEmpty()) {
            removeByIds(oldId);
        }
        return saveBatch(compensateDTO.getCompensateList())?
                ResultUtil.Success():ResultUtil.Error("500","系统异常");
    }
}
