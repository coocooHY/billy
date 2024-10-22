package com.javalab.board.vo;

import lombok.Data;

@Data
public class UserRolesVo {
    private String userId;     // 사용자 ID
    private String userType;   // 사용자 유형 (예: 'person', 'company', 'admin')
    private String roleId;     // 권한 ID
}
