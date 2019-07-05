package team.rjgc.GymSys.controller.area;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import team.rjgc.GymSys.base.ResultUtil;
import team.rjgc.GymSys.base.dto.ResultDTO;
import team.rjgc.GymSys.dto.area.AreaUseDTO;
import team.rjgc.GymSys.dto.area.AreaUseSelectDTO;
import team.rjgc.GymSys.dto.area.AreaUseUpdateDTO;
import team.rjgc.GymSys.entity.area.AreaBook;
import team.rjgc.GymSys.entity.area.AreaInfo;
import team.rjgc.GymSys.entity.auth.AuthUser;
import team.rjgc.GymSys.service.area.AreaBookService;
import team.rjgc.GymSys.service.area.AreaInfoService;
import team.rjgc.GymSys.service.area.AreaUseService;
import team.rjgc.GymSys.entity.area.AreaUse;
import team.rjgc.GymSys.service.area.Areaservice;
import team.rjgc.GymSys.service.auth.AuthUserService;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author ${author}
 * @since 2019-06-23
 */
@RestController
@RequestMapping("/areaUse")
public class AreaUseController {

    @Autowired
    private AreaUseService areaUseService;
    @Autowired
    private Areaservice areaService;
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private AreaBookService areaBookService;
    @Autowired
    private AreaInfoService areaInfoService;

    @PostMapping
    @ApiOperation(value = "新建场地使用情况")
    public ResultDTO insertOne(AreaUseDTO areaUseDTO, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaUse areaUse = new AreaUse();
        BeanUtils.copyProperties(areaUseDTO, areaUse);
        String money = areaInfoService.getById(areaBookService.getById(areaUse.getAreaBookId()).getAreaId()).getMoney();//获取单价
        Long hours = useTime(areaUse.getUseStartTime(), areaUse.getUseEndTime());//获取使用时间
        Long totalmoney = hours * Integer.valueOf(money);
        areaUse.setMoney(totalmoney.toString());
        areaUseService.save(areaUse);
        return ResultUtil.Success(areaUse);
    }

    @PutMapping
    @ApiOperation(value = "按id修改场地使用情况")
    public ResultDTO updateById(AreaUseUpdateDTO areaUseUpdateDTO, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaUse areaUse = new AreaUse();
        BeanUtils.copyProperties(areaUseUpdateDTO, areaUse);
        String money = areaInfoService.getById(areaBookService.getById(areaUse.getAreaBookId()).getAreaId()).getMoney();//获取单价
        Long hours = useTime(areaUse.getUseStartTime(), areaUse.getUseEndTime());//获取使用时间
        Long totalmoney = hours * Integer.valueOf(money);
        areaUse.setMoney(totalmoney.toString());
        areaUseService.updateById(areaUse);
        return ResultUtil.Success();
    }

    @DeleteMapping("/{id:\\d+}")
    @ApiOperation("按id删除场地使用情况")
    public ResultDTO deleteById(@PathVariable Long id, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        areaUseService.removeById((long) id);
        return ResultUtil.Success();
    }

    @GetMapping("/page")
    @ApiOperation("按条件查询场地使用情况（（不包括id），分页")
    public IPage<AreaUse> selectEntityPage(AreaUseSelectDTO areaUseSelectDTO,
                                           @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "8") int pageSize, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaUse areaUse = new AreaUse();
        BeanUtils.copyProperties(areaUseSelectDTO, areaUse);
        QueryWrapper<AreaUse> areaUseQueryWrapper = condition(areaUse);
        return areaUseService.page(new Page<AreaUse>(pageNum, pageSize), areaUseQueryWrapper);
    }

    @GetMapping("/list")
    @ApiOperation("按条件查询场地使用情况（不包括id），不分页")
    public ResultDTO selectEntityPage(AreaUseSelectDTO areaUseSelectDTO, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaUse areaUse = new AreaUse();
        BeanUtils.copyProperties(areaUseSelectDTO, areaUse);
        QueryWrapper<AreaUse> areaUseQueryWrapper = condition(areaUse);
        return ResultUtil.Success(areaUseService.list(areaUseQueryWrapper));
    }

    @GetMapping("/{id:\\d+}")
    @ApiOperation("按id查询场地使用情况")
    public ResultDTO selectEntityPage(@PathVariable Long id, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        return ResultUtil.Success(areaUseService.getById(id));
    }

    @GetMapping("/break")
    @ApiOperation("失约的场地预约信息")
    public ResultDTO selectBreakBook() {
        ;
        return ResultUtil.Success(areaService.breakBook());
    }

    @GetMapping("/history")
    @ApiOperation("查看某个人单独的场地租用历史，登陆使用")
    public ResultDTO selecthistoryByUser(Authentication authentication) throws Exception {
        Long userId = authUserService.getUserDetails(authentication).getUserId();
        return ResultUtil.Success(areaService.history(userId));
    }

    //    获取选择条件
    public QueryWrapper<AreaUse> condition(AreaUse areaUse) {
        QueryWrapper<AreaUse> areaUseQueryWrapper = new QueryWrapper<>();
        if (areaUse.getAreaBookId() != null) {
            areaUseQueryWrapper.eq("area_book_id", areaUse.getAreaBookId());
        }
        if (areaUse.getUseStartTime() != null) {
            areaUseQueryWrapper.eq("use_start_time", areaUse.getUseStartTime());
        }
        if (areaUse.getUseEndTime() != null) {
            areaUseQueryWrapper.eq("use_end_time", areaUse.getUseEndTime());
        }
        if (areaUse.getMoney() != null) {
            areaUseQueryWrapper.eq("money", areaUse.getMoney());
        }
        return areaUseQueryWrapper;
    }

    //    计算时差
    public Long useTime(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes();//相差的分钟数
        long millis = duration.toMillis();//相差毫秒数
        long nanos = duration.toNanos();//相差的纳秒数
        if (minutes == 0 && millis == 0 && nanos == 0) {
        } else {
            hours++;
        }
        return hours;
    }
}
