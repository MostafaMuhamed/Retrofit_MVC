package Retrofit.retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import Retrofit.Fragment.albumFragment;
import Retrofit.Fragment.photoFragment;
import Retrofit.Fragment.postFragment;
import Retrofit.Fragment.commentFragment;
import Retrofit.Fragment.todoFragment;
import Retrofit.Fragment.userFragment;

public class MainActivity extends AppCompatActivity
{
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialViews();


    }

    private void initialViews()
    {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            Fragment[] fragments = new Fragment[]
                    {
                      new postFragment(),
                      new commentFragment(),
                      new albumFragment(),
                      new todoFragment(),
                      new userFragment(),
                      new photoFragment()
                    };
            @NonNull
            @Override
            public Fragment getItem(int position)
            {
                return fragments[position];
            }

            @Override
            public int getCount()
            {
                return fragments.length;
            }
        };

        setViews();
    }

    private void setViews()
    {
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_post);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_comment);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_images);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_book);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_team);
        tabLayout.getTabAt(5).setIcon(R.drawable.ic_picture);
    }
}
