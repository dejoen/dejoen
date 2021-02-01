package com.dejoeent.musicapp;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
  final ArrayList<Fragment> fragments=new ArrayList<>();
   final ArrayList<String> titles=new ArrayList<>();
    
    public ViewPagerAdapter(FragmentManager manager){
        
        super(manager);
    }
    @Override
    public Fragment getItem(int p1) {
        return fragments.get(p1);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    
    public void addFrag(Fragment fragment,String title){
        
        fragments.add(fragment);
        titles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
    
    
}
