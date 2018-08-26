package com.galvanize.jalbersh.springplayground.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
        "wordCount.caseSensitive=true",
        "wordCount.words.skip[0]=Hello",
        "wordCount.words.skip[1]=there",
        "wordCount.words.skip[2]=world",
})
public class AnotherWordCountConfigTest {
    @Autowired
    private WordCountConfig config;

    @Test
    public void testPropertiesAreMappedCorrectly() {
        assertThat(config.isCaseSensitive(), equalTo(true));
        assertThat(config.getWords().getSkip(), contains("Hello", "there", "world"));
    }
}
