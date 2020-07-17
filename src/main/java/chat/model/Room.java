package chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Post.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 6/16/2020
 */
@Entity
@Table(name = "room")
public class Room {
    /**
     * field a id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_room")
    private Integer id;

    /**
     * field a time to create.
     */
    private LocalDateTime created;

    /**
     * field a author.
     */
    private String author;
    /**
     * field a messages.
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "room")
    private Set<Message> messages = new HashSet<>();

    /**
     * Constructor.
     */
    public Room() {
    }

    /**
     * Constructor.
     *
     * @param aId      id
     * @param aCreated create date and time
     */
    public Room(final Integer aId, final LocalDateTime aCreated, final String aAuthor) {
        this.id = aId;
        this.created = aCreated;
        this.author = aAuthor;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(final Set<Message> messages) {
        this.messages = messages;
    }

    /**
     * Method to get.
     *
     * @return author
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Method to set.
     *
     * @param aAuthor a author
     **/
    public void setAuthor(final String aAuthor) {
        this.author = aAuthor;
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
     * @param aId id
     */
    public void setId(final Integer aId) {
        this.id = aId;
    }

    /**
     * Method to get.
     *
     * @return a time of create
     */
    public LocalDateTime getCreated() {
        return this.created;
    }

    /**
     * Method to set.
     *
     * @param aCreated created
     **/
    public void setCreated(final LocalDateTime aCreated) {
        this.created = aCreated;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        final Room post = (Room) o;
        return Objects.equals(getId(), post.getId())
                && Objects.equals(getCreated(), post.getCreated())
                && Objects.equals(getAuthor(), post.getAuthor());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getCreated(), getAuthor());
    }

    @Override
    public final String toString() {
        return new StringJoiner(", ",
                "\n" + Room.class.getSimpleName() + "[", "]")
                .add("id=" + this.id)
                .add("created=" + this.created)
                .add("author=" + this.author)
                .add("messages=" + this.messages)
                .toString();
    }
}
