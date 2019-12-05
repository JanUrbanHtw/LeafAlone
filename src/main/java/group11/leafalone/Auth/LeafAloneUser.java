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

    @Column(name="username", unique = true)
    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty (message = "Password is required")
    @Column(name="password")
    private String password;

    @Column(name = "role")
    private String role;

    @Transient
    @NotEmpty (message = "Confirmation is required")
    private String confirmPassword;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    protected LeafAloneUser(){}

    public LeafAloneUser(@NotEmpty(message = "Username is required") String username, @NotEmpty(message = "Password is required") String password, String role, @NotEmpty(message = "Confirmation is required") String confirmPassword) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.confirmPassword = confirmPassword;
    }
}