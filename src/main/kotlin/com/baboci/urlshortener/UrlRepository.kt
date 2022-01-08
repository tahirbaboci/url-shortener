package com.baboci.urlshortener

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import redis.clients.jedis.Jedis

@Repository
class UrlRepository {
    private val jedis: Jedis
    private val idKey: String
    private val urlKey: String

    constructor() {
        jedis = Jedis()
        idKey = "id"
        urlKey = "url:"
    }

    constructor(jedis: Jedis, idKey: String, urlKey: String) {
        this.jedis = jedis
        this.idKey = idKey
        this.urlKey = urlKey
    }

    fun incrementID(): Long {
        val id: Long = jedis.incr(idKey)
        logger.info("Incrementing ID: {}", id - 1)
        return id - 1
    }

    fun saveUrl(key: String?, longUrl: String?) {
        logger.info("Saving: {} at {}", longUrl, key)
        jedis.hset(urlKey, key, longUrl)
    }

    @Throws(Exception::class)
    fun getUrl(id: Long): String {
        logger.info("Retrieving at {}", id)
        val url: String = jedis.hget(urlKey, "url:$id") ?: throw Exception("URL at key $id does not exist")
        return jedis.hget(urlKey, "url:$id")
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UrlRepository::class.java)
    }
}