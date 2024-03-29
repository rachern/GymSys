package team.rjgc.GymSys.base;


import team.rjgc.GymSys.base.dto.ResultDTO;

/**
 * @author ReMidDream
 * @date 2018-02-22 15:55
 **/
public class ResultUtil {

    public static ResultDTO Success(Object object) {
        ResultDTO resultDto = new ResultDTO();
        resultDto.setData(object);
        resultDto.setMsg("成功");
        resultDto.setCode("200");
        return resultDto;
    }

    public static ResultDTO Success(Object object,Integer count) {
        ResultDTO resultDto = new ResultDTO();
        resultDto.setData(object);
        resultDto.setMsg("成功");
        resultDto.setCode("200");
        resultDto.setCount(count);
        return resultDto;
    }

    public static ResultDTO Success() {
        return Success(null);
    }

    public static ResultDTO Error(String code, String msg) {
        ResultDTO resultDto = new ResultDTO();
        resultDto.setMsg(msg);
        resultDto.setCode(code);
        return resultDto;
    }
}
