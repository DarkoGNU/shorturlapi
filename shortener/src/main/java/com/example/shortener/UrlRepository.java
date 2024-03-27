package com.example.shortener;

import org.springframework.data.repository.CrudRepository;

interface UrlRepository extends CrudRepository<Url, Long> {
}
