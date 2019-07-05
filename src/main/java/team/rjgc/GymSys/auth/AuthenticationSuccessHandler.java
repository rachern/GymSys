package team.rjgc.GymSys.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team.rjgc.GymSys.dto.auth.RoleDTO;
import team.rjgc.GymSys.dto.auth.UseInfoDTO;
import team.rjgc.GymSys.dto.auth.UserDTO;
import team.rjgc.GymSys.entity.auth.AuthUser;
import team.rjgc.GymSys.enums.AccountEnum;
import team.rjgc.GymSys.mapper.auth.AuthUserMapper;
import team.rjgc.GymSys.service.auth.AuthUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ReMidDream
 * @date 2018/10/12 15:56
 **/
@Component
@Slf4j
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private AuthUserMapper authUserMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("登录成功!");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        AuthUser user = new AuthUser();
        try {
            user = authUserService.getUserDetails(authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        UserDTO userDTO = authUserMapper.findAuthorityInfo(user.getUserName()).get(0);
//
//        for (RoleDTO child : userDTO.getChildren()) {
//            if (child.getName().equals(AccountEnum.USER.getMessage())) {
//                response.sendRedirect("/index.html");
//            }
//            if (child.getName().equals(AccountEnum.EQ_ADMIN.getMessage())) {
//                response.sendRedirect("/equipmentSys/managerEquipmentList.html");
//            }
//        }
        UserDTO userDTO = authUserMapper.findAuthorityInfo(user.getUserName()).get(0);
        userDTO.setUserPassword(null);
        response.getWriter().write(objectMapper.writeValueAsString(userDTO));

    }
}