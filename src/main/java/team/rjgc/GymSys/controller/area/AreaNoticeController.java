package team.rjgc.GymSys.controller.area;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.area.AreaNoticeDTO;
import team.rjgc.GymSys.dto.area.AreaNoticeUpdateDTO;
import team.rjgc.GymSys.entity.area.AreaNotice;
import team.rjgc.GymSys.entity.area.AreaType;
import team.rjgc.GymSys.service.area.AreaNoticeService;
import team.rjgc.GymSys.service.auth.AuthUserService;

/**
 * @author ${author}
 * @since 2019-06-17
 */
@RestController
@RequestMapping("/areaNotice")
public class AreaNoticeController {

    @Autowired
    public AreaNoticeService areaNoticeService;
    @Autowired
    private AuthUserService authUserService;

    @PostMapping
    @ApiOperation(value = "新增一条公告")
    public ResultDTO insertOne(AreaNoticeDTO areaNoticeDTO, Authentication authentication) throws Exception {
        Long userId = authUserService.getUserDetails(authentication).getUserId();
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaNotice areaNotice = new AreaNotice();
        BeanUtils.copyProperties(areaNoticeDTO, areaNotice);
        areaNotice.setUserId(userId);
        areaNoticeService.save(areaNotice);
        return ResultUtil.Success(areaNotice);
    }

    @PutMapping
    @ApiOperation(value = "按id修改公告")
    public ResultDTO updateById(AreaNoticeUpdateDTO areaNoticeUpdateDTO, Authentication authentication) throws Exception {
        Long userId = authUserService.getUserDetails(authentication).getUserId();
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaNotice areaNotice = new AreaNotice();
        BeanUtils.copyProperties(areaNoticeUpdateDTO, areaNotice);
        areaNotice.setUserId(userId);
        areaNoticeService.updateById(areaNotice);
        return ResultUtil.Success();
    }

    @DeleteMapping("/{id:\\d+}")
    @ApiOperation("按id删除公告")
    public ResultDTO deleteById(@PathVariable Long id, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        areaNoticeService.removeById((long) id);
        return ResultUtil.Success();
    }

    @GetMapping("/page")
    @ApiOperation("按条件查询（不包括id），分页")
    public IPage<AreaNotice> selectEntityPage(AreaNoticeDTO areaNoticeDTO,
                                              @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "8") int pageSize, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaNotice areaNotice = new AreaNotice();
        BeanUtils.copyProperties(areaNoticeDTO, areaNotice);
        QueryWrapper<AreaNotice> areaNoticeQueryWrapper = condition(areaNotice);
        return areaNoticeService.page(new Page<AreaNotice>(pageNum, pageSize), areaNoticeQueryWrapper);
    }

    @GetMapping("/list")
    @ApiOperation("按条件查询（不包括id），不分页")
    public ResultDTO selectEntityPage(AreaNoticeDTO areaNoticeDTO, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaNotice areaNotice = new AreaNotice();
        BeanUtils.copyProperties(areaNoticeDTO, areaNotice);
        QueryWrapper<AreaNotice> areaNoticeQueryWrapper = condition(areaNotice);
        return ResultUtil.Success(areaNoticeService.list(areaNoticeQueryWrapper));
    }

    @GetMapping("/{id:\\d+}")
    @ApiOperation("按id查询公告")
    public ResultDTO selectEntityPage(@PathVariable Long id) throws Exception {
        return ResultUtil.Success(areaNoticeService.getById(id));
    }

    //    获取选择条件
    public QueryWrapper<AreaNotice> condition(AreaNotice areaNotice) {
        QueryWrapper<AreaNotice> areaNoticeQueryWrapper = new QueryWrapper<>();
        if (areaNotice.getTitle() != null) {
            areaNoticeQueryWrapper.eq("title", areaNotice.getTitle());
        }
        if (areaNotice.getMessage() != null) {
            areaNoticeQueryWrapper.eq("message", areaNotice.getMessage());
        }
        return areaNoticeQueryWrapper;
    }

}
