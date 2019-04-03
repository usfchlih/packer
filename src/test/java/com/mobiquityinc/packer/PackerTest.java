package com.mobiquityinc.packer;


import com.mobiquityinc.exception.APIException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * @author ychlih
 */
public class PackerTest {
    String testCaseFilePath;

    @BeforeEach
    void init() {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        testCaseFilePath = resourceDirectory.toAbsolutePath().toString() + File.separator + "testCases.txt";
    }

    /**
     *
     */
    @Test
    public void packTest() {
        assertNotNull(Packer.pack(testCaseFilePath));
    }

    /**
     *
     */
    @Test
    public void packTestException() {
        assertThrows(APIException.class, () -> Packer.pack("dump/..")
        );
    }
}
