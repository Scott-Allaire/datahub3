package com.github.coder229.datahub3.storage;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@Entity
public class FileStorage {
    @Id
    @GeneratedValue
    private Long id;

    @JsonView(FileStorageViews.Public.class)
    private String filename;

    @JsonView(FileStorageViews.Public.class)
    private String contentType;

    @JsonView(FileStorageViews.Public.class)
    private Long length;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] contents;

}
