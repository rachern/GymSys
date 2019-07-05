package team.rjgc.GymSys.controller.area;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.entity.area.AreaBook;
import team.rjgc.GymSys.entity.area.AreaInfo;
import team.rjgc.GymSys.enums.AccountEnum;
import team.rjgc.GymSys.exception.AccountException;
import team.rjgc.GymSys.service.area.AreaInfoService;
import team.rjgc.GymSys.service.area.AreaTypeService;
import team.rjgc.GymSys.entity.area.AreaType;
import team.rjgc.GymSys.service.auth.AuthUserService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ${author}
 * @since 2019-06-22
 */
@RestController
@RequestMapping("/areaType")
public class AreaTypeController {

    @Autowired
    private AreaTypeService areaTypeService;
    @Autowired
    private AreaInfoService areaInfoService;

    @PostMapping
    @ApiOperation(value = "新增一个场地类型")
    public ResultDTO insertOne(@RequestParam String typeName, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        if (!typeNameUnconflict(typeName)) {
            throw new RuntimeException("场地类型名冲突");
        }
        AreaType areaType = new AreaType();
        areaType.setName(typeName);
        areaTypeService.save(areaType);
        return ResultUtil.Success(areaType);
    }

    @PutMapping
    @ApiOperation(value = "修改场地类型")
    public ResultDTO updateById(AreaType areaType, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        if (!typeNameUnconflict(areaType.getName())) {
            throw new RuntimeException("场地类型名冲突");
        }
        areaTypeService.updateById(areaType);
        return ResultUtil.Success();
    }

    @DeleteMapping("/{id:\\d+}")
    @ApiOperation("删除场地类型,确定没有场地为该类型才可以删除")
    public ResultDTO deleteById(@PathVariable Long id, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        List<AreaInfo> areaInfos = areaInfoService.list();
        for (AreaInfo areaInfo : areaInfos) {
            if (areaInfo.getAreaTypeId().equals(id)) {
                throw new RuntimeException("场地类型正在使用");
            }
        }
        areaTypeService.removeById((long) id);
        return ResultUtil.Success();
    }

    @GetMapping("/page")
    @ApiOperation("按条件查询场地类型（不包括id），分页")
    public IPage<AreaType> selectEntityPage(String  name,
                                            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "8") int pageSize, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        QueryWrapper<AreaType> areaTypeQueryWrapper =  new QueryWrapper<>();
        if(name!=null){
            areaTypeQueryWrapper.eq("name",name);
        }
        return areaTypeService.page(new Page<AreaType>(pageNum, pageSize), areaTypeQueryWrapper);
    }

    @GetMapping("/list")
    @ApiOperation("按条件查询场地类型（不包括id），不分页")
    public ResultDTO selectEntityPage(String name, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        QueryWrapper<AreaType> areaTypeQueryWrapper =  new QueryWrapper<>();
        if(name!=null){
            areaTypeQueryWrapper.eq("name",name);
        }
        return ResultUtil.Success(areaTypeService.list(areaTypeQueryWrapper));
    }

    @GetMapping("/{id:\\d+}")
    @ApiOperation("按id查询场地类型")
    public ResultDTO selectEntityPage(@PathVariable Long id, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        return ResultUtil.Success(areaTypeService.getById(id));
    }

    @GetMapping("/unconflict")
    @ApiOperation(value = "查看又没有重复的名字，" +
            "true为无重复，否则为不可用")
    public Boolean typeNameUnconflict(String typeName) {
        boolean useful = true;
        List<AreaType> areaTypeList = areaTypeService.list();//查询全部的类型
        for (AreaType areaType : areaTypeList) {
            if (areaType.getName().equals(typeName)) {
                useful = false;
            }
        }
        return useful;
    }
}
