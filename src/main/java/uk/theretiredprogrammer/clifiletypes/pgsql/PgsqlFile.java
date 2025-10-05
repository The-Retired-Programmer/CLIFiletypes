package uk.theretiredprogrammer.clifiletypes.pgsql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

public class PgsqlFile {

    private static final String EMBEDDED_DATABASE_NAME_PREFIX = "--! dbname=";
    private final File pgsqlFile;
    private final FileObject pgsqlFO;

    public PgsqlFile(FileObject pgsqlFO) {
        this.pgsqlFO = pgsqlFO;
        this.pgsqlFile = FileUtil.toFile(pgsqlFO);
    }

    public File getFile() {
        return pgsqlFile;
    }

    public String getPath() {
        return pgsqlFile.getPath();
    }

    public File getParent() {
        return FileUtil.toFile(pgsqlFO.getParent());
    }

    public String getDbname() {
        String dbname;
        try {
            dbname = getDbnameFromFile();
            if (dbname != null) {
                return dbname;
            }
        } catch (IOException ex) {
        }
        //
        try {
            dbname = getDbnameFromProperties();
            if (dbname != null) {
                return dbname;
            }
        } catch (IOException ex) {
        }
        return null;
    }

    private String getDbnameFromFile() throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(pgsqlFile))) {
            String firstline = in.readLine();
            if (firstline != null && firstline.startsWith(EMBEDDED_DATABASE_NAME_PREFIX)) {
                return firstline.substring(EMBEDDED_DATABASE_NAME_PREFIX.length()).trim();
            }
            return null;
        }
    }

    private String getDbnameFromProperties() throws IOException {
        FileObject propertiesFO = pgsqlFO.getParent().getFileObject("postgresql", "properties");
        if (propertiesFO == null) {
            return null;
        }
        Properties properties = new Properties();
        try (InputStream propsin = propertiesFO.getInputStream()) {
            properties.load(propsin);
        }
        return properties.getProperty("dbname");
    }
}
