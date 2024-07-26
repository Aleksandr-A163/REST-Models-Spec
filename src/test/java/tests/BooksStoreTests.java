package tests;

import org.junit.jupiter.api.DisplayName;
import models.*;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import pages.ProfilePage;


public class BooksStoreTests extends TestBase {

    private final ProfilePage profilePage = new ProfilePage();
    void deleteBookFromBasketWithStepsAnnotationTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
}