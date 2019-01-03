package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject
{
    protected static String
        TITLE_TPL,
        FOOTER,
        OPTIONS_BUTTON,
        OPTIONS_ADD_TO_MY_READING_LIST,
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        NAME_OF_FOLDER_TPL,
        SYNC_SAVED_ARTICLES_POPUP_TITLE,
        SYNC_SAVED_ARTICLES_POPUP_CLOSE_BUTTON;

    private static String getTitleLocatorByTitle(String title)
    {
        if (Platform.getInstance().isIOS()) {
            return TITLE_TPL.replace("{TITLE}", title);
        } else {
            return TITLE_TPL;
        }
    }

    public ArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public WebElement waitForTitleElement(String title)
    {
        String title_locator = getTitleLocatorByTitle(title);
        return this.waitForElementPresent(title_locator, "Cannot find article title", 15);
    }

    public String getArticleTitle(String title)
    {
        WebElement titleElement = waitForTitleElement(title);

        if (Platform.getInstance().isAndroid()) {
            return titleElement.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            return titleElement.getAttribute("name");
        } else {
            return titleElement.getText();
        }
    }

    public void swipeToFooter()
    {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER,
                    "Cannot find the end of article",
                    20
            );
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(
                    FOOTER,
                    "Cannot find the end of article",
                    40
            );
        } else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER,
                    "Cannot find the end of article",
                    40
            );
        }
    }

    public void addArticleToMyList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_READING_LIST,
                "Cannot find option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article folder",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot find input to set name of article folder",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );
    }

    public void closeArticle()
    {
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find X link",
                    5
            );
        } else {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }


    }

    public void closeSyncSavedArticlesPopup()
    {
        if (Platform.getInstance().isIOS()) {
            this.waitForElementPresent(
                    SYNC_SAVED_ARTICLES_POPUP_TITLE,
                    "Cannot find Sync Saved Articles title text",
                    10
            );
            this.waitForElementAndClick(
                    SYNC_SAVED_ARTICLES_POPUP_CLOSE_BUTTON,
                    "Cannot find Sync Saved Articles Close Button",
                    10
            );
        } else {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }

    }

    public void addArticlesToMySaved()
    {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfItAdded();
        }
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_READING_LIST,
                "Cannot find option to add article to reading list",
                10
        );
    }

    public void removeArticleFromSavedIfItAdded()
    {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannto click button to remove an article from saved",
                    1
            );
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_MY_READING_LIST,
                    "Cannot fins button to add an article to saved list after removing from this list before"
            );
        }
    }

    // Ex8
    private static String getFolderXpathByFolderName(String name_of_folder)
    {
        return NAME_OF_FOLDER_TPL.replace("{FOLDER}", name_of_folder);
    }

    public void addArticleToExistingList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_READING_LIST,
                "Cannot find option to add article to reading list",
                5
        );

        String folderXpath = getFolderXpathByFolderName(name_of_folder);
        this.waitForElementAndClick(
                folderXpath,
                "Cannot find folder " + name_of_folder + " to save second article to",
                5
        );
    }

    public void assertArticleHasTitleElement(String title)
    {
        String title_locator = getTitleLocatorByTitle(title);
        this.assertElementPresent(
             title_locator,
            "No page title found"
    );
    }
}
