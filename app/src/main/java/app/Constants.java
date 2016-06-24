package app;

/**
 * The app's constants
 * Author  yogu
 * Since  2016/6/24
 */


public class Constants {
    // database
    public static final String ARTICLES_TABLE = "articles";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PUB_DATE = "pubDate";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_AUTHOR = "author";

    public static final String[] COLUMNS = {
            Constants.COLUMN_ID,
            Constants.COLUMN_TITLE,
            Constants.COLUMN_DESCRIPTION,
            Constants.COLUMN_PUB_DATE,
            Constants.COLUMN_LINK,
            Constants.COLUMN_AUTHOR,
    };
    // End database

}
