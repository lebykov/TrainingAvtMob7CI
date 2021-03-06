package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase
{
    private static final String name_of_folder = "Learning programming";
    private static final String
            login = "avt_training_test",
            password = "123Testuser";

    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement("Java (programming language)");
        String article_title = articlePageObject.getArticleTitle("Java (programming language)");

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
            articlePageObject.closeSyncSavedArticlesPopup();
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            articlePageObject.waitForTitleElement("Java (programming language)");

            assertEquals(
                    "We are not on the same page after login",
                    article_title,
                    articlePageObject.getArticleTitle("Java (programming language)")
            );

            articlePageObject.addArticlesToMySaved();

        }

        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder);
        }
        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    // Ex17. Refactor test from Ex5
    @Test
    public void testSaveTwoArticlesToMyList()
    {
        // 1. Save two articles
        // 1.1. Saving first article
        String articleToRemoveSearchLine = "Java";
        String articleToRemoveTitle = "Java (programming language)";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(articleToRemoveSearchLine);
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement(articleToRemoveTitle);
//        String articleToRemoveTitle = articlePageObject.getArticleTitle(articleToRemoveTitle);
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
            articlePageObject.closeSyncSavedArticlesPopup();
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            articlePageObject.waitForTitleElement("Java (programming language)");

            assertEquals(
                    "We are not on the same page after login",
                    articleToRemoveTitle,
                    articlePageObject.getArticleTitle("Java (programming language)")
            );

            articlePageObject.addArticlesToMySaved();

        }

        articlePageObject.closeArticle();

        // 1.2. Saving second article
        String articleToKeepSearchLine = "Kotlin";
        String articleToKeepTitle = "Kotlin (programming language)";

        searchPageObject.initSearchInput();
        if (Platform.getInstance().isIOS()) {
            searchPageObject.clearSearchField();
        }
        searchPageObject.typeSearchLine(articleToKeepSearchLine);
        searchPageObject.clickByArticleWithSubstring("rogramming language");

        articlePageObject.waitForTitleElement(articleToKeepTitle);
//        String articleToKeepTitle = articlePageObject.getArticleTitle(articleToKeepTitle);
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToExistingList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();

        // 2. Navigate to list and delete article
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder);
        }
        myListsPageObject.swipeByArticleToDelete(articleToRemoveTitle);


        if (Platform.getInstance().isAndroid()) {
            int amountOfSavedArticles = myListsPageObject.getAmountOfSavedArticles();

            assertEquals(
                    "There supposed to remain 1 saved article, but got " + amountOfSavedArticles,
                    1,
                    amountOfSavedArticles
            );

            // 3. Open remaining article
            myListsPageObject.openArticleByTitle(articleToKeepTitle);

            // 4. Assert that remaining article is the article to keep
            articlePageObject.waitForTitleElement(articleToKeepTitle);
            String titleOfRemainingArticle = articlePageObject.getArticleTitle(articleToKeepTitle);

            assertEquals(
                    "Title of remaining article supposed to be '"  + articleToKeepTitle + "', but got '" + titleOfRemainingArticle + "'",
                    articleToKeepTitle,
                    titleOfRemainingArticle
            );
        } else if (Platform.getInstance().isIOS()) {
            // Search saved articles with title of article to keep and
            // assert that there is exactly one article in the results
            myListsPageObject.searchSavedArticlesByTitle(articleToKeepTitle);
            int amountOfFilteredSavedArticles = myListsPageObject.getNumberOfFilteredArticles();

            assertEquals(
                    "There supposed to 1 saved article in the search results, but got " + amountOfFilteredSavedArticles,
                    1,
                    amountOfFilteredSavedArticles
            );
        } else {
            // For MW search for article to keep title and assert
            // that there is Stop watching button (green star) in the search results cell
            searchPageObject.initSearchInput();
            searchPageObject.typeSearchLine(articleToKeepSearchLine);
            searchPageObject.waitForWatchedButtonInResultsBySubtitle("rogramming language");
        }
    }
}
