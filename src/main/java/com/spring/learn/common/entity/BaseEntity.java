package com.spring.learn.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author J.zhu
 * @date 2019/6/1
 */
@Data
public class BaseEntity implements Serializable {

    private Long id;

    private Timestamp createTime;
    
    private Timestamp updateTime;

}
