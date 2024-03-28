package com.sword.crud.controller;

import cn.hutool.core.bean.BeanUtil;
import com.sword.crud.domain.po.Address;
import com.sword.crud.domain.vo.AddressVO;
import com.sword.crud.service.IAddressService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("address")
@RestController
@RequiredArgsConstructor
public class AddressController {
    private final IAddressService iAddressService;
    @GetMapping("{id}")
    public AddressVO getById(@PathVariable Long id){
        Address address = iAddressService.getById(id);
        return BeanUtil.copyProperties(address, AddressVO.class);
        // ==>  Preparing: SELECT id,user_id,province,city,town,mobile,street,contact,is_default,notes,deleted FROM address WHERE id=?
    }
}
