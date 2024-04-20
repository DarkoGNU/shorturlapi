package com.example.cleaner;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@Table
public class Url {
    @PrimaryKey
    private Long id;

    @NotNull
    @URL(regexp = "^(http|https).*")
    private String url;
}
