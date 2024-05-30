package com.sword.resultmap.domain.dto;

import com.sword.resultmap.domain.po.User;
import lombok.Data;

@Data
public class UserCardAssociation extends User {
    private Card card;
}
