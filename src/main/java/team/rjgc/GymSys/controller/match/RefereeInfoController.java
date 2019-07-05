package team.rjgc.GymSys.controller.match;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.service.match.RefereeInfoService;
import team.rjgc.GymSys.entity.match.RefereeInfo;

/**
 *
 * @author ${author}
 * @since 2019-06-17
 */
@RestController
@RequestMapping("/refereeInfo")
public class RefereeInfoController {

    @Autowired
    public RefereeInfoService refereeInfoService;

    @PostMapping
    @ApiOperation(value = "插入一条数据")
    public ResultDTO insertOne(RefereeInfo refereeInfo) throws Exception {
        refereeInfoService.save(refereeInfo);
        return ResultUtil.Success(refereeInfo);
    }

    @PutMapping
    @ApiOperation(value = "按id修改数据")
    public ResultDTO updateById(RefereeInfo refereeInfo) throws Exception {
        refereeInfoService.updateById(refereeInfo);
        return ResultUtil.Success();
    }

    @DeleteMapping("/{id:\\d+}")
    @ApiOperation("按id删除数据")
    public ResultDTO deleteById(@PathVariable Long id) throws Exception {
        refereeInfoService.removeById((long) id);
        return ResultUtil.Success();
    }

    @GetMapping("/page")
    @ApiOperation("按条件查询，分页")
    public IPage<RefereeInfo> selectEntityPage(RefereeInfo refereeInfo,
                                            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "8") int pageSize)throws Exception{
        QueryWrapper<RefereeInfo> refereeInfoQueryWrapper = new QueryWrapper<>();
        //refereeInfoQueryWrapper.eq("name",test.getName());
        return refereeInfoService.page(new Page<RefereeInfo>(pageNum, pageSize), refereeInfoQueryWrapper);
    }

    @GetMapping("/list")
    @ApiOperation("按条件查询，不分页")
    public ResultDTO selectEntityPage(RefereeInfo refereeInfo)throws Exception{
        QueryWrapper<RefereeInfo> refereeInfoQueryWrapper = new QueryWrapper<>();
        //refereeInfoQueryWrapper.eq("name",test.getName());
        return ResultUtil.Success(refereeInfoService.list(refereeInfoQueryWrapper));
    }

    @GetMapping("/{id:\\d+}")
    @ApiOperation("按id查询数据")
    public ResultDTO selectEntityPage(@PathVariable Long id)throws Exception{
        return ResultUtil.Success(refereeInfoService.getById(id));
    }


}
