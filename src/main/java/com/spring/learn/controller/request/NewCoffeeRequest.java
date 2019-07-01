package com.spring.learn.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.money.Money;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author J.zhu
 * @date 2019/7/1
 */
@Getter
@Setter
@ToString
public class NewCoffeeRequest {
    @NotEmpty
    private String name;
    @NotNull
    private Money price;
}
