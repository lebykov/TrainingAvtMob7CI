package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;
import sun.awt.PlatformFont;

public class GetStartedTest extends CoreTestCase
{
    @Test
    public void testPastThroughWelcome()
    {
        if (Platform.getInstance().isAndroid() || (Platform.getInstance().isMW())) {
            return;
        }

        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

        welcomePageObject.waitForLearnMoreLink();
        welcomePageObject.clickOnNextButton();

        welcomePageObject.waitForNewWaysToExploreText();
        welcomePageObject.clickOnNextButton();

        welcomePageObject.waitForAddOrEditPreferredLangText();
        welcomePageObject.clickOnNextButton();

        welcomePageObject.waitForLearnMoreAboutDataCollectedText();
        welcomePageObject.clickGetStartedButton();
    }
}
