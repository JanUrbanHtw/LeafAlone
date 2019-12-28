package group11.leafalone.Auth;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "leaf_alone_user")
public class LeafAloneUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    @Column(name = "password")
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty(message = "Email is required")
    private String email;

    @Column(name = "role")
    private String role;

    @Transient
    @NotEmpty(message = "Confirmation is required")
    private String confirmPassword;

    public LeafAloneUser() {
    }

    public LeafAloneUser(@NotEmpty(message = "Username is required") String username, @NotEmpty(message = "Password is required") String password, String role, @NotEmpty(message = "Email is required") String email, @NotEmpty(message = "Confirmation is required") String confirmPassword) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.confirmPassword = confirmPassword;
    }

    public static class Builder {
        private Long id;
        private String username;
        private String password;
        private String role;
        private String confirmPassword;

        public Builder withId(Long id) {
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

        public Builder withRole(String role) {
            this.role = role;
            return this;
        }

        public Builder withConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public LeafAloneUser build() {
            LeafAloneUser leafAloneUser = new LeafAloneUser();
            leafAloneUser.setId(this.id);
            leafAloneUser.setUsername(this.username);
            leafAloneUser.setPassword(this.password);
            leafAloneUser.setRole(this.role);
            leafAloneUser.setConfirmPassword(this.confirmPassword);
            return leafAloneUser;
        }
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}