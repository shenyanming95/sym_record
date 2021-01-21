package com.sym.serialization.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 普通的实例类
 *
 * Created by shenym on 2019/12/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BankEntity {
    private Long id;
    private String bankCode;
    private String bankName;
    private Boolean effective;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
