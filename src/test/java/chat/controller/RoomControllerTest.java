package chat.controller;

import chat.ChatApp;
import chat.model.Room;
import chat.repository.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * RoomControllerTest.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/18/2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatApp.class)
@AutoConfigureMockMvc
public class RoomControllerTest {
    @MockBean
    private RoomRepository rooms;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper jo;

    @Test
    public void findAll() throws Exception {
        final String substring = "{\"id\":1,\"created\":\"2020-07-13T13:09:00\",\"author\":\"user\"}";
        final LocalDateTime created = LocalDateTime.parse("2020-07-13T13:09:00");
        final Room room = new Room(1, created, "user");
        when(this.rooms.findAll()).thenReturn(List.of(room));
        this.mvc.perform(get("/room/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(substring)));
    }

    @Test
    public void findById() throws Exception {
        final String stub = "{\"id\":1,\"created\":\"2020-07-13T13:09:00\",\"author\":\"user\"}";
        final LocalDateTime created = LocalDateTime.parse("2020-07-13T13:09:00");
        final Room room = new Room(1, created, "user");
        when(this.rooms.findById(1)).thenReturn(Optional.of(room));
        this.mvc.perform(get("/room/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(stub)));
    }

    @Test
    public void create() throws Exception {
        final LocalDateTime created = LocalDateTime.parse("2020-07-13T13:09:00");
        final Room room = new Room(1, created, "NEW");
        this.mvc.perform(post("/room/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.jo.writeValueAsString(room)))
                .andExpect(status().isCreated());
        ArgumentCaptor<Room> argument = ArgumentCaptor.forClass(Room.class);
        verify(this.rooms).save(argument.capture());
        final String author = argument.getValue().getAuthor();
        assertEquals("NEW", author);
    }

    @Test
    public void remove() throws Exception {
        final LocalDateTime created = LocalDateTime.parse("2020-07-13T13:09:00");
        final Room room = new Room(1, created, "NEW");
        when(this.rooms.findById(1)).thenReturn(Optional.of(room));
        this.mvc.perform(delete("/room/{id}", room.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        ArgumentCaptor<Room> argument = ArgumentCaptor.forClass(Room.class);
        verify(this.rooms).delete(argument.capture());
    }

    @Test
    public void update() throws Exception {
        final String stub = "{\"id\":1,\"created\":\"2020-07-13T13:09:00\",\"author\":\"UPDATE\"}";
        final LocalDateTime created = LocalDateTime.parse("2020-07-13T13:09:00");
        final Room room = new Room(1, created, "ROOM");
        final Room update = new Room(1, created, "UPDATE");
        when(this.rooms.findById(room.getId())).thenReturn(Optional.of(room));
        this.mvc.perform(put("/room/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.jo.writeValueAsString(update)))
                .andExpect(status().isOk());
        ArgumentCaptor<Room> argument = ArgumentCaptor.forClass(Room.class);
        verify(this.rooms).save(argument.capture());
        final String author = argument.getValue().getAuthor();
        assertEquals("UPDATE", author);
    }
}
