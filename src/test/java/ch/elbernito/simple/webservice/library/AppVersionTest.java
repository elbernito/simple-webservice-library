package ch.elbernito.simple.webservice.library;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileReader;
import java.net.URL;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import org.apache.maven.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppVersionTest {

    private String pom;

    private AppVersion testee;


    @BeforeEach
    void tearUp(){
        testee = new AppVersion(pom){
            @Override String getPomFile() {
                return pom;
            }
        };
    }


    @Test
    void testWehnReadCorrectPomThenVersionIsCorrect(){

        // arrange
        pom = Objects.requireNonNull(getClass().getClassLoader().getResource("pom-version-666.xml")).getPath();

        // act
        final String result = testee.getVersion();

        // assert
        assertThat("Wrong version", result, is("666"));

    }

    @Test
    void testWehnReadCorrectPomThenInfoIsCorrect(){

        // arrange
        pom = Objects.requireNonNull(getClass().getClassLoader().getResource("pom-version-666.xml")).getPath();

        // act
        final String result = testee.getMavenInfo();
        System.out.println(result);
        // assert
        assertThat("Wrong version", result, containsString("hell.heaven:between-the-fronts:jar:666"));
        assertThat("Wrong version", result, containsString("hell.heaven"));
        assertThat("Wrong version", result, containsString("between-the-fronts"));
        assertThat("Wrong version", result, containsString("666"));

    }


}