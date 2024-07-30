package com.safesoft.domain.appointment.equipment.room.impl;

import com.safesoft.commons.IdNameValueObject;
import com.safesoft.commons.ValidateGroup;
import com.safesoft.domain.appointment.equipment.room.RoomInfoService;
import com.safesoft.domain.appointment.equipment.room.entity.RoomInfoEntity;
import com.safesoft.domain.appointment.equipment.room.param.RoomInfoCondition;
import com.safesoft.domain.appointment.equipment.room.repository.RoomInfoRepository;
import com.safesoft.domain.appointment.equipment.room.vo.RoomInfoVO;
import com.safesoft.domain.appointment.equipment.room.vo.RoomMonitorVO;
import com.safesoft.domain.common.ReturnInfo;
import com.safesoft.domains.AbstractBaseServiceImpl;
import com.safesoft.domains.ValidateResult;
import com.safesoft.web.JsonPagedVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Chris.Liu
 * @date 2024/7/16
 * @apiNote 房间信息管理-服务层实现
 */
@Service
public class RoomInfoServiceImpl extends AbstractBaseServiceImpl implements RoomInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomInfoServiceImpl.class);

    private final RoomInfoRepository roomInfoRepository;

    public RoomInfoServiceImpl(RoomInfoRepository roomInfoRepository) {
        this.roomInfoRepository = roomInfoRepository;
    }

    @Override
    public JsonPagedVO<RoomInfoVO> queryRoomInfoPage(RoomInfoCondition condition) {
        LOGGER.info("查询房间信息分页，condition:{}", condition);
        final long total = roomInfoRepository.queryRoomInfoTotal(condition);
        final List<RoomInfoVO> list = total > 0 ? roomInfoRepository.queryRoomInfoPage(condition) : Collections.emptyList();

        return new JsonPagedVO<>(list, total);
    }

    @Override
    public RoomInfoVO queryRoomInfoById(Long id) {
        LOGGER.info("查询房间信息，id:{}", id);

        final RoomInfoVO roomInfo = roomInfoRepository.queryRoomInfoById(id);
        if (Objects.nonNull(roomInfo)) {
            final List<RoomMonitorVO> roomMonitorList = roomInfoRepository.queryRoomMonitorList(id);
            roomInfo.setMonitorList(CollectionUtils.isEmpty(roomMonitorList) ? Collections.emptyList() : roomMonitorList);
        }

        return roomInfo;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ReturnInfo<Void> saveRoomInfo(RoomInfoVO roomInfo) {
        LOGGER.info("保存房间信息，roomInfo:{}", roomInfo);

        // 入参校验
        final ValidateResult validateResult = super.beanValidator(roomInfo, ValidateGroup.Save.class);
        if (Boolean.TRUE.equals(validateResult.getHasError())) {
            LOGGER.error("入参校验失败，validateResult:{}", validateResult);
            return ReturnInfo.failure(validateResult.getError());
        }

        final RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
        BeanUtils.copyProperties(roomInfo, roomInfoEntity);
        // 校验房间名称是否存在
        final int checkExistsRes = roomInfoRepository.checkRoomNameExists(roomInfoEntity);
        if (checkExistsRes > 0) {
            LOGGER.error("房间名称已存在，roomInfo:{}", roomInfo);
            return ReturnInfo.failure("房间名称已存在");
        }

        // 保存房间信息
        final int saveRes = roomInfoRepository.insertRoom(roomInfoEntity);
        if (saveRes == 0) {
            LOGGER.error("保存房间信息失败，roomInfo:{}", roomInfo);
            return ReturnInfo.failure("保存房间信息失败");
        }

        // 保存监控信息
        if (!CollectionUtils.isEmpty(roomInfo.getMonitorList())) {
            roomInfoRepository.batchInsertRoomMonitor(roomInfo.getMonitorList(), roomInfoEntity.getId());
        }

        return ReturnInfo.ok();
    }

    @Override
    public ReturnInfo<Void> updateRoomInfo(RoomInfoVO roomInfo) {
        LOGGER.info("更新房间信息，roomInfo:{}", roomInfo);

        // 入参校验
        final ValidateResult validateResult = super.beanValidator(roomInfo, ValidateGroup.Update.class);
        if (Boolean.TRUE.equals(validateResult.getHasError())) {
            LOGGER.error("入参校验失败，validateResult:{}", validateResult);
            return ReturnInfo.failure(validateResult.getError());
        }

        final RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
        BeanUtils.copyProperties(roomInfo, roomInfoEntity);
        // 校验房间名称是否存在
        final int checkExistsRes = roomInfoRepository.checkRoomNameExists(roomInfoEntity);
        if (checkExistsRes > 0) {
            LOGGER.error("房间名称已存在，roomInfo:{}", roomInfo);
            return ReturnInfo.failure("房间名称已存在");
        }

        // 更新房间信息
        final int updateRes = roomInfoRepository.updateRoom(roomInfoEntity);
        if (updateRes == 0) {
            LOGGER.error("更新房间信息失败，roomInfo:{}", roomInfo);
            return ReturnInfo.failure("更新房间信息失败");
        }

        // 保存监控信息并删除原监控信息
        roomInfoRepository.deleteRoomMonitor(roomInfoEntity.getId());
        if (!CollectionUtils.isEmpty(roomInfo.getMonitorList())) {
            roomInfoRepository.batchInsertRoomMonitor(roomInfo.getMonitorList(), roomInfoEntity.getId());
        }

        return ReturnInfo.ok();
    }

    @Override
    public ReturnInfo<Void> deleteRoomInfo(Long id, Long currentUserId) {
        LOGGER.info("删除房间信息，id:{}", id);

        final int delRes = roomInfoRepository.deleteRoom(id, currentUserId);
        return delRes > 0 ? ReturnInfo.ok() : ReturnInfo.failure("删除房间信息失败");
    }

    @Override
    public List<IdNameValueObject> queryRoomDropDown() {
        LOGGER.info("查询房间下拉列表");

        return roomInfoRepository.queryRoomDropDown();
    }
}
