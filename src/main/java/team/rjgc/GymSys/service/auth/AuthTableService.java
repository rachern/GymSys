package team.rjgc.GymSys.service.auth;


import team.rjgc.GymSys.dto.auth.AuthenticationDTO;

/**
 * @author ReMidDream
 * @date 2018/10/31 20:23
 **/
public interface AuthTableService {

    void updateTable(AuthenticationDTO baseDto, String type)throws Exception;

}
