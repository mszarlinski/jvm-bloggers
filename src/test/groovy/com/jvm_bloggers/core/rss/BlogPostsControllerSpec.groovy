package com.jvm_bloggers.core.rss

import com.jvm_bloggers.SpringContextAwareSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import spock.lang.Subject
import spock.lang.Unroll

import static org.springframework.http.MediaType.APPLICATION_ATOM_XML_VALUE
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

@Subject(BlogPostsController)
class BlogPostsControllerSpec extends SpringContextAwareSpecification {

    private static final String BROWSER_ACCEPT_HEADER = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0"

    @Autowired
    WebApplicationContext webApplicationContext

    @Unroll
    def "Should get OK status for RSS feed in #format format request"() {
        given:
        MockMvc mockMvc = webAppContextSetup(webApplicationContext)
                .build()

        expect:
        mockMvc.perform(get("/pl/rss.$format")
                .header("Accept", BROWSER_ACCEPT_HEADER))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))

        where:
        format || mediaType
        "json" || APPLICATION_JSON_UTF8_VALUE
        "xml"  || APPLICATION_ATOM_XML_VALUE
        ""     || APPLICATION_ATOM_XML_VALUE
    }

}
