package com.github.coder229.datahub3.data;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.coder229.datahub3.storage.FileStorageViews;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Data
@Entity
public class SensorData {
    @Id
    @GeneratedValue
    private Long id;

    @JsonView(SensorDataViews.Public.class)
    private String code;

    @JsonView(SensorDataViews.Public.class)
    private String source;

    @JsonView(SensorDataViews.Public.class)
    private String value;

    @JsonView(SensorDataViews.Public.class)
    private long epoch;

    @JsonView(SensorDataViews.Public.class)
    private ZonedDateTime received;
}
