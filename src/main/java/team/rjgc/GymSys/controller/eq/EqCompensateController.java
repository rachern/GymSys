package team.rjgc.GymSys.controller.eq;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.eq.EqCompensateDTO;
import team.rjgc.GymSys.entity.eq.EqInfo;
import team.rjgc.GymSys.service.eq.EqCompensateService;
import team.rjgc.GymSys.entity.eq.EqCompensate;
import team.rjgc.GymSys.service.eq.EqInfoService;

import java.util.List;

/**
 *
 * @author ${author}
 * @since 2019-06-17
 */
@RestController
@RequestMapping("/equipment/compensate")
@CrossOrigin
public class EqCompensateController {

    @Autowired
    private  EqCompensateService eqCompensateService;
    @Autowired
    private EqInfoService eqInfoService;

    @PostMapping
    @ApiOperation(value = "赔偿标准(compensateId不填, 填compensatePrice和eqId)")
    public ResultDTO insertOne(@RequestBody EqCompensateDTO compensateDTO) throws Exception {
        return eqCompensateService.saveCompensate(compensateDTO);
    }

    @GetMapping("/list")
    @ApiOperation("查询全部赔偿标准")
    public ResultDTO selectEntityPage()throws Exception{
        List<EqCompensate> compensateList = eqCompensateService.list();
        for (EqCompensate eqCompensate : compensateList) {
            eqCompensate.setEqName(eqInfoService.getById(eqCompensate.getEqId()).getEqName());
        }
        return ResultUtil.Success(compensateList);
    }


}
