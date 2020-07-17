package chat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Authority.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 6/17/2020
 */
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_authority")
    private int id;

    private String authority;

    public Authority() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int aId) {
        this.id = aId;
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(final String aAuthority) {
        this.authority = aAuthority;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Authority)) {
            return false;
        }
        final Authority authority1 = (Authority) o;
        return getId() == authority1.getId()
                && getAuthority().equals(authority1.getAuthority());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getAuthority());
    }
}
