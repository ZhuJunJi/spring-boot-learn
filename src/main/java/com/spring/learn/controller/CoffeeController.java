package com.spring.learn.controller;

import com.spring.learn.common.entity.Coffee;
import com.spring.learn.common.entity.Result;
import com.spring.learn.controller.request.NewCoffeeRequest;
import com.spring.learn.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author J.zhu
 * @date 2019/7/1
 */
@Controller
@RequestMapping("/coffee")
@Slf4j
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping(value = "/get/{id}")
    @ResponseBody
    public Result<Coffee> get(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        return Result.newSuccessResult(coffee);
    }


    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Result addJsonCoffeeWithoutBindingResult(@Valid @RequestBody NewCoffeeRequest newCoffee) {
        coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
        return Result.newSuccessResult();
    }
}
