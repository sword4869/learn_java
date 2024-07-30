package com.safesoft.domain.appointment.equipment.room.repository;

import com.safesoft.commons.IdNameValueObject;
import com.safesoft.domain.appointment.equipment.room.entity.RoomInfoEntity;
import com.safesoft.domain.appointment.equipment.room.param.RoomInfoCondition;
import com.safesoft.domain.appointment.equipment.room.vo.RoomInfoVO;
import com.safesoft.domain.appointment.equipment.room.vo.RoomMonitorVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Chris.Liu
 * @description 房间管理持久层
 * @date 2024/7/16
 */
@Mapper
@Repository
public interface RoomInfoRepository {

    /**
     * 查询房间信息总数
     *
     * @param condition 房间分页查询条件
     * @return 房间总数
     */
    long queryRoomInfoTotal(RoomInfoCondition condition);

    /**
     * 查询房间信息分页
     *
     * @param roomInfoCondition 房间分页查询条件
     * @return 房间信息分页
     */
    List<RoomInfoVO> queryRoomInfoPage(RoomInfoCondition roomInfoCondition);

    /**
     * 根据房间id查询房间信息
     *
     * @param id 房间id
     * @return 房间信息
     */
    RoomInfoVO queryRoomInfoById(Long id);

    /**
     * 根据房间id查询房间监控信息
     *
     * @param roomId 房间id
     * @return 房间监控信息
     */
    List<RoomMonitorVO> queryRoomMonitorList(Long roomId);

    /**
     * 新增房间信息
     *
     * @param roomInfo 房间信息
     * @return 新增结果
     */
    int insertRoom(RoomInfoEntity roomInfo);

    /**
     * 修改房间信息
     */
    int updateRoom(RoomInfoEntity roomInfo);

    /**
     * 删除房间信息
     *
     * @param id            房间id
     * @param currentUserId 操作人id
     * @return 删除结果
     */
    int deleteRoom(Long id, Long currentUserId);

    /**
     * 批量新增房间监控信息
     *
     * @param roomMonitorVOList 房间监控信息
     */
    void batchInsertRoomMonitor(@Param("monitorList") List<RoomMonitorVO> roomMonitorVOList, @Param("roomId") Long roomId);

    /**
     * 删除房间监控信息
     *
     * @param roomId 房间id
     */
    void deleteRoomMonitor(Long roomId);

    /**
     * 检查房间名称是否存在
     *
     * @param roomInfo 房间信息
     * @return 存在结果
     */
    int checkRoomNameExists(RoomInfoEntity roomInfo);

    /**
     * 查询房间下拉列表
     *
     * @return 房间下拉列表
     */
    List<IdNameValueObject> queryRoomDropDown();
}
