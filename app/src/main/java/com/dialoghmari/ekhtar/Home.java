package com.dialoghmari.ekhtar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    private TextView nameTV,usernameTV;
    String uid;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;

    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar= (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Initaliser les nom username mel intent */
        uid = getIntent().getStringExtra("uid");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(textView.getText()+" "+getIntent().getStringExtra("username"));



        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {

                int id = menuItem.getItemId();
                Intent intent = new Intent(getApplicationContext(),Question.class);
                switch (id) {
                    case R.id.nav_fun:

                        Toast.makeText(Home.this, "Fun Selected", Toast.LENGTH_SHORT).show();
                        intent.putExtra("uid",uid);
                        intent.putExtra("categorie","Fun");
                        startActivity(intent);
                        return true;

                    case R.id.nav_art:

                        Toast.makeText(Home.this, "Art Selected", Toast.LENGTH_SHORT).show();
                        intent.putExtra("uid",uid);
                        intent.putExtra("categorie","Art");
                        startActivity(intent);
                        return true;
                    case R.id.nav_cinema:

                        Toast.makeText(Home.this, "Cinema Selected", Toast.LENGTH_SHORT).show();
                        intent.putExtra("uid",uid);
                        intent.putExtra("categorie","Cinema");
                        startActivity(intent);
                        return true;
                    case R.id.nav_politics:

                        Toast.makeText(Home.this, "Politics Selected", Toast.LENGTH_SHORT).show();
                        intent.putExtra("uid",uid);
                        intent.putExtra("categorie","Politics");
                        startActivity(intent);
                        return true;
                    case R.id.nav_science:

                        Toast.makeText(Home.this, "Science Selected", Toast.LENGTH_SHORT).show();
                        intent.putExtra("uid",uid);
                        intent.putExtra("categorie","Science");
                        startActivity(intent);
                        return true;
                    case R.id.nav_sport:

                        Toast.makeText(Home.this, "Sport Selected", Toast.LENGTH_SHORT).show();
                        intent.putExtra("id",uid);
                        intent.putExtra("categorie","Sport");
                        startActivity(intent);
                        return true;
                    case R.id.nav_addquestion:

                        Toast.makeText(Home.this, "New poll", Toast.LENGTH_SHORT).show();
                        Intent intentAdd = new Intent(getApplicationContext(),Addquestion.class);
                        intentAdd.putExtra("uid",uid);
                        startActivity(intentAdd);
                        return true;
                    case R.id.nav_logut:

                        finish();
                        return true;

                    case R.id.nav_setting:
                        Toast.makeText(Home.this, "Setting Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_account:
                        Toast.makeText(Home.this, "Account Selected", Toast.LENGTH_SHORT).show();
                        Intent intentAccount = new Intent(getApplicationContext(),Account.class);
                        intentAccount.putExtra("uid",uid);
                        startActivity(intentAccount);
                        return true;
                    default:
                        return true;
                }

            }

        });
        View headerView =  mNavigationView.getHeaderView(0);
        usernameTV = (TextView)headerView.findViewById(R.id.usernameTV);
        usernameTV.setText(""+getIntent().getStringExtra("username"));
        nameTV = (TextView)headerView.findViewById(R.id.nameTV);
        nameTV.setText(""+getIntent().getStringExtra("name"));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
