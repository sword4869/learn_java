package com.safesoft.domain.appointment.equipment.room;

import com.safesoft.commons.IdNameValueObject;
import com.safesoft.domain.appointment.equipment.room.param.RoomInfoCondition;
import com.safesoft.domain.appointment.equipment.room.vo.RoomInfoVO;
import com.safesoft.domain.common.ReturnInfo;
import com.safesoft.web.JsonPagedVO;

import java.util.List;

/**
 * @author Chris.Liu
 * @description 房间信息管理-服务层
 * @date 2024/7/16
 */
public interface RoomInfoService {

    /**
     * 查询房间信息分页
     *
     * @param condition 分页查询条件
     * @return 房间信息分页
     */
    JsonPagedVO<RoomInfoVO> queryRoomInfoPage(RoomInfoCondition condition);

    /**
     * 根据id查询房间信息详情
     *
     * @param id 房间id
     * @return 房间信息详情
     */
    RoomInfoVO queryRoomInfoById(Long id);

    /**
     * 保存房间信息
     *
     * @param roomInfo 房间信息
     * @return 保存结果
     */
    ReturnInfo<Void> saveRoomInfo(RoomInfoVO roomInfo);

    /**
     * 更新房间信息
     *
     * @param roomInfo 房间信息
     * @return 操作结果
     */
    ReturnInfo<Void> updateRoomInfo(RoomInfoVO roomInfo);

    /**
     * 删除房间信息
     *
     * @param id            房间id
     * @param currentUserId 当前用户id
     * @return 操作结果
     */
    ReturnInfo<Void> deleteRoomInfo(Long id, Long currentUserId);

    /**
     * 查询房间下拉列表
     *
     * @return 房间下拉列表
     */
    List<IdNameValueObject> queryRoomDropDown();
}
