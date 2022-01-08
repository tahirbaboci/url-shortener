package com.baboci.urlshortener

import com.baboci.urlshortener.IDConverter.createUniqueID
import com.baboci.urlshortener.IDConverter.getDictionaryKeyFromUniqueID
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UrlShortenerService @Autowired constructor(private val urlRepository: UrlRepository) {
    fun shortenURL(localURL: String, longUrl: String?): String {
        logger.info("Shortening {}", longUrl)
        val id = urlRepository.incrementID()
        val uniqueID = createUniqueID(id)
        urlRepository.saveUrl("url:$id", longUrl)
        val baseString = formatLocalURLFromShortener(localURL)
        return baseString + uniqueID
    }

    @Throws(Exception::class)
    fun getLongURLFromID(uniqueID: String?): String {
        val dictionaryKey = getDictionaryKeyFromUniqueID(uniqueID!!)
        println("dictionaryKey: $dictionaryKey")
        val longUrl = urlRepository.getUrl(dictionaryKey)
        logger.info("Converting shortened URL back to {}", longUrl)
        return longUrl
    }

    private fun formatLocalURLFromShortener(localURL: String): String {
        val addressComponents: List<String> = localURL.split("/".toRegex())
        // remove the endpoint (last index)
        val sb = StringBuilder()
        for (i in 0 until addressComponents.size - 1) {
            sb.append(addressComponents[i])
        }
        sb.append('/')
        return sb.toString()
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UrlShortenerService::class.java)
    }
}