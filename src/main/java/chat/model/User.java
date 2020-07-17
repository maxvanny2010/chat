package chat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * User.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 6/17/2020
 */
@Entity
@Table(name = "users")
public class User {
    /**
     * field a id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    /**
     * field a user name.
     */
    private String username;
    /**
     * field a password.
     */
    private String password;

    /**
     * field a password.
     */
    private boolean enable;

    /**
     * field a role.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authority_id", nullable = false)
    private Authority authority;

    /**
     * Constructor.
     */
    public User() {
    }

    /**
     * Constructor.
     *
     * @param aId        id
     * @param aUsername  user name
     * @param aPassword  password
     * @param aAuthority a authority
     */
    public User(final Integer aId, final String aUsername,
                final String aPassword, final Authority aAuthority) {
        this.id = aId;
        this.username = aUsername;
        this.password = aPassword;
        this.authority = aAuthority;
    }

    /**
     * Method to get.
     *
     * @return enable
     */
    public boolean isEnable() {
        return this.enable;
    }

    /**
     * Method to set.
     *
     * @param aEnable a enable of user
     **/
    public void setEnable(final boolean aEnable) {
        this.enable = aEnable;
    }

    /**
     * Method to get.
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Method to set.
     *
     * @param aId a id
     **/
    public void setId(final Integer aId) {
        this.id = aId;
    }

    /**
     * Method to get.
     *
     * @return a user name
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Method to set.
     *
     * @param aUsername a user name
     **/
    public void setUsername(final String aUsername) {
        this.username = aUsername;
    }

    /**
     * Method to get.
     *
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Method to set.
     *
     * @param aPassword a password
     **/
    public void setPassword(final String aPassword) {
        this.password = aPassword;
    }

    /**
     * Method to get.
     *
     * @return a authority
     */
    public Authority getAuthority() {
        return this.authority;
    }

    /**
     * Method to set.
     *
     * @param aAuthority a authority
     **/
    public void setAuthority(final Authority aAuthority) {
        this.authority = aAuthority;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        final User user = (User) o;
        return getId().equals(user.getId())
                && getUsername().equals(user.getUsername())
                && getPassword().equals(user.getPassword())
                && getAuthority() == user.getAuthority();
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getAuthority());
    }

    @Override
    public final String toString() {
        return new StringJoiner(", ",
                User.class.getSimpleName() + "[", "]")
                .add("id=" + this.id)
                .add("username='" + this.username + "'")
                .add("password='" + this.password + "'")
                .add("authority=" + this.authority)
                .toString();
    }
}
