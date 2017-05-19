package awebbs.module.security.entity;

import awebbs.module.user.entity.User;
import awebbs.common.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zgqq.
 */
@Entity
@Table(name = "awebbs_role")
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8293845253380001671L;

    @Id
    @GeneratedValue
    private int id;

    //权限标识
    private String name;

    //权限描述
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    //角色与权限的关联关系
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "awebbs_role_permission",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")}
    )
    private Set<Permission> permissions = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
