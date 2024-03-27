package com.example.shortener;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;

record Url(
        @Id Long id,
        @NotNull @URL(regexp = "^(http|https).*") String url
) {
}
