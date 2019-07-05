package team.rjgc.GymSys.controller.match;


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
import team.rjgc.GymSys.dto.match.MatchInfoDTO;
import team.rjgc.GymSys.dto.match.MatchInfoSelectDTO;
import team.rjgc.GymSys.dto.match.MatchInfoUpdateDTO;
import team.rjgc.GymSys.entity.match.MatchInfo;
import team.rjgc.GymSys.entity.match.RefereeInfo;
import team.rjgc.GymSys.service.area.AreaBookService;
import team.rjgc.GymSys.service.area.AreaInfoService;
import team.rjgc.GymSys.service.auth.AuthUserService;
import team.rjgc.GymSys.service.match.MatchInfoService;
import team.rjgc.GymSys.service.match.RefereeInfoService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ${author}
 * @since 2019-06-17
 */
@RestController
@RequestMapping("/matchInfo")
public class MatchInfoController {

    @Autowired
    private MatchInfoService matchInfoService;
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private AreaBookService areaBookService;
    @Autowired
    private AreaInfoService areaInfoService;
    @Autowired
    private RefereeInfoService refereeInfoService;

    @PostMapping
    @ApiOperation(value = "插入一条数据")
    public ResultDTO insertOne(MatchInfoDTO matchInfoDTO, Authentication authentication) throws Exception {
        Long userId = authUserService.getUserDetails(authentication).getUserId();
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        MatchInfo matchInfo = new MatchInfo();
        BeanUtils.copyProperties(matchInfoDTO, matchInfo);
        matchInfo.setRegisterId(userId);
        matchInfoService.save(matchInfo);
        return ResultUtil.Success(matchInfo);
    }

    @PutMapping
    @ApiOperation(value = "按id修改数据")
    public ResultDTO updateById(MatchInfoUpdateDTO matchInfoUpdateDTO, Authentication authentication) throws Exception {
        Long userId = authUserService.getUserDetails(authentication).getUserId();
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        MatchInfo matchInfo = new MatchInfo();
        BeanUtils.copyProperties(matchInfoUpdateDTO, matchInfo);
        matchInfo.setRegisterId(userId);
        matchInfoService.updateById(matchInfo);
        return ResultUtil.Success();
    }

    @DeleteMapping("/{id:\\d+}")
    @ApiOperation("按id删除数据")
    public ResultDTO deleteById(@PathVariable Long id, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        matchInfoService.removeById((long) id);
        return ResultUtil.Success();
    }

    @GetMapping("/page")
    @ApiOperation("按条件查询，分页")
    public IPage<MatchInfo> selectEntityPage(MatchInfoSelectDTO matchInfoSelectDTO,
                                             @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "8") int pageSize, Authentication authentication) throws Exception {
//        if (authentication == null) {
//            throw new RuntimeException("未登录!");
//        }
        MatchInfo matchInfo = new MatchInfo();
        BeanUtils.copyProperties(matchInfoSelectDTO, matchInfo);
        QueryWrapper<MatchInfo> matchInfoQueryWrapper = condition(matchInfo);
        return matchInfoService.page(new Page<MatchInfo>(pageNum, pageSize), matchInfoQueryWrapper);
    }

    @GetMapping("/list")
    @ApiOperation("按条件查询，不分页")
    public ResultDTO selectEntityPage() throws Exception {
        List<MatchInfoDTO> matchInfoSelectDTOList = new ArrayList<>();
        List<MatchInfo> matchInfoList = matchInfoService.list();
        for (MatchInfo matchInfo : matchInfoList) {
            MatchInfoDTO matchInfoDTO = new MatchInfoDTO(refereeInfoService.getById(matchInfo.getRefereeId()).getName(),
                    areaInfoService.getById(areaBookService.getById(matchInfo.getAreaBookId()).getAreaId()).getName());
            matchInfoDTO.setMatchId(matchInfo.getMatchId());
            matchInfoDTO.setTitle(matchInfo.getTitle());
            matchInfoDTO.setMessage(matchInfo.getMessage());
            matchInfoSelectDTOList.add(matchInfoDTO);
        }
        return ResultUtil.Success(matchInfoSelectDTOList);
    }

    @GetMapping("/{id:\\d+}")
    @ApiOperation("按id查询数据")
    public ResultDTO selectEntityPage(@PathVariable Long id) throws Exception {
        MatchInfo matchInfo = matchInfoService.getById(id);
            MatchInfoDTO matchInfoDTO = new MatchInfoDTO(refereeInfoService.getById(matchInfo.getRefereeId()).getName(),
            areaInfoService.getById(areaBookService.getById(matchInfo.getAreaBookId()).getAreaId()).getName());
            matchInfoDTO.setMatchId(matchInfo.getMatchId());
            matchInfoDTO.setTitle(matchInfo.getTitle());
            matchInfoDTO.setMessage(matchInfo.getMessage());
        return ResultUtil.Success(matchInfoDTO);
    }

    //    获取选择条件
    public QueryWrapper<MatchInfo> condition(MatchInfo matchInfo) {
        QueryWrapper<MatchInfo> matchInfoQueryWrapper = new QueryWrapper<>();
        if (matchInfo.getAreaBookId() != null) {
            matchInfoQueryWrapper.eq("area_book_id", matchInfo.getAreaBookId());
        }
        if (matchInfo.getRefereeId() != null) {
            matchInfoQueryWrapper.eq("referee_id", matchInfo.getRefereeId());
        }
        if (matchInfo.getTitle() != null) {
            matchInfoQueryWrapper.eq("title", matchInfo.getTitle());
        }
        if (matchInfo.getMessage() != null) {
            matchInfoQueryWrapper.eq("message", matchInfo.getMessage());
        }
        if (matchInfo.getRegisterId() != null) {
            matchInfoQueryWrapper.eq("register_id", matchInfo.getRegisterId());
        }
        return matchInfoQueryWrapper;
    }
}
