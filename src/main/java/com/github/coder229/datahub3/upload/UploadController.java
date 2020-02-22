package com.github.coder229.datahub3.upload;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    UploadService uploadService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(UploadViews.Public.class)
    public Upload acceptUpload(HttpEntity<byte[]> requestEntity) {
        /*
            Content-Disposition: form-data; name="file"; filename="my.txt"
            Content-Type: application/octet-stream
            Content-Length: ...
         */
        HttpHeaders headers = requestEntity.getHeaders();
        String filename = headers.getContentDisposition().getFilename();
        MediaType contentType = requestEntity.getHeaders().getContentType();
        byte[] contents = requestEntity.getBody();
        long length = requestEntity.getHeaders().getContentLength();

        return uploadService.create(filename, contentType, contents, length);
    }
}
