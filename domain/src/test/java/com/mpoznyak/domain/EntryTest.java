package com.mpoznyak.domain;

import com.mpoznyak.domain.model.Entry;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EntryTest {

    private Entry doc = new Entry("/Users");

    @Test
    public void testConstructors() {
        String name = "Users";
        assertTrue(doc.getName().equals(name));
    }

    @Test
    public void testGetPath() {
        String path = "/Users";
        assertTrue(doc.getPath()
                .toString()
                .equals(path));
    }

    @Test
    public void testSetPath() {
        Path path = Paths.get("/Users/qarnd");
        doc.setPath(path);
        assertTrue(doc.getPath().equals(path));
    }

    @Test
    public void testGetSize() {
        Path path = doc.getPath();
        assertTrue(doc.getSize() == path.toFile().length());
    }

    @Test
    public void testGetLastModified() {
        long millis = doc.getPath()
                .toFile()
                .lastModified();
        doc = mock(Entry.class);
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        doc.getLastModified();
        when(doc.getLastModified()).thenReturn(pattern.format(millis));
        verify(doc).getLastModified();
        assertTrue(doc.getLastModified().equals(pattern.format(millis)));

    }

   //TODO replace this snippet to Interactor
    @Test
    public void shouldReturnListFiles() {
        List<Entry> docs = new ArrayList<>();
        Entry doc = new Entry("/Users/qarnd/Development/carinadocs/dataproviders/docs");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(doc.getPath(),"*.{md,doc,java}")) {
            for (Path path : stream) {
                docs.add(new Entry(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
