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
import team.rjgc.GymSys.dto.area.AreaBookDTO;
import team.rjgc.GymSys.dto.area.AreaBookShowDTO;
import team.rjgc.GymSys.entity.area.AreaBook;
import team.rjgc.GymSys.entity.area.AreaInfo;
import team.rjgc.GymSys.enums.AccountEnum;
import team.rjgc.GymSys.exception.AccountException;
import team.rjgc.GymSys.service.area.AreaBookService;
import team.rjgc.GymSys.service.area.Areaservice;
import team.rjgc.GymSys.utils.AreaBeanUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ${author}
 * @since 2019-06-20
 */
@RestController
@RequestMapping("/areaBook")
public class AreaBookController {

    @Autowired
    private AreaBookService areaBookService;
    @Autowired
    private Areaservice areaService;

    @PostMapping
    @ApiOperation(value = "申请场地预约")
    public ResultDTO insertOne(AreaBookDTO areaBookDTO, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaBook areaBook = new AreaBook();
        BeanUtils.copyProperties(areaBookDTO, areaBook);
        EffectiveTime(areaBook.getBookStartTime(), areaBook.getBookEndTime());
        areaService.conflict(areaBook);
        areaBookService.save(areaBook);
        return ResultUtil.Success(areaBook);
    }

    @PutMapping
    @ApiOperation(value = "修改预约")
    public ResultDTO updateById(AreaBook areaBook, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        EffectiveTime(areaBook.getBookStartTime(), areaBook.getBookEndTime());
        areaService.conflict(areaBook);
        areaBookService.updateById(areaBook);
        return ResultUtil.Success();
    }

    @DeleteMapping("/{id:\\d+}")
    @ApiOperation("按id删除预约")
    public ResultDTO deleteById(@PathVariable Long id, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        areaBookService.removeById((long) id);
        return ResultUtil.Success();
    }

    @GetMapping("/page")
    @ApiOperation("按条件查询（不包括id），分页")
    public IPage<AreaBookShowDTO> selectEntityPage(AreaBookDTO areaBookDTO,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "8") int pageSize, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaBook areaBook = new AreaBook();
        BeanUtils.copyProperties(areaBookDTO, areaBook);
        QueryWrapper<AreaBook> areaBookQueryWrapper = condition(areaBook);
        IPage<AreaBook> areaBookPage = areaBookService.page(new Page<AreaBook>(pageNum, pageSize), areaBookQueryWrapper);
        return AreaBeanUtils.AreaBookShowPage(areaBookPage);
    }

    @GetMapping("/list")
    @ApiOperation("按条件查询（不包括id），不分页")
    public ResultDTO selectEntityPage(AreaBookDTO areaBookDTO, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaBook areaBook = new AreaBook();
        BeanUtils.copyProperties(areaBookDTO, areaBook);
        QueryWrapper<AreaBook> areaBookQueryWrapper = condition(areaBook);

        List<AreaBook> areaBooks = areaBookService.list(areaBookQueryWrapper);
        return ResultUtil.Success(AreaBeanUtils.AreaBookShowList(areaBooks));
    }

    @GetMapping("/{id:\\d+}")
    @ApiOperation("按id查询预约")
    public ResultDTO selectEntityPage(@PathVariable Long id, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        AreaBook areaBook = areaBookService.getById(id);
        return ResultUtil.Success(AreaBeanUtils.AreaBookShow(areaBook));
    }

    //    预定冲突
    @GetMapping("/conflict")
    @ApiOperation("判断预约是否有冲突")
    public ResultDTO selectEntity(AreaBook areaBook) {
        EffectiveTime(areaBook.getBookStartTime(), areaBook.getBookEndTime());
        return ResultUtil.Success(areaService.conflict(areaBook));
    }

    @GetMapping("/booked/{id:\\d+}")
    @ApiOperation("根据id查询接下来一周某个场地的预约情况，不分页")
    public ResultDTO selectBookedById(@PathVariable Integer id, Authentication authentication) {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        return ResultUtil.Success(areaService.selectBookedById(id));
    }

    @GetMapping("/booked/{id:\\d+}/all")
    @ApiOperation("根据id查询接下来某个场地的全部预约情况，不分页")
    public ResultDTO selectAllBookedById(@PathVariable Integer id) {
        areaBookService.getById(id);
        return ResultUtil.Success(areaBookService.getById(id));
    }

    @GetMapping("/info")
    @ApiOperation("根据时间显示场地预约情况(默认一周)，不分页")
    public ResultDTO selectWithoutBookedByTime(@RequestParam(required = false) LocalDateTime startTime,
                                               @RequestParam(required = false) LocalDateTime endTime,
                                               Authentication authentication) {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        if (startTime != null) {
            EffectiveTime(startTime, endTime);
        } else {//默认一周
            startTime = LocalDateTime.now();
            endTime = startTime.plusWeeks(1);
        }
        return ResultUtil.Success(areaService.selectWithoutBookedByTime(startTime, endTime));
    }

    public void EffectiveTime(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime nowTime = LocalDateTime.now();
        if ((startTime.isBefore(nowTime))
                && (endTime.isAfter(nowTime.plusWeeks(1)))
                && (startTime.isBefore(endTime))) {//时间合理性判断
        } else {
            throw new AccountException(AccountEnum.ERRPR_TIME);
        }
    }

    //    获取选择条件
    public QueryWrapper<AreaBook> condition(AreaBook areaBook) {
        QueryWrapper<AreaBook> areaBookQueryWrapper = new QueryWrapper<>();
        if (areaBook.getAreaId() != null) {
            areaBookQueryWrapper.eq("area_id", areaBook.getAreaId());
        }
        if (areaBook.getUserId() != null) {
            areaBookQueryWrapper.eq("user_id", areaBook.getUserId());
        }
        if (areaBook.getBookStartTime() != null) {
            areaBookQueryWrapper.eq("area_book_start", areaBook.getBookStartTime());
        }
        if (areaBook.getBookEndTime() != null) {
            areaBookQueryWrapper.eq("area_book_start", areaBook.getBookEndTime());
        }
        return areaBookQueryWrapper;
    }
}
