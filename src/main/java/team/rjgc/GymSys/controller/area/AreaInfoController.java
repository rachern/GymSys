package team.rjgc.GymSys.controller.area;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.area.AreaDTO;
import team.rjgc.GymSys.dto.area.AreaShowDTO;
import team.rjgc.GymSys.entity.area.AreaBook;
import team.rjgc.GymSys.entity.area.AreaType;
import team.rjgc.GymSys.service.area.AreaInfoService;
import team.rjgc.GymSys.entity.area.AreaInfo;
import team.rjgc.GymSys.service.area.AreaTypeService;
import team.rjgc.GymSys.utils.AreaBeanUtils;

import java.util.List;

/**
 * @author ${author}
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/areaInfo")
public class AreaInfoController {

    @Autowired
    public AreaInfoService areaInfoService;
    @Autowired
    private AreaTypeService areaTypeService;

    @PostMapping
    @ApiOperation(value = "新增一个场地信息，classed默认为非上课场地(0)，teamed默认为非校队场地(0)")
    public ResultDTO insertOne(@RequestBody AreaDTO areaDTO, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaInfo areaInfo = new AreaInfo();
        BeanUtils.copyProperties(areaDTO, areaInfo);
        if (!EffectiveTypeId(areaInfo)) {
            throw new RuntimeException("场地类型不正确");
        } else if (areaInfo.getMoney().equals("") || areaInfo.getMoney() == null) {
            throw new RuntimeException("场地费用不能为空");
        }
        areaInfoService.save(areaInfo);
        return ResultUtil.Success(areaInfo);
    }

    @PutMapping
    @ApiOperation(value = "更新场地信息")
    public ResultDTO updateById(AreaInfo areaInfo, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        if (!EffectiveTypeId(areaInfo)) {
            throw new RuntimeException("场地类型不正确");
        } else if (areaInfo.getMoney().equals("") || areaInfo.getMoney() == null) {
            throw new RuntimeException("场地费用不能为空");
        }
        areaInfoService.updateById(areaInfo);
        return ResultUtil.Success();
    }

    @DeleteMapping("/{id:\\d+}")
    @ApiOperation("删除场地信息")
    public ResultDTO deleteById(@PathVariable Long id, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        areaInfoService.removeById((long) id);
        return ResultUtil.Success();
    }

    @GetMapping("/page")
    @ApiOperation("按条件查询（不包括id），分页")
    public IPage<AreaShowDTO> selectEntityPage(AreaDTO areaDTO,
                                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "8") int pageSize, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaInfo areaInfo = new AreaInfo();
        BeanUtils.copyProperties(areaDTO, areaInfo);
        QueryWrapper<AreaInfo> areaInfoQueryWrapper = condition(areaInfo);
        IPage<AreaInfo> areaInfoIPage = areaInfoService.page(new Page<AreaInfo>(pageNum, pageSize), areaInfoQueryWrapper);
        return AreaBeanUtils.AreaShowPage(areaInfoIPage);
    }

    @GetMapping("/list")
    @ApiOperation("按条件查询（不包括id），不分页")
    public ResultDTO selectEntityPage(AreaDTO areaDTO, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaInfo areaInfo = new AreaInfo();
        BeanUtils.copyProperties(areaDTO, areaInfo);
        QueryWrapper<AreaInfo> areaInfoQueryWrapper = condition(areaInfo);
        List<AreaInfo> areaInfos = areaInfoService.list(areaInfoQueryWrapper);
        return ResultUtil.Success(AreaBeanUtils.AreaShowList(areaInfos));
    }

    @GetMapping("/{id:\\d+}")
    @ApiOperation("按id查询数据")
    public ResultDTO selectEntityPage(@PathVariable Long id, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaInfo areaInfo = areaInfoService.getById(id);
        return ResultUtil.Success(AreaBeanUtils.AreaShow(areaInfo));
    }


    //    判断场地的场地类型是否有效
    public Boolean EffectiveTypeId(AreaInfo areaInfo) {
        boolean useful = false;
        Integer typeId = areaInfo.getAreaTypeId();
        List<AreaType> areaTypeList = areaTypeService.list();
        for (AreaType areaType : areaTypeList) {
            if (areaType.getAreaTypeId().equals(areaInfo.getAreaTypeId())) {
                useful = true;
            }
        }
        return useful;
    }

    //    获取选择条件
    public QueryWrapper<AreaInfo> condition(AreaInfo areaInfo) {
        QueryWrapper<AreaInfo> areaInfoQueryWrapper = new QueryWrapper<>();
        if (areaInfo.getAreaTypeId() != null) {
            areaInfoQueryWrapper.eq("area_type_id", areaInfo.getAreaTypeId());
        }
        if (areaInfo.getName() != null) {
            areaInfoQueryWrapper.eq("name", areaInfo.getName());
        }
        if (areaInfo.getTeamed() != null) {
            areaInfoQueryWrapper.eq("teamed", areaInfo.getTeamed());
        }
        if (areaInfo.getClassed() != null) {
            areaInfoQueryWrapper.eq("classed", areaInfo.getClassed());
        }
        if (areaInfo.getMoney() != null) {
            areaInfoQueryWrapper.eq("money", areaInfo.getMoney());
        }
        return areaInfoQueryWrapper;
    }
}
