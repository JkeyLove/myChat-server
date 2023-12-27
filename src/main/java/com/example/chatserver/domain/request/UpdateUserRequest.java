package com.example.chatserver.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    String username;

    /**
     * 0设为非会员
     * 1设为会员
     */
    Integer status;

}
