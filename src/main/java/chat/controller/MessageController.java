package chat.controller;

import chat.model.Message;
import chat.model.Room;
import chat.repository.MessageRepository;
import chat.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * MessageController.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/17/2020
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageRepository messages;
    private final RoomRepository rooms;

    public MessageController(final MessageRepository messages, final RoomRepository rooms) {
        this.messages = messages;
        this.rooms = rooms;
    }

    @GetMapping("/")
    public List<Message> findAll() {
        return (List<Message>) this.messages.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Message> add(@RequestParam("id") final int id,
                                       @RequestParam("desc") final String desc,
                                       @RequestParam("name") final String name) {
        var answer = new ResponseEntity<>(new Message(), HttpStatus.NOT_FOUND);
        final Message message = new Message();
        message.setCreated(LocalDateTime.now());
        message.setAuthor(name);
        message.setDescription(desc);
        final Room room = this.rooms.findById(id).orElse(null);
        if (room != null) {
            message.setRoom(room);
            final Message saved = this.messages.save(message);
            answer = new ResponseEntity<>(saved, HttpStatus.OK);
        }
        return answer;
    }

    @PutMapping("/")
    public ResponseEntity<Message> update(@RequestBody final Message msg) {
        var answer = new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        final Message message = this.messages.findById(msg.getId()).orElse(null);
        if (message != null) {
            message.setAuthor(msg.getAuthor());
            message.setDescription(msg.getDescription());
            message.setCreated(LocalDateTime.now());
            answer = new ResponseEntity<>(this.messages.save(message), HttpStatus.OK);
        }
        return answer;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final int id) {
        final Optional<Message> message = this.messages.findById(id);
        message.ifPresent(this.messages::delete);
        return message.isPresent()
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
