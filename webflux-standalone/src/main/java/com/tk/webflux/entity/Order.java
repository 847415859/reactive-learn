package com.tk.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Date : 2023/04/12 16:06
 * @Auther : tiankun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private String name;
}
