package com.example.redirector;

import org.springframework.data.repository.CrudRepository;

interface UrlRepository extends CrudRepository<Url, Long> {
}
