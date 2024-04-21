package com.example.shortener;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.SASI;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Table
public class Url {
    @PrimaryKey
    private Long id;

    @NotNull
    @URL(regexp = "^(http|https).*")
    private String url;

    @SASI(indexMode = SASI.IndexMode.SPARSE)
    private LocalDateTime createdTime;
}
