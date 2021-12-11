package com.wsb.taskmanager.authentication.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user", schema = "taskmanager")
@SequenceGenerator(name = UserBE.SEQ_NAME, sequenceName = UserBE.SEQ_NAME, allocationSize = 1)
public class UserBE {
    private static final long serialVersionUID = 1;
    public static final String SEQ_NAME = "sq_user";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = UserBE.SEQ_NAME)
    private long id;

    @NotBlank
    @Column(unique = true)
    @Size(max = 20)
    private String username;

    @NotBlank
    @Column(unique = true)
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoleBE> roles;

    public UserBE() {
    }

    public UserBE(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleBE> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleBE> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBE userBE = (UserBE) o;
        return username.equals(userBE.username) && email.equals(userBE.email) && password.equals(userBE.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }

    public static final class Builder {
        private long id;
        private String username;
        private String email;
        private String password;
        private Set<Role> roles = new HashSet<>();

        private Builder() {

        }

        public static Builder aUserBE() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withRoles(Set<Role> roles) {
            if (roles != null) {
                this.roles.addAll(roles);

            }
            return this;
        }

        public UserBE build() {
            UserBE userBE = new UserBE();
            userBE.setId(this.id);
            userBE.setUsername(this.username);
            userBE.setEmail(this.email);
            userBE.setPassword(this.password);
            userBE.setRoles(roles.stream().map(roleName -> new RoleBE(userBE, roleName)).collect(Collectors.toSet()));
            return userBE;
        }
    }
}
