package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
        TITLE_TPL = "css:#content h1";
        FOOTER = "css:footer";
        OPTIONS_ADD_TO_MY_READING_LIST = "css:#page-actions li#ca-watch.mw-ui-icon-mf-watch";
        NAME_OF_FOLDER_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_container']//*[@text='{FOLDER}']";
        SYNC_SAVED_ARTICLES_POPUP_TITLE = "id:Sync your saved articles?";
        SYNC_SAVED_ARTICLES_POPUP_CLOSE_BUTTON = "id:places auth close";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css:#page-actions li#ca-watch.mw-ui-icon-mf-watched";
    }

    public MWArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

}
