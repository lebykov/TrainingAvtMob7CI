package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            SAVED_ARTICLE_CONTAINER,
            SEARCH_SAVED_INPUT,
            FILTERED_ARTICLE,
            REMOVE_FROM_SAVED_BUTTON;

    private static String getFolderXpathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getRemoveButtonByTitle(String article_title)
    {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }

    public MyListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title " + article_title,
                15
        );
    }

    public void waitForArticleToDissappearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title " + article_title,
                15
        );
    }

    public void swipeByArticleToDelete(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.waitForArticleToAppearByTitle(article_title);
            this.swipeElementToLeft(
                    article_xpath,
                    "Cannot find saved article"
            );
        } else {
            String remove_locator = getRemoveButtonByTitle(article_title);
            this.waitForElementAndClick(
                    remove_locator,
                    "Cannot click button to remove article from saved",
                    10
            );
            System.out.println("swipeByArticleToDelete(): clicked button " + remove_locator);
        }


        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(
                    article_xpath,
                    "Cannot find saved article"
            );
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
            System.out.println("swipeByArticleToDelete(): refreshed paged");
            driver.navigate().refresh();
        }

        this.waitForArticleToDissappearByTitle(article_title);
    }

    // Ex8
    public int getAmountOfSavedArticles()
    {
        return this.getAmountOfElements(SAVED_ARTICLE_CONTAINER);
    }

    public void openArticleByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementAndClick(
                article_xpath,
                "Cannot click to open article with title " + article_title,
                5
        );
    }

    // Ex 10
    public void searchSavedArticlesByTitle(String title)
    {
        this.waitForElementToBeClickable(
                SEARCH_SAVED_INPUT,
                "Search reading list is not clickable",
                10
        );

        this.waitForElementAndSendKeys(
                SEARCH_SAVED_INPUT,
                title,
                "Cannot find and send keys to Search reading list input",
                10
        );
    }

    public int getNumberOfFilteredArticles()
    {
        return this.getAmountOfElements(FILTERED_ARTICLE);
    }


}
