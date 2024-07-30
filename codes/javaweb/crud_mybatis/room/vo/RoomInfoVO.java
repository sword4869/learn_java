package com.safesoft.domain.appointment.equipment.room.vo;

import com.safesoft.commons.AbstractUpdateTraceValueObject;
import com.safesoft.commons.ValidateGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author Chris.Liu
 * @date 2024/7/16
 * @apiNote 房间信息实体
 */
@Getter
@Setter
@ToString(callSuper = true)
public class RoomInfoVO extends AbstractUpdateTraceValueObject {

    /**
     * 房间名称
     */
    @NotBlank(groups = {ValidateGroup.Save.class, ValidateGroup.Update.class})
    @Length(max = 50, groups = {ValidateGroup.Save.class, ValidateGroup.Update.class})
    private String roomName;

    /**
     * 房间描述
     */
    @Length(max = 500, groups = {ValidateGroup.Save.class, ValidateGroup.Update.class})
    private String roomDescription;

    /**
     * 门禁设备序列号
     */
    @Length(max = 50, groups = {ValidateGroup.Save.class, ValidateGroup.Update.class})
    private String accessControlDeviceSerial;

    /**
     * 监控信息列表
     */
    @Size(max = 10, groups = {ValidateGroup.Save.class, ValidateGroup.Update.class})
    @Valid
    private List<RoomMonitorVO> monitorList;
}
