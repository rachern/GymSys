package team.rjgc.GymSys.mapper.area;

import org.apache.ibatis.annotations.Param;
import team.rjgc.GymSys.entity.area.AreaBook;
import team.rjgc.GymSys.entity.area.AreaUse;

import java.time.LocalDateTime;
import java.util.List;

public interface AreaMapper {
//    判断某个场地/某个时间段的租用情况
    List<AreaBook> effectiveBook(@Param("area_id") Integer area_id, @Param("book_start_time")LocalDateTime book_start_time, @Param("book_end_time")LocalDateTime book_end_time);

//    获取失约的场地预约
    List<AreaBook> breakBook();

    List<AreaUse> history(@Param("user_id")Long userId);
}
