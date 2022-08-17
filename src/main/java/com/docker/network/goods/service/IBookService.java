package com.docker.network.goods.service;

import com.docker.network.goods.entity.Goods;

import java.util.Optional;

public interface IBookService {

    Goods findById(Long id);

    Goods save(Goods goods);
}
