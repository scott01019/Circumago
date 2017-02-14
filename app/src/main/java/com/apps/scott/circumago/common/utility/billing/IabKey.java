package com.apps.scott.circumago.common.utility.billing;

/**
 * Created by Scott on 11/29/2016.
 */
public class IabKey {
    public static String[] keys = {
            "YIR6QyRu7W8t+klSmJlwvmejBriO6IsXUVQGM",
            "qgR+Y+CZhz8tnyoAovCfqSlzYqyDeDHNj55oWtBP",
            "eaJaqdf2ggggT5jHJIWn1H1MEKZh3jB8njb8JHf",
            "RpnkaS6hxZRKkXfnL5Lo257VgioRB8gtAV8plU4",
            "wE/W5bI/65WahknlDsX0vLa0rDsU3DHp3hPGikf48",
            "S0B9H83ddx4DmB6lpzgYn9n2bUwbvhHAcSrqenWMWD",
            "jPqQsnWgAY8pDewOjqTY4PF0oXwqg1bcFnZK",
            "EpSzkAQa6k/GxX9IoLoNcpjNITDd1fWidaqab",
            "miibiJanbGKQHKIg9W0baqefaaocaq8amiibcGkcaq",
            "2CUiLLCQTIM+J2S4esV9ZUgE7oG1JC+aVNN69jZ"
    };

    public static int[] indicies = { 8, 2, 6, 3, 5, 4, 1, 9, 0, 7 };

    public static String getKey() {
        String result = "";
        for (int index : indicies) {
            byte[] bytes = keys[index].getBytes();
            for (int i = 0; i < bytes.length; i++) {
                if(bytes[i] >= 'A' && bytes[i] <= 'Z')
                    bytes[i] = (byte)( 'a' + (bytes[i] - 'A'));
                else if(bytes[i] >= 'a' && bytes[i] <= 'z')
                    bytes[i] = (byte)( 'A' + (bytes[i] - 'a'));
            }
            result += new String(bytes);
        }
        return result;
    }
}