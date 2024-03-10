package com.example.shorturlapi;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotNull;

record Url(
        @Id Long id,
        @NotNull String url
) {}
