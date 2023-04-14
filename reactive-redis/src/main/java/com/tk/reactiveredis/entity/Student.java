package com.tk.reactiveredis.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Date : 2023/04/14 19:55
 * @Auther : tiankun
 */
@Data
@Table("student")
public class Student {

    @Id
    private Long id;
    private String code;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String address;
    private String remark;
    private boolean active;
    @ReadOnlyProperty
    private LocalDateTime createdAt;
    private String createdBy;
    @ReadOnlyProperty
    private LocalDateTime updatedAt;
    private String updatedBy;
}
