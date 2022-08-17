package com.docker.network.goods.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_goods")
public class Goods implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name",columnDefinition = "varchar(255) COMMENT '商品名称'")
    private String name;

    @Column(name = "code",columnDefinition = "varchar(64) COMMENT '商品编码'")
    private String code;

    @Column(name = "money",columnDefinition = "varchar(64) COMMENT '商品价格'")
    private Double money;

}


