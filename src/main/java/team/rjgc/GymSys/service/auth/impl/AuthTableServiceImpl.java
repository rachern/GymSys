package team.rjgc.GymSys.service.auth.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.rjgc.GymSys.dto.auth.AuthenticationDTO;
import team.rjgc.GymSys.entity.auth.AuthPower;
import team.rjgc.GymSys.entity.auth.AuthRole;
import team.rjgc.GymSys.entity.auth.AuthRolePowerTable;
import team.rjgc.GymSys.entity.auth.AuthUserRoleTable;
import team.rjgc.GymSys.enums.AccountEnum;
import team.rjgc.GymSys.exception.AccountException;
import team.rjgc.GymSys.service.auth.AuthTableService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ReMidDream
 * @date 2018/10/31 20:23
 **/
@Slf4j
@Component("authTableService")
public class AuthTableServiceImpl implements AuthTableService {

    @Autowired
    private Map<String, IService<?>> ServiceMap;

    @Override
    @Transactional
    public void updateTable(AuthenticationDTO dto, String type) throws Exception {
        if(check(dto)){
            updateUserRoleTableHandler(dto, type);
        }
    }

    private boolean check(AuthenticationDTO dto){
        if (dto.getId() == null||dto.getChildren().size() == 0) {
            log.info("参数异常!");
            throw new AccountException(AccountEnum.Error);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private void updateUserRoleTableHandler(AuthenticationDTO dto, String type) throws Exception {

        IService baseService;
        IService tableService;
        if (type.equals("user")) {
            baseService = ServiceMap.get("authRoleServiceImpl");
            tableService = ServiceMap.get("authUserRoleTableServiceImpl");
        } else {
            tableService = ServiceMap.get("authRolePowerTableServiceImpl");
            baseService = ServiceMap.get("authPowerServiceImpl");
        }

        //数据库所有角色
        List<Object> allChild = baseService.list();
        List<Long> allChildIds = new ArrayList<>();
        //数据库所有角色的Id
        if (type.equals("user")) {
            for (Object role : allChild) {
                allChildIds.add(((AuthRole)role).getRoleId());
            }
        }else{
            for (Object power : allChild) {
                allChildIds.add(((AuthPower)power).getPowerId());
            }
        }
        // 预关联角色的Id
        List<AuthenticationDTO> targetChild = dto.getChildren();
        List<Long> targetIds = targetChild.stream().map(AuthenticationDTO::getId).collect(Collectors.toList());

        /*
         * 预关联角色的Id是否存在
         * */
        for (Long id : targetIds) {
            if (allChildIds.stream().noneMatch(id::equals)) {
                log.info("编号不存在!");
                throw new AccountException(AccountEnum.Error);
            }
        }
        /*
         * 用户在关系表中已存在的孩子Id
         * */
        List<Long> existentChildIds;
        if (type.equals("user")) {
            QueryWrapper<AuthUserRoleTable> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",dto.getId());
            List<AuthUserRoleTable> tables = tableService.list(wrapper);
            existentChildIds = tables.stream().map(AuthUserRoleTable::getRoleId).collect(Collectors.toList());
        } else {
            QueryWrapper<AuthRolePowerTable> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id",dto.getId());
            List<AuthRolePowerTable> tables = tableService.list(wrapper);
            existentChildIds = tables.stream().map(AuthRolePowerTable::getPowerId).collect(Collectors.toList());
        }
        /*
         * 判断有没有任何关联角色
         * */
        if (existentChildIds.size() != 0) {
            for (Long roleId : targetIds) {
                /*
                 * 匹配到,移出队列; 匹配不到,插入操作
                 * */
                if (existentChildIds.stream().anyMatch(roleId::equals)) {
                    existentChildIds.remove(roleId);
                } else {
                    Object table;
                    if (type.equals("user")) {
                        table = new AuthUserRoleTable(dto.getId(), roleId);
                    } else {
                        table = new AuthRolePowerTable(dto.getId(), roleId);
                    }
                    tableService.save(table);
                }
            }
            /*
             * 将队列里面剩余的关联数据删除
             * */
            if (existentChildIds.size() != 0) {
                //todo 改成批量删除
                for (Long existentChildId : existentChildIds) {
                    Object deleteTable;
                    if (type.equals("user")) {
                        QueryWrapper<AuthUserRoleTable> wrapper = new QueryWrapper<>();
                        wrapper.eq("user_id",dto.getId());
                        wrapper.eq("role_id",existentChildId);
                        tableService.remove(wrapper);
                    } else {
                        QueryWrapper<AuthUserRoleTable> wrapper = new QueryWrapper<>();
                        wrapper.eq("role_id",dto.getId());
                        wrapper.eq("power_id",existentChildId);
                        tableService.remove(wrapper);
                    }
                }
            }
        } else {
            //todo 改成批量添加
            for (Long childId : targetIds) {
                Object table;
                if (type.equals("user")) {
                    table = new AuthUserRoleTable(dto.getId(), childId);
                } else {
                    table = new AuthRolePowerTable(dto.getId(), childId);
                }
                tableService.save(table);
            }
        }
    }
}
