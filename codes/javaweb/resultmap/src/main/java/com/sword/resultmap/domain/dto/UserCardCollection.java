package com.sword.resultmap.domain.dto;

import com.sword.resultmap.domain.po.User;
import lombok.Data;

import java.util.List;

@Data
public class UserCardCollection extends User {
    private List<Card> cards;
}
