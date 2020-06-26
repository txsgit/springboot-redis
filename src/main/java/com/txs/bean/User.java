package com.txs.bean;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor  //全参构造方法
@NoArgsConstructor   //无参构造方法
@ToString
public class User {

    private Long id;
    private String name;
    private Integer age;
    private Date birth;
}
