package com.safesoft.domain.appointment.equipment.room.entity;

import com.safesoft.domain.appointment.equipment.room.vo.RoomMonitorVO;
import com.safesoft.domains.AbstractUpdateTraceDomain;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Chris.Liu
 * @date 2024/7/16
 * @apiNote 房间信息实体
 */
@Getter
@Setter
@ToString(callSuper = true)
public class RoomInfoEntity extends AbstractUpdateTraceDomain {

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 房间描述
     */
    private String roomDescription;

    /**
     * 门禁设备序列号
     */
    private String accessControlDeviceSerial;
}
