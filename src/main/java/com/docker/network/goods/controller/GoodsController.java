package com.docker.network.goods.controller;

import com.docker.network.goods.entity.Goods;
import com.docker.network.goods.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IBookService bookService;

    @GetMapping("/index")
    public String index() {
        return "首页";
    }

    @GetMapping("/findById/{id}")
    public Goods findById(@PathVariable("id") Long id) {
        Goods goods = bookService.findById(id);
        return goods;
    }

    @GetMapping("/save")
    public Goods findById() {
        Goods goods = Goods.builder()
                .code(UUID.randomUUID().toString().substring(0, 5))
                .name("手机")
                .money(8999.99).build();
        Goods goods1 = bookService.save(goods);
        return goods1;
    }

}
