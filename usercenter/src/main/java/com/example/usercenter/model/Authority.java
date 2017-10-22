package com.example.usercenter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ZXN on 2017/10/21.
 */
@Entity
@Table(name="authority")
public class Authority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="authority_id")
    private Long authorityId;
    @Column(name="authority_name")
    private String authorityName;
    @Column(name="authority_code")
    private String authorityCode;
    @Column(name="create_time")
    private Date createTime;

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthorityCode() {
        return authorityCode;
    }

    public void setAuthorityCode(String authorityCode) {
        this.authorityCode = authorityCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
