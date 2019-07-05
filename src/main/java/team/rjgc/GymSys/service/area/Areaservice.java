package team.rjgc.GymSys.service.area;

import team.rjgc.GymSys.entity.area.AreaBook;
import team.rjgc.GymSys.entity.area.AreaInfo;
import team.rjgc.GymSys.entity.area.AreaUse;

import java.time.LocalDateTime;
import java.util.List;

public interface Areaservice {
    Void conflict(AreaBook areaBook);

    List<AreaBook> selectBookedById(Integer id);

    List<AreaInfo> selectWithoutBookedByTime(LocalDateTime startTime, LocalDateTime endTime);

    List<AreaBook> breakBook();

    List<AreaUse> history(Long userId);
}
