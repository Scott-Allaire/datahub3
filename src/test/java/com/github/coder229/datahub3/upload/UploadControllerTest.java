package com.github.coder229.datahub3.upload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UploadControllerTest {
    /*
     Content-Disposition: form-data; name="file"; filename="my.txt"
     Content-Type: application/octet-stream
     Content-Length: ...
    */

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void uploadJson() throws Exception {
        Instant instant = Instant.now();
        ObjectNode document = objectMapper.createObjectNode();
        document.put("tempf", 68.0);
        document.put("source", getClass().getSimpleName());
        document.put("epoch", instant.toEpochMilli());

        String json = document.toString();
        System.out.println(json);

        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        //  @formatter:off
        mockMvc.perform(post("/upload")
                .content(bytes)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .header(HttpHeaders.CONTENT_LENGTH, bytes.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sensordata.json"))
            .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storage.filename", equalTo("sensordata.json")))
                .andExpect(jsonPath("$.storage.contentType", containsString(MediaType.APPLICATION_JSON.toString())))
                .andExpect(jsonPath("$.storage.length", equalTo(bytes.length)));
        //  @formatter:on

        // TODO wait for job finish and assert that data is saved
    }
}
