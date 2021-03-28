package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GeoIpServiceTests {

  @Test
  public void testMyIp() {
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("31.211.111.39");
    Assert.assertEquals(ipLocation, "<GeoIP><Country>RU</Country><State>75</State></GeoIP>");
  }
}
