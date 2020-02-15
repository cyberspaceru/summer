package ru.qa.summer.appium.util;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.ProxySpecification;
import org.apache.http.HttpHeaders;
import ru.qa.summer.support.Support;

import java.io.File;
import java.util.Base64;

import static ru.qa.summer.appium.AppiumConfiguration.APPIUM_CONFIGURATION;

public class BitBarUtil {
    public static String uploadFile(File file) {
        String encode = Base64.getEncoder().encodeToString((APPIUM_CONFIGURATION.getAppiumBitBarUsername() + ":" + APPIUM_CONFIGURATION.getAppiumBitBarPassword()).getBytes());
        Response response = RestAssured.given()
                .proxy(ProxySpecification.host("192.168.0.36").withPort(3128).withAuth("itatsiy", "1"))
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encode)
                .multiPart("file", file)
                .post(APPIUM_CONFIGURATION.getAppiumBitBarCloudAddress() + "/api/v2/users/" + APPIUM_CONFIGURATION.getAppiumBitBarUserId() + "/files");
        return "" + Support.getGson().fromJson(response.asString(), UploadResponse.class).id;
    }

    public static class UploadResponse {
        Long id;
    }
}
