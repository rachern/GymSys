package team.rjgc.GymSys.service.area.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.rjgc.GymSys.entity.area.AreaBook;
import team.rjgc.GymSys.entity.area.AreaInfo;
import team.rjgc.GymSys.entity.area.AreaUse;
import team.rjgc.GymSys.mapper.area.AreaBookMapper;
import team.rjgc.GymSys.mapper.area.AreaInfoMapper;
import team.rjgc.GymSys.mapper.area.AreaMapper;
import team.rjgc.GymSys.service.area.Areaservice;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AreaServiceImpl implements Areaservice {
    @Autowired
    private AreaBookMapper areaBookMapper;
    @Autowired
    private AreaInfoMapper areaInfoMapper;
    @Autowired
    private AreaMapper areaMapper;

    //    判断预定某场地是否合理
    @Override
    public Void conflict(AreaBook areaBook) {
        LocalDateTime nowTime = LocalDateTime.now();
        if (areaBook.getBookStartTime() != null) {
            List<AreaBook> areaBooks = areaMapper.effectiveBook(areaBook.getAreaId(), areaBook.getBookStartTime(), areaBook.getBookEndTime());//获得某个场地某个时间段是否有预约信息
            if (areaBooks.size() != 0) {//场地有被预定
                throw new RuntimeException("预定場地衝突");
            }
        }
        return null;
    }

    @Override
    public List<AreaBook> selectBookedById(Integer id) {
        return areaMapper.effectiveBook(id, null, null);//获取有效的场地租用;
    }

    @Override
    public List<AreaInfo> selectWithoutBookedByTime(LocalDateTime startTime, LocalDateTime endTime) {
        List<AreaInfo> areaInfos = areaInfoMapper.selectList(null);//获取全部场地信息
        List<AreaBook> areaBooks = areaMapper.effectiveBook(null, startTime, endTime);//获取有效的场地租用
        for (AreaBook areaBook : areaBooks) {//删除已经在时间段中的数据
            AreaInfo temp = areaInfoMapper.selectById(areaBook.getAreaId());
            areaInfos.remove(temp);
        }
        return areaInfos;
    }

    @Override
    public List<AreaBook> breakBook() {
        return areaMapper.breakBook();
    }

    @Override
    public List<AreaUse> history(Long userId) {
        return areaMapper.history(userId);
    }
}
