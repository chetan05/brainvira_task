package com.example.brainvira_task.view;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class TestUtil {
    public static final String TAG = TestUtil.class.getCanonicalName();
    private static final String TEST_RESOURCES_PATH="/Users/cmagar/desktop/reservation/android/axxess_task/app";

    /**
     * InputStream of file
     * @param relFilePath path
     * @return InputStream of specified file
     * @throws Exception
     */
    public static InputStream getTestResourceInputStream(String relFilePath) throws FileNotFoundException{
        return new FileInputStream(TEST_RESOURCES_PATH + "/src/test/java/assets/" + relFilePath);
    }

    /**
     * get file contents as String
     * @param relFilePath path to file relative to KumNGo/app directory, eg "mock_responses/facebook_response_without_name.json"
     * @return contents of file as String
     * @throws Exception
     */
    public static String getTestResourceString( String relFilePath) throws Exception {
        try (InputStream inputStream = getTestResourceInputStream(relFilePath)) {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        }

    }

}
