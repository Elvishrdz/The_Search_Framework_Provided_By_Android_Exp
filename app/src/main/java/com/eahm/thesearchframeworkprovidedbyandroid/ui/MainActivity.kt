package com.eahm.thesearchframeworkprovidedbyandroid.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.eahm.thesearchframeworkprovidedbyandroid.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val editTextFake : EditText? = null
        editTextFake?.setOnEditorActionListener { v, actionId, event ->
            // React to the done or enter button from the keyboard when editing text is finished.
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                event.getAction() == KeyEvent.ACTION_DOWN &&
                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if (event == null || !event.isShiftPressed()) {
                    // the user is done typing.
                    callSearchDialog()
                    //true // consume.
                }
            }
            false  // pass on to other listeners.
        }*/

        // Getting the search manager. (Also check onCreateOptionsMenu() method)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        // SearchView
        searchViewSearch.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchViewSearch.isIconified = false

        // Button
        buttonSearch.setOnClickListener{
            callSearchDialog()
        }

        buttonOtherActivity.setOnClickListener{
            startActivity(Intent(this@MainActivity, NewsActivity::class.java))
        }

        theSearchFramework()

        /* If the user cancels search by pressing the Back button, the search
        dialog closes and the activity regains input focus. You can register
        to be notified when the search dialog is closed with setOnDismissListener()
        and/or setOnCancelListener(). */
        // val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager // Already declared above.

        /* OnDismissListener it is called every time the search dialog closes */
        searchManager.setOnDismissListener {
            // Executed just with the Search Dialog.
            Log.i("SearchTag", "MainActivity: onDismiss Called()")
        }

        /* OnCancelListener only pertains to events in which the user explicitly exited the
          search dialog, so it is not called when a search is executed (in which case, the
          search dialog naturally disappears). */
        searchManager.setOnCancelListener {
            // Executed just with the Search Dialog.
            Log.i("SearchTag", "MainActivity: onCancel Called()")
        }

        theVoiceSearch()
        theSearchSuggestions()
        theCustomSearchSuggestions()
        
    }

    private fun theSearchFramework(){
        /*
            The search dialog
            is a UI component that's controlled by the Android system.
            When activated by the user, the search dialog appears at the top of the activity.
            The Android system controls all events in the search dialog. When the user submits
            a query, the system delivers the query to the activity that you specify to handle searches.
            The dialog can also provide search suggestions while the user types.

            The search widget
            is an instance of SearchView that you can place anywhere in your layout. By default,
            the search widget behaves like a standard EditText widget and doesn't do anything, but you
            can configure it so that the Android system handles all input events, delivers queries
            to the appropriate activity, and provides search suggestions (just like the search dialog)
        */

        /* Steps
        1. create the res/xml -> searchable.xml configuration
        2. declare your searchable activity in the Android manifest file.
        3. performing a search in your searchable activity involves three steps:
           a. Receiving the query
           b. Searching your data
           c. Presenting the results
        3.1 if multiple activities uses the search activity then declare it in the AndroidManifest.xml
            with the meta-data tag
        4.Invoking the search dialog. If your app uses an app bar, then you should
          not use the search dialog for your search interface. Instead, use the search
          widget as a collapsible view in the app bar.

        5. call the onSearchRequested() to invoke the search dialog. If is not working be sure you declare
        the meta-data tag in the activities that uses the search dialog in the manifest.
         */
    }

    private fun theVoiceSearch(){
        /* Voice Search: See res/xml -> searchable.xml */
    }

    private fun theSearchSuggestions(){
        /* Adding Search Suggestions
        The system manages the list of suggestions and handles the event when the user selects a suggestion.

        You can provide two kinds of search suggestions:

        1. Recent query search suggestions:
           words that the user previously used as search queries in your application.

        2. Custom search suggestions:
           suggestions that you provide from your own data source, to help users
           immediately select the correct spelling or item they are searching for. the user can select a suggestion
           to instantly go to the definition.

          */

        /*
        To provide recent queries suggestions, you need to:
        1. Implement a searchable activity, as described in Creating a Search Interface. (Done!)
        2. Create a content provider that extends SearchRecentSuggestionsProvider and declare it in your application manifest. (See SearchSuggestionProvider.kt)
        3. Modify the searchable configuration with information about the content provider that provides search suggestions. (See searchable.xml)
        4. Save queries to your content provider each time a search is executed. (See the searchable activity SearchActivity.kt -> handleIntent())

        5. clearing the suggestion data (See SearchActivity.kt -> clearSearchSuggestions())
         */
    }

    private fun theCustomSearchSuggestions(){
        /* https://developer.android.com/guide/topics/search/adding-custom-suggestions */

        /* To provide custom suggestions, do the following:
            1. Implement a basic searchable activity, as described in Creating a Search Interface. (Done!)
            2. Modify the searchable configuration with information about the content provider that provides custom suggestions. (See searchable.xml)
            (See SearchActivity.kt -> handleIntent() -> When using a Custom Search... )
            3. Build a table (such as in an SQLiteDatabase) for your suggestions and format the table with required columns.
            4. Create a Content Provider that has access to your suggestions table and declare the provider in your manifest.
            5. Declare the type of Intent to be sent when the user selects a suggestion (including a custom action and custom data).*/
    }

    /* The search dialog is always hidden by default, until the user activates it. Your application
    can activate the search dialog by calling onSearchRequested(). However, this method doesn't
    work until you enable the search dialog for the activity. */
    private fun callSearchDialog() {
        onSearchRequested()
    }

    /* If you want to be notified when the search dialog is
    activated, override the onSearchRequested() */
    override fun onSearchRequested(): Boolean {
        /* When the search dialog appears, NO lifecycle methods (such
           as onPause()) are called */
        performBeforeTheSearchDialogAppearOnScreen()

        /* Unless you are passing search context data (discussed
        below), you should end the method by calling the
        super class implementation */
        return super.onSearchRequested()
    }

    private fun performBeforeTheSearchDialogAppearOnScreen() {
        // Perform something that is necessary before we show the search dialog
    }

    /* After you've created a searchable configuration and a searchable activity, you
    need to enable assisted search for each SearchView */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        /* if you're using a SearchView as an action view in the app bar, you
        should enable the widget during the onCreateOptionsMenu() callback */

        menuInflater.inflate(R.menu.menu_toolbar, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_search)?.actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            // Do not iconify the widget; expand it by default
            isIconified = false
        }

        /*
        Using both the widget and the dialog:
        To handle this situation, the menu item to which you've attached the search
        widget should activate the search dialog when the user selects it from the
        overflow menu. To do so, you must implement onOptionsItemSelected() to handle
        the "Search" menu item and open the search dialog by calling onSearchRequested().
        */
       /* menu.findItem(R.id.menu_search).setOnMenuItemClickListener {
            // You must remove the app:actionViewClass="androidx.appcompat.widget.SearchView" line in
            // the menu_search in menu_toolbar.xml in order to work
            onSearchRequested()
        }*/

        return true
    }

    /* If the current activity is not the searchable activity (and is not!), then the normal activity
    lifecycle events are triggered once the user executes a search. */
    override fun onPause() {
        super.onPause()
        // onPause is called when the search is executed or any other action that makes that we leave the activity
        Log.i("SearchTag", "MainActivity: onPause Called()")
    }

}