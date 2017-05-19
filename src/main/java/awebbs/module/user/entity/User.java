package awebbs.module.user.entity;

import awebbs.common.BaseEntity;
import awebbs.module.security.entity.Role;
import awebbs.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zgqq.
 */
@Entity
@Table(name = "awebbs_user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 200796098159096559L;

    @Id
    @GeneratedValue
    private int id;

    //用户名
    @Column(unique = true, nullable = false)
    private String username;

    //密码
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    //头像
    @Column(nullable = false)
    private String avatar;

    //用户邮箱
    private String email;

    //个人签名
    private String signature;

    //个人主页
    private String url;

    //注册时间
    @Column(nullable = false)
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private Date inTime;

    //用户是否被禁用
    private boolean block;

    //用户令牌
    private String token;

    //用户与角色的关联关系
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "awebbs_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
