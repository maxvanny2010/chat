package chat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Message.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 6/17/2020
 */
@Entity
@Table(name = "message")
public class Message {
    /**
     * field a id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private Integer id;
    /**
     * field a description.
     */
    private String description;
    /**
     * field a time of create.
     */
    private LocalDateTime created;

    /**
     * field a author.
     */
    private String author;

    /**
     * field a post.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id",
            foreignKey = @ForeignKey(name = "room_id_fk"),
            nullable = false)
    private Room room;

    /**
     * Constructor.
     */
    public Message() {
    }

    /**
     * Constructor.
     *
     * @param aId       id
     * @param aDesc     description
     * @param aDateTime date and time a create
     * @param aAuthor   a author
     */
    public Message(final Integer aId, final String aDesc,
                   final LocalDateTime aDateTime,
                   final String aAuthor) {
        this.id = aId;
        this.description = aDesc;
        this.created = aDateTime;
        this.author = aAuthor;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(final Room post) {
        this.room = post;
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
     **/
    public void setId(final Integer aId) {
        this.id = aId;
    }

    /**
     * Method to get.
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Method to set.
     *
     * @param aDesc description
     **/
    public void setDescription(final String aDesc) {
        this.description = aDesc;
    }

    /**
     * Method to get.
     *
     * @return date and time
     */
    public LocalDateTime getCreated() {
        return this.created;
    }

    /**
     * Method to set.
     *
     * @param aDateTime date and time create
     **/
    public void setCreated(final LocalDateTime aDateTime) {
        this.created = aDateTime;
    }

    /**
     * Method to check post.
     *
     * @return is new post or not
     */
    public boolean isNew() {
        return Objects.isNull(this.id);
    }

    @Override
    public final String toString() {
        return new StringJoiner(", ",
                "\n" + Message.class.getSimpleName() + "[", "]")
                .add("id=" + this.id)
                .add("desc='" + this.description + "'")
                .add("dateTime=" + this.created)
                .add("author=" + this.author)
                .toString();
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        final Message message = (Message) o;
        return Objects.equals(getId(), message.getId())
                && Objects.equals(getDescription(), message.getDescription())
                && Objects.equals(getCreated(), message.getCreated())
                && Objects.equals(getAuthor(), message.getAuthor());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getDescription(), getCreated(), getAuthor());
    }
}
