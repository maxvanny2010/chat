package chat.controller;

import chat.model.Room;
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
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * ChatController.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/17/2020
 */
@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomRepository room;

    public RoomController(final RoomRepository room) {
        this.room = room;
    }

    @GetMapping("/")
    public List<Room> findAll() {
        return (List<Room>) this.room.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable("id") final int id) {
        var room = this.room.findById(id);
        return new ResponseEntity<>(room.orElse(new Room()),
                room.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody final Room room) {
        room.setCreated(LocalDateTime.now());
        return new ResponseEntity<>(this.room.save(room), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") final int id) {
        final Optional<Room> room = this.room.findById(id);
        room.ifPresent(this.room::delete);
        return room.isPresent()
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/")
    public ResponseEntity<Room> update(@RequestBody final Room room) {
        final Room update = this.room.findById(room.getId()).orElse(null);
        if (update != null) {
            update.setAuthor(room.getAuthor());
            update.setCreated(LocalDateTime.now());
            return new ResponseEntity<>(this.room.save(update), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Room(), HttpStatus.NOT_FOUND);
    }
}
