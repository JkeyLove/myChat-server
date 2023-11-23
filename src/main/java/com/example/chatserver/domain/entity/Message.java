package com.example.chatserver.domain.entity;


import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Message)表实体类
 *
 * @author makejava
 * @since 2023-11-22 22:43:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("message")
public class Message  {
    @TableId
    private Long id;


    private String username;

    private String content;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;



}

