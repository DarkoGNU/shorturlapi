package com.example.shorturlapi;

import org.springframework.data.repository.CrudRepository;

interface UrlRepository extends CrudRepository<Url, Long> {
}
