package progernapplications.gameofthroneswikiapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        setTitle("");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // Do nothing
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_books:
                showBooksHelpDialog();
                break;
            case R.id.action_characters:
                showCharactersHelpDialog();
                break;
            case R.id.action_houses:
                showHousesHelpDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        switch(item.getItemId())
        {
            case R.id.nav_houses:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new HousesFragment()).commit();
                break;
            case R.id.nav_books:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new BooksFragment()).commit();
                break;
            case R.id.nav_characters:
               getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new CharactersFragment()).commit();
                break;
            case R.id.nav_info:
                // TODO Info toast or smthing
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showCharactersHelpDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainDrawerActivity.this);
        builder.setTitle("Characters query")
                .setMessage(R.string.help_dialog_characters)
                .setIcon(R.drawable.crusader_icon_for_dialog)
                .setCancelable(false)
                .setPositiveButton("Got it!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showHousesHelpDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainDrawerActivity.this);
        builder.setTitle("House query")
                .setMessage(R.string.help_dialog_houses)
                .setIcon(R.drawable.castle_icon_for_dialog)
                .setCancelable(false)
                .setPositiveButton("Got it!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showBooksHelpDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainDrawerActivity.this);
        builder.setTitle("Book query")
                .setMessage(R.string.help_dialog_books)
                .setIcon(R.drawable.books_icon_for_dialog)
                .setCancelable(false)
                .setPositiveButton("Got it!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
