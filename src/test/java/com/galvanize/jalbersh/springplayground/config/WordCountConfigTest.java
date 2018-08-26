package com.galvanize.jalbersh.springplayground.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordCountConfigTest {
    @Autowired
    private WordCountConfig config;

    @Test
    public void testPropertiesAreMappedCorrectly() {
        assertThat(config.isCaseSensitive(), equalTo(false));
        assertThat(config.getWords().getSkip(), contains("the","an","a"));
    }
}
