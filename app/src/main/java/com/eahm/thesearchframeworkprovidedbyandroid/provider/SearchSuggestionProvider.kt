package com.eahm.thesearchframeworkprovidedbyandroid.provider

import android.content.SearchRecentSuggestionsProvider


/* 2. Creating a Content Provider
   must be an implementation of SearchRecentSuggestionsProvider
    */
class SearchSuggestionProvider : SearchRecentSuggestionsProvider(){

    init {
        /* a. The call to setupSuggestions() passes the name of the search authority and a database mode. */
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object{
        /* b. The search authority can be any unique string, but the best practice is to use a fully qualified
              name for your content provider (package name followed by the provider's class name; for example:
              "com.example.MySuggestionProvider"). (See AndroidManifest.xml-><application>-><provider> |
               | See searchable.xml->searchSuggestAuthority attribute) */
        const val AUTHORITY = "com.eahm.SearchSuggestionProvider"

        /* c. The database mode must include DATABASE_MODE_QUERIES and can optionally include DATABASE_MODE_2LINES
              which adds another column to the suggestions table that allows you to provide a second line of text
              with each suggestion. For example, if you want to provide two lines in each suggestion */
        const val MODE = DATABASE_MODE_QUERIES or DATABASE_MODE_2LINES
    }

    /* d. Now declare the content provider in the AndroidManifest.xml -> application tag with the
          same authority string used in your extended SearchRecentSuggestionsProvider class. */

}