package com.baboci.urlshortener

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import java.io.IOException
import java.net.URISyntaxException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


@RestController
class UrlShortenerController @Autowired constructor(val urlShortenerService: UrlShortenerService) {
    @RequestMapping(value = ["/shortener"], method = [RequestMethod.POST], consumes = ["application/json"])
    @Throws(Exception::class)
    fun shortenUrl(@RequestBody @Valid shortenRequest: ShortenRequest, request: HttpServletRequest): String {
        logger.info("Received url to shorten: " + shortenRequest.url)
        val longUrl = shortenRequest.url
        if (UrlValidator.validateURL(longUrl)) {
            val localURL: String = request.requestURL.toString()
            val shortenedUrl: String = urlShortenerService.shortenURL(localURL, shortenRequest.url)
            logger.info("Shortened url to: $shortenedUrl")
            return shortenedUrl
        }
        throw Exception("Please enter a valid URL")
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    @Throws(IOException::class, URISyntaxException::class, Exception::class)
    fun redirectUrl(@PathVariable id: String, request: HttpServletRequest?, response: HttpServletResponse?): RedirectView {
        logger.debug("Received shortened url to redirect: $id")
        val redirectUrlString: String = urlShortenerService.getLongURLFromID(id)
        val redirectView = RedirectView()
        redirectView.url = redirectUrlString
        return redirectView
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UrlShortenerController::class.java)
    }
}