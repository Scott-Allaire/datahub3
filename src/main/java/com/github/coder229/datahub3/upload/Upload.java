package com.github.coder229.datahub3.upload;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.coder229.datahub3.storage.FileStorage;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.time.ZonedDateTime;

@Data
@Entity
public class Upload {

    @Id
    @GeneratedValue
    private Long id;

    @JsonView(UploadViews.Public.class)
    private ZonedDateTime uploaded;

    @JsonView(UploadViews.Public.class)
    private UploadStatus uploadStatus;

    @JsonView(UploadViews.Public.class)
    @Version
    private Integer version;

    @ManyToOne
    @JsonView(UploadViews.Public.class)
    private FileStorage storage;
}
