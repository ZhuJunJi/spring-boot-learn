package com.spring.learn.common.entity;

import lombok.*;
import org.joda.money.Money;

/**
 *
 * @author J.zhu
 * @date 2019/7/1
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Coffee extends BaseEntity{

    private String name;

    private Money price;

}
