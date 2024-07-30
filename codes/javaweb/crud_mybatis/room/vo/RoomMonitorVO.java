package com.safesoft.domain.appointment.equipment.room.vo;

import com.safesoft.commons.AbstractUpdateTraceValueObject;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * @author Chris.Liu
 * @date 2024/7/16
 * @apiNote 房间监控信息VO
 */
@Getter
@Setter
@ToString(callSuper = true)
public class RoomMonitorVO extends AbstractUpdateTraceValueObject {

    /**
     * 房间id 对应表t_room_info主键id
     */
    private Long roomId;

    /**
     * 设备序列号
     */
    @NotBlank
    @Length(max = 50)
    private String deviceSerial;

    /**
     * 通道号
     */
    @NotBlank
    @Length(max = 10)
    private String channelNo;
}
