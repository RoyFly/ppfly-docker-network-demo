package com.docker.network.goods.service.impl;

import com.docker.network.goods.entity.Goods;
import com.docker.network.goods.repository.GoodsRepository;
import com.docker.network.goods.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class BookServiceImpl implements IBookService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String GOODID = "goods:";

    @Override
    public Goods findById(Long id) {
        //在高并发条件下，会出现缓存穿透
        Goods goods = (Goods) redisTemplate.opsForValue().get(GOODID + id);
        if (null == goods) {
            //5个线程， 4个等，1个进入
            synchronized (this) {
                //双重检测锁,假使同时有5个请求进入了上一个if(null == goods),
                //加了锁之后one by one 的访问,这里再次对缓存进行检测,尽一切可能防止缓存穿透的产生,但是性能会有所损失
                goods = (Goods) redisTemplate.opsForValue().get(GOODID + id);
                if (null == goods) {
                    Optional<Goods> optional = goodsRepository.findById(id);
                    if (optional.isPresent()) {
                        goods = optional.get();
                        redisTemplate.opsForValue().set(GOODID + id, optional.get());
                    } else {
                        //redisTemplate.opsForValue().set(GOODID + id, "{}", 100, TimeUnit.MILLISECONDS);
                    }
                    log.info("缓存中没有该商品{},请求的数据库。。。。。。", id);
                } else {
                    log.info("高并发场景，缓存已被设置。。。。。。");
                }
            }
        } else {
            log.info("请求的缓存。。。。。。");
        }
        return goods;
    }


    //@see https://blog.csdn.net/xnian_/article/details/117065782
    @Override
    public Goods save(Goods goods) {
        Goods newGoods = goodsRepository.save(goods);
        redisTemplate.opsForValue().set(GOODID + newGoods.getId(), newGoods);
        return newGoods;
    }

}
