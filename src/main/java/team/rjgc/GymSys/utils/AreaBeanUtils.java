package team.rjgc.GymSys.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.rjgc.GymSys.dto.area.AreaBookShowDTO;
import team.rjgc.GymSys.dto.area.AreaShowDTO;
import team.rjgc.GymSys.entity.area.AreaBook;
import team.rjgc.GymSys.entity.area.AreaInfo;
import team.rjgc.GymSys.entity.area.AreaType;
import team.rjgc.GymSys.entity.auth.AuthUser;
import team.rjgc.GymSys.service.area.AreaInfoService;
import team.rjgc.GymSys.service.area.AreaTypeService;
import team.rjgc.GymSys.service.auth.AuthUserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

//类型转换
@Component
public class AreaBeanUtils {
    @Autowired
    private AreaTypeService areaTypeService;
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private AreaInfoService areaInfoService;

    public static AreaBeanUtils areaBeanUtils;
    public static List<AreaType> areaTypes;
    public static List<AuthUser> authUsers;
    public static List<AreaInfo> areaInfos;

    @PostConstruct
    public void init() {
        areaBeanUtils = this;
        areaTypes = areaTypeService.list();
        authUsers = authUserService.list();
        areaInfos = areaInfoService.list();
    }

    //    显示场地信息的类型转换
    public static AreaShowDTO AreaShow(AreaInfo areaInfo) throws BeansException {
        AreaShowDTO areaShow = new AreaShowDTO();
        if (areaInfo != null) {
            BeanUtils.copyProperties(areaInfo, areaShow);
            for (AreaType areaType : areaTypes) {
                if (areaType.getAreaTypeId().equals(areaInfo.getAreaTypeId())) {
                    areaShow.setAreaType(areaType.getName());
                }
            }
            return areaShow;
        } else {
            return null;
        }
    }

    //    多个不分页场地信息显示
    public static List<AreaShowDTO> AreaShowList(List<AreaInfo> areaInfos) throws BeansException {
        List<AreaShowDTO> areaShowDTOS = new ArrayList<>();
        for (AreaInfo areaInfo : areaInfos) {
            AreaShowDTO areaShowDTO = AreaShow(areaInfo);
            areaShowDTOS.add(areaShowDTO);
        }
        return areaShowDTOS;
    }

    //    多个分页场地信息展示
    public static IPage<AreaShowDTO> AreaShowPage(IPage<AreaInfo> areaInfoIPage) {
        IPage<AreaShowDTO> areaShowDTOIPage = new Page<>();
        List<AreaInfo> areaInfos = areaInfoIPage.getRecords();
        List<AreaShowDTO> areaShowDTOS = new ArrayList<>();
        for (AreaInfo areaInfo : areaInfos) {
            AreaShowDTO areaShowDTO = AreaShow(areaInfo);
                areaShowDTOS.add(areaShowDTO);
        }
        return areaShowDTOIPage.setRecords(areaShowDTOS);
    }

    //    显示场地预约情况
    public static AreaBookShowDTO AreaBookShow(AreaBook areaBook) throws BeansException {
        AreaBookShowDTO areaBookShowDTO = new AreaBookShowDTO();
        Integer AreaType = null;
        if (areaBook != null) {
            BeanUtils.copyProperties(areaBook, areaBookShowDTO);
            for (AuthUser authUser : authUsers) {//设置用户名字
                if (authUser.getUserId().equals(areaBook.getUserId())) {
                    areaBookShowDTO.setUserName(authUser.getUserName());
                }
            }
            for (AreaInfo areaInfo : areaInfos) {//设置场地信息
                if (areaInfo.getAreaId().equals(areaBook.getAreaId())) {
                    areaBookShowDTO.setAreaName(areaInfo.getName());
                    areaBookShowDTO.setTeamed(areaInfo.getTeamed());
                    areaBookShowDTO.setClassed(areaInfo.getClassed());
                    areaBookShowDTO.setMoney(areaInfo.getMoney());
                    AreaType = areaInfo.getAreaTypeId();
                }
            }
            for (AreaType areaType : areaTypes) {//设置场地类型
                if (areaType.getAreaTypeId().equals(AreaType)) {
                    areaBookShowDTO.setAreaTypeName(areaType.getName());
                }
            }
            return areaBookShowDTO;
        } else {
            return null;
        }
    }

    //    多个不分页场地预约情况
    public static List<AreaBookShowDTO> AreaBookShowList(List<AreaBook> areaBooks) throws BeansException {
        List<AreaBookShowDTO> areaBookShowDTOS = new ArrayList<>();
        for (AreaBook areaBook : areaBooks) {
            AreaBookShowDTO areaBookShowDTO = AreaBookShow(areaBook);
            areaBookShowDTOS.add(areaBookShowDTO);
        }
        return areaBookShowDTOS;
    }

    //    多个分页场地预约情况
    public static IPage<AreaBookShowDTO> AreaBookShowPage(IPage<AreaBook> areaBookIPage) {
        IPage<AreaBookShowDTO> areaBookShowDTOIPage = new Page<>();
        List<AreaBook> areaBooks = areaBookIPage.getRecords();
        List<AreaBookShowDTO> areaBookShowDTOS = new ArrayList<>();
        for (AreaBook areaBook : areaBooks) {
            AreaBookShowDTO areaBookShowDTO = AreaBookShow(areaBook);
            areaBookShowDTOS.add(areaBookShowDTO);
        }
        return areaBookShowDTOIPage.setRecords(areaBookShowDTOS);
    }


}
