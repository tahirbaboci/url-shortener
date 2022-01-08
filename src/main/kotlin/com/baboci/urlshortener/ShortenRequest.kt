package com.baboci.urlshortener

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ShortenRequest @JsonCreator constructor(val url: String) {
//    var url: String? = null

    //    @JsonCreator
//    constructor(@JsonProperty("url") url: String?) {
//        this.url = url
//    }
}