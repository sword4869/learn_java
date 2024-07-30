package com.safesoft.web.appointment.equipment;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.safesoft.commons.IdNameValueObject;
import com.safesoft.commons.SingleIdParam;
import com.safesoft.commons.ValidateGroup;
import com.safesoft.domain.appointment.equipment.room.RoomInfoService;
import com.safesoft.domain.appointment.equipment.room.param.RoomInfoCondition;
import com.safesoft.domain.appointment.equipment.room.vo.RoomInfoVO;
import com.safesoft.domain.common.ReturnInfo;
import com.safesoft.web.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Chris.Liu
 * @date 2024/7/19
 * @apiNote 房间信息管理-控制层
 */
@SaCheckLogin
@RestController
@RequestMapping(value = WebURIMappingConstant.REQUEST_MAPPING_ROOM_INFO)
public class EquipmentRoomInfoController extends AbstractSessionSupportController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentRoomInfoController.class);

    private final RoomInfoService roomInfoService;

    public EquipmentRoomInfoController(RoomInfoService roomInfoService) {
        this.roomInfoService = roomInfoService;
    }

    /**
     * 查询房间信息分页
     */
    @SaCheckPermission(PermissionConstant.PERMISSION_ROOM_INFO)
    @PostMapping(WebURIMappingConstant.PAGE)
    public Rest<JsonPagedVO<RoomInfoVO>> queryRoomInfoPage(@RequestBody @Validated RoomInfoCondition condition) {
        LOGGER.info("用户：{}，查询房间信息分页:{}", getCurrentUserId(), condition);

        return RestBody.okData(roomInfoService.queryRoomInfoPage(condition));
    }

    /**
     * 查询房间信息详情
     */
    @GetMapping(WebURIMappingConstant.VIEW + "/{id}")
    public Rest<RoomInfoVO> queryRoomDetailById(@PathVariable Long id) {
        LOGGER.info("用户：{}，查询房间信息详情:{}", getCurrentUserId(), id);

        return RestBody.okData(roomInfoService.queryRoomInfoById(id));
    }

    /**
     * 保存房间信息
     */
    @SaCheckPermission(PermissionConstant.PERMISSION_ROOM_INFO)
    @PostMapping
    public Rest<Void> saveRoomInfo(@RequestBody @Validated(ValidateGroup.Save.class) RoomInfoVO roomInfo) {
        LOGGER.info("用户：{}，保存房间信息:{}", getCurrentUserId(), roomInfo);

        roomInfo.setCreatedUserId(getCurrentUserId());
        roomInfo.setUpdatedUserId(getCurrentUserId());
        final ReturnInfo<Void> returnInfo = roomInfoService.saveRoomInfo(roomInfo);

        return returnInfo.isSuccess() ? RestBody.ok() : RestBody.failure(HttpStatus.BAD_REQUEST.value(), returnInfo.getMsg());
    }

    /**
     * 更新房间信息
     */
    @SaCheckPermission(PermissionConstant.PERMISSION_ROOM_INFO)
    @PutMapping
    public Rest<Void> updateRoomInfo(@RequestBody @Validated(ValidateGroup.Update.class) RoomInfoVO roomInfo) {
        LOGGER.info("用户：{}，更新房间信息:{}", getCurrentUserId(), roomInfo);

        roomInfo.setUpdatedUserId(getCurrentUserId());
        final ReturnInfo<Void> returnInfo = roomInfoService.updateRoomInfo(roomInfo);

        return returnInfo.isSuccess() ? RestBody.ok() : RestBody.failure(HttpStatus.BAD_REQUEST.value(), returnInfo.getMsg());
    }

    /**
     * 删除房间信息
     */
    @SaCheckPermission(PermissionConstant.PERMISSION_ROOM_INFO)
    @DeleteMapping
    public Rest<Void> deleteRoomInfo(@Validated @RequestBody SingleIdParam param) {
        LOGGER.info("用户：{}，删除房间信息:{}", getCurrentUserId(), param.getId());

        final ReturnInfo<Void> returnInfo = roomInfoService.deleteRoomInfo(param.getId(), getCurrentUserId());

        return returnInfo.isSuccess() ? RestBody.ok() : RestBody.failure(HttpStatus.BAD_REQUEST.value(), returnInfo.getMsg());
    }

    /**
     * 查询房间下拉框
     */
    @GetMapping(WebURIMappingConstant.LIST)
    public Rest<List<IdNameValueObject>> queryRoomDropDown() {
        LOGGER.info("用户：{}，查询房间下拉框", getCurrentUserId());

        return RestBody.okData(roomInfoService.queryRoomDropDown());
    }
}
