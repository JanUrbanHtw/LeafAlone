package group11.leafalone.Auth;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "leafAloneUser")
public class LeafAloneUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @NotEmpty
    @Column(name="password")
    private String password;

    @Column(name = "role")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}