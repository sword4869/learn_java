package com.safesoft.domain.appointment.equipment.room.param;

import com.safesoft.domains.AbstractPaginationQueryCondition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Chris.Liu
 * @date 2024/7/16
 * @apiNote TODO
 */
@Getter
@Setter
@ToString(callSuper = true)
public class RoomInfoCondition extends AbstractPaginationQueryCondition {

    /**
     * 房间名称
     */
    private String roomName;
}
