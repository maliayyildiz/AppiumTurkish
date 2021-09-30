package EcommerceApp;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ECommerce04FiyatiOnayla {

     @Test
     public void test() throws MalformedURLException, InterruptedException {
         DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

         desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UiAutomator2");
         desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
         desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"Emulator");
         desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"10.0");
         desiredCapabilities.setCapability("app","/Users/ayyildiz/IdeaProjects/AppiumTurkish/Apps/General-Store.apk");
         desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET,true);

         AndroidDriver<MobileElement> driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),desiredCapabilities);
//butun data eksiksiz doldurunca basarili girs yapacagiz
         Thread.sleep(3000);
         driver.findElementById("com.androidsample.generalstore:id/spinnerCountry").click();

         driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Belgium\"))");

         driver.findElementByXPath("//android.widget.TextView[@text='Belgium']").click();

         driver.findElementById("com.androidsample.generalstore:id/nameField").sendKeys("Anne");
         //eger klavyeyi kapatmak istiyorsaniz
        // driver.hideKeyboard(); //klavyeyi kapatir

         driver.getKeyboard();

         driver.findElementById("com.androidsample.generalstore:id/radioFemale").click();

         driver.findElementById("com.androidsample.generalstore:id/btnLetsShop").click();
         Thread.sleep(3000);
//product title basrasili girsi sonrasi onayliyalim
         Assert.assertTrue(driver.findElementById("com.androidsample.generalstore:id/toolbar_title").isDisplayed());

       // sepete ekelem
        driver.findElementByXPath("(//android.widget.TextView[@text='ADD TO CART'])[1]").click();
//
//         driver.findElementByXPath("(//android.widget.TextView[@text='ADD TO CART'])[1]").click();

         //eger syntax bu sekilde yaziyorsaniz (ozellikle POM da ) bu sekilde calisir
         //ama yukardaki gibi (49 ve 51 satirlar) o zman element indexine dikkat etmeliyiz
//         MobileElement ilkurun = driver.findElementByXPath("(//android.widget.TextView[@text='ADD TO CART'])[1]");
//         MobileElement ikinciUrun = driver.findElementByXPath("(//android.widget.TextView[@text='ADD TO CART'])[2]");
//         ilkurun.click();
//         ikinciUrun.click();
//         //sepete git
//         driver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();
//
//        String ilkUrunAdi = driver.findElementByXPath("//android.widget.TextView[@text='Air Jordan 4 Retro']").getText();
//
//         String ikinciUrunAdi = driver.findElementByXPath("//android.widget.TextView[@text='Air Jordan 1 Mid SE']").getText();
//
//         Assert.assertEquals(ilkUrunAdi,"Air Jordan 4 Retro");
//         Assert.assertEquals(ikinciUrunAdi,"Air Jordan 1 Mid SE");
//4 2 secilecek elementi scroll yaparak bulduk
         driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.androidsample.generalstore:id/rvProductList\")).scrollIntoView(text(\"Jordan Lift Off\"))");
//         driver.findElementById("com.androidsample.generalstore:id/rvProductList");

        List<MobileElement> urunler = driver.findElementsById("com.androidsample.generalstore:id/productName");
// ueun adi uzerinden add to cart butonuna gidiyoruz
         for (int i = 0; i < urunler.size(); i++) {
             if (urunler.get(i).getText().equals("Jordan Lift Off")){
                 driver.findElementsById("com.androidsample.generalstore:id/productAddCart").get(i).click();
             }
         }

         driver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();

       //fiyatin onaylanmasi

         List<MobileElement> urunfiyatlari = driver.findElementsById("com.androidsample.generalstore:id/productPrice");

         String ilkUrunFiyati = urunfiyatlari.get(0).getText();
         System.out.println("ilk urun fiyati "+ilkUrunFiyati);//$160.97
         String ikinciUrunFiyati = urunfiyatlari.get(1).getText();
         System.out.println("ikinci urun fiyati "+ikinciUrunFiyati);//$115.0
         //ilk once string degerlerini numeric hale getirmeliyoz
         ilkUrunFiyati = ilkUrunFiyati.substring(1);
         System.out.println(ilkUrunFiyati);//160.97
         ikinciUrunFiyati = ikinciUrunFiyati.substring(1);
         System.out.println(ikinciUrunFiyati);//115.0
         double ilkUrunDouble = Double.parseDouble(ilkUrunFiyati);
         System.out.println("double olarak "+ilkUrunDouble);
         double ikinciUrunDouble = Double.parseDouble(ikinciUrunFiyati);
         System.out.println(ikinciUrunDouble + "double olarak");

         double expectedTotal = ilkUrunDouble + ikinciUrunDouble;
         System.out.println(expectedTotal);
         String expectedTotal1 = "$ "+expectedTotal;
         System.out.println(expectedTotal1);//$ 275.97

        String actualTotal = driver.findElementById("com.androidsample.generalstore:id/totalAmountLbl").getText();

        Assert.assertEquals(actualTotal,expectedTotal1);


     }
}
