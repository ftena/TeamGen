package com.tarlic.TeamGen;

import android.provider.BaseColumns;

public class EnclosingClass {

public static abstract class FeedEntry implements BaseColumns {
    public static final String TABLE_NAME = "entry";
    public static final String COLUMN_NAME_ENTRY_ID = "entryid";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    
}

//Prevents the FeedReaderContract class from being instantiated.
private EnclosingClass() {}
}


//Prevents the FeedReaderContract class from being instantiated.
