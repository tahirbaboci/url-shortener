package com.baboci.urlshortener

import com.fasterxml.jackson.annotation.JsonCreator

data class ShortenRequest @JsonCreator constructor(val url: String) {}