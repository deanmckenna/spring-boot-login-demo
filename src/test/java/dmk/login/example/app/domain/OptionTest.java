package dmk.login.example.app.domain;

import dmk.login.example.app.domain.Option;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OptionTest {

    private Option classUnderTest;

    @Before
    public void init(){
        classUnderTest = new Option("", "");
    }

    @Test
    public void whenGetName_returnNameValueThatWasSet(){
        String name = "John";
        classUnderTest.setName(name);
        assertEquals(name, classUnderTest.getName());
    }

    @Test
    public void whenGetPath_returnPathValueThatWasSet(){
        String path = "/home";
        classUnderTest.setPath(path);
        assertEquals(path, classUnderTest.getPath());
    }


}
