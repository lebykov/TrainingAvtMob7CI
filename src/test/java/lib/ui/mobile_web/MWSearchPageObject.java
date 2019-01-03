package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
        static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class, 'wikidata-description')][contains(text(), '{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        SEARCH_RESULTS_LIST = "id:org.wikipedia:id/search_results_list";
        SEARCH_RESULT_TITLE_ELEMENT = "id:org.wikipedia:id/page_list_item_title";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL =
                "xpath://li[contains(@title, '{TITLE}')]//div[contains(@class, 'wikidata-description')][contains(text(), '{DESCRIPTION}')]";
        CLEAR_SEARCH_FIELD_BUTTON = "id:clear mini";
        SEARCH_RESULTS_STOP_WATCHING_BUTTON_BY_SUBTITLE_TPL =
                "xpath://div[contains(@class, 'results')]//h3[contains(text(), '{SUBTITLE}')]/../../div[contains(@class, 'watched')]";
    }

    public MWSearchPageObject(RemoteWebDriver driver)
        {
            super(driver);
        }
}
