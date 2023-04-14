package com.tk.reactiveredis.entity;

import lombok.Data;

/**
 * @Description:
 * @Date : 2023/04/14 19:26
 * @Auther : tiankun
 */
@Data
public class City {

    private Long id;
    // 省份编号
    private Long provinceId;
    // 城市名称
    private String cityName;
    // 描述
    private String description;
}
