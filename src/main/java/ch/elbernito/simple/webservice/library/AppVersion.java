package ch.elbernito.simple.webservice.library;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import com.google.common.annotations.VisibleForTesting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppVersion {

    private static final String NO_POM_MESSAGE = "NoPomFound";
    private final String pathToPom;

    private final MavenXpp3Reader reader = new MavenXpp3Reader();

    public static AppVersion create(final String pom) {
        return new AppVersion(pom);
    }

    @VisibleForTesting AppVersion(final String pom) {
        this.pathToPom = pom;
    }

    public String getVersion() {
        try {
            final Model model = getModel(pathToPom);
            return model.getVersion();
        } catch (Exception e) {
            log.error("Failure while reading pom.xml on path=[{}]", pathToPom);
        }
        return "No model loaded from pom";
    }

    public Version getVersionModel() {
        try {
            final Model model = getModel(pathToPom);
            return new Version(model.getId(), model.getGroupId(), model.getArtifactId(), model.getVersion());

        } catch (Exception e) {
            log.error("Failure while reading pom.xml on path=[{}]", pathToPom);
        }
        return new Version(NO_POM_MESSAGE, NO_POM_MESSAGE, NO_POM_MESSAGE, NO_POM_MESSAGE);
    }

    public String getMavenInfo() {
        try {
            final Model model = getModel(pathToPom);
            final StringBuilder builder = new StringBuilder();
            builder.append("Id:          ").append(model.getId()).append("\n");
            builder.append("GroupId:     ").append(model.getGroupId()).append("\n");
            builder.append("ArtifactId:  ").append(model.getArtifactId()).append("\n");
            builder.append("Version:     ").append(model.getVersion()).append("\n");
            return builder.toString();
        } catch (Exception e) {
            log.error("Failure while reading pom.xml on path=[{}]", pathToPom);
        }
        return "No model loaded from pom";
    }

    private Model getModel(final String pom) {
        try {
            final String pomFileName = getPomFile();
            return reader.read(new FileReader(pomFileName));
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }
    }

    @VisibleForTesting
    String getPomFile() {
        final File pom = new File(pathToPom);
        if (!pom.exists()) {
            try {
                System.out.println(pom.getAbsolutePath());
                System.out.println(pom.getCanonicalPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return pom.getPath();
    }

}
