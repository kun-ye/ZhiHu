package com.kunye.zhihu.ui.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kunye.zhihu.R;
import com.kunye.zhihu.ui.fragment.BaseFragment;
import com.kunye.zhihu.ui.fragment.BoredFragment;
import com.kunye.zhihu.ui.fragment.CartoonFragment;
import com.kunye.zhihu.ui.fragment.CompanyFragment;
import com.kunye.zhihu.ui.fragment.DailyFragment;
import com.kunye.zhihu.ui.fragment.DesignFragment;
import com.kunye.zhihu.ui.fragment.FilmFragment;
import com.kunye.zhihu.ui.fragment.FragmentFactory;
import com.kunye.zhihu.ui.fragment.GameFragment;
import com.kunye.zhihu.ui.fragment.IndexFragment;
import com.kunye.zhihu.ui.fragment.MoneyFragment;
import com.kunye.zhihu.ui.fragment.MusicFragment;
import com.kunye.zhihu.ui.fragment.NetFragment;
import com.kunye.zhihu.ui.fragment.SportFragment;
import com.kunye.zhihu.ui.fragment.UserFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BaseFragment fragment = null;
    private FragmentManager manager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragment = new IndexFragment();
        FragmentFactory.createFtagment(manager,fragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_index) {
            fragment = new IndexFragment();
            FragmentFactory.createFtagment(manager,fragment);
        } else if (id == R.id.nav_daily) {
            fragment = new DailyFragment();
            FragmentFactory.createFtagment(manager,fragment);
        } else if (id == R.id.nav_user) {
            fragment = new UserFragment();
            FragmentFactory.createFtagment(manager,fragment);
        } else if (id == R.id.nav_film) {
            fragment = new FilmFragment();
            FragmentFactory.createFtagment(manager,fragment);
        } else if (id == R.id.nav_bored) {
            fragment = new BoredFragment();
            FragmentFactory.createFtagment(manager,fragment);
        } else if (id == R.id.nav_design) {
            fragment = new DesignFragment();
            FragmentFactory.createFtagment(manager,fragment);
        }else if (id == R.id.nav_company) {
            fragment = new CompanyFragment();
            FragmentFactory.createFtagment(manager,fragment);
        }else if (id == R.id.nav_money) {
            fragment = new MoneyFragment();
            FragmentFactory.createFtagment(manager,fragment);
        }else if (id == R.id.nav_net) {
            fragment = new NetFragment();
            FragmentFactory.createFtagment(manager,fragment);
        }else if (id == R.id.nav_game) {
            fragment = new GameFragment();
            FragmentFactory.createFtagment(manager,fragment);
        }else if (id == R.id.nav_music) {
            fragment = new MusicFragment();
            FragmentFactory.createFtagment(manager,fragment);
        }else if (id == R.id.nav_cartoon) {
            fragment = new CartoonFragment();
            FragmentFactory.createFtagment(manager,fragment);
        }else if (id == R.id.nav_sport) {
            fragment = new SportFragment();
            FragmentFactory.createFtagment(manager,fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
