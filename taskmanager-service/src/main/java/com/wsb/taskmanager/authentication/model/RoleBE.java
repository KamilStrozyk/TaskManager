package com.wsb.taskmanager.authentication.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "role", schema = "taskmanager")
@SequenceGenerator(name = RoleBE.SEQ_NAME, sequenceName = RoleBE.SEQ_NAME, allocationSize = 1)
public class RoleBE {
    private static final long serialVersionUID = 1;
    public static final String SEQ_NAME = "sq_role";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = RoleBE.SEQ_NAME)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserBE user;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private Role role;

    public RoleBE() {
    }

    public RoleBE(UserBE user, Role role) {
        this.user = user;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserBE getUser() {
        return user;
    }

    public void setUser(UserBE user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleBE roleBE = (RoleBE) o;
        return user.equals(roleBE.user) && role == roleBE.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}
