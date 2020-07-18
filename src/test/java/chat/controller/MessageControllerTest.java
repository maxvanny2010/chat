package chat.controller;

import chat.ChatApp;
import chat.model.Message;
import chat.model.Room;
import chat.repository.MessageRepository;
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
import static org.junit.Assert.assertNull;
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
 * MessageControllerTest.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/18/2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatApp.class)
@AutoConfigureMockMvc
public class MessageControllerTest {
    @MockBean
    private RoomRepository rooms;
    @MockBean
    private MessageRepository msg;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper jo;

    @Test
    public void findAll() throws Exception {
        final LocalDateTime created = LocalDateTime.parse("2020-07-13T13:09:00");
        final String subMsg = "[{\"id\":1,\"description\":\"FIND ALL\",\"created\":\"2020-07-13T13:09:00\",\"author\":\"user\",\"room\":null}]";
        final Message msg = new Message(1, "FIND ALL", created, "user");

        when(this.msg.findAll()).thenReturn(List.of(msg));
        this.mvc.perform(get("/message/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(subMsg)));
    }

    @Test
    public void add() throws Exception {
        final LocalDateTime created = LocalDateTime.parse("2020-07-13T13:09:00");
        final Room room = new Room(1, created, "user");

        when(this.rooms.findById(1)).thenReturn(Optional.of(room));
        this.mvc.perform(post("/message/")
                .param("id", "1")
                .param("desc", "ADD")
                .param("name", "NEW"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        final ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);
        verify(this.msg).save(argument.capture());
        final String desc = argument.getValue().getDescription();
        assertEquals("ADD", desc);
    }

    @Test
    public void update() throws Exception {
        final LocalDateTime created = LocalDateTime.parse("2020-07-13T13:09:00");
        final Message msg = new Message(1, "MSG", created, "user");
        final Message update = new Message(1, "UPDATE", created, "user");
        when(this.msg.findById(msg.getId())).thenReturn(Optional.of(msg));
        this.mvc.perform(put("/message/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.jo.writeValueAsString(update)))
                .andDo(print())
                .andExpect(status().isOk());
        ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);
        verify(this.msg).save(argument.capture());
        final String result = argument.getValue().getDescription();
        assertEquals("UPDATE", result);
    }

    @Test
    public void whenDeleteIsOk() throws Exception {
        final LocalDateTime created = LocalDateTime.parse("2020-07-13T13:09:00");
        final Message msg = new Message(1, "MSG", created, "user");
        when(this.msg.findById(msg.getId())).thenReturn(Optional.of(msg));
        this.mvc.perform(delete("/message/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
