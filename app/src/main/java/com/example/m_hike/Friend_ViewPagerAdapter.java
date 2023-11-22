package com.example.m_hike;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Friend_ViewPagerAdapter extends FragmentStateAdapter {
    public Friend_ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1){
            return new FriendRequestFragment();
        }
        return new AllFriendFragment();
    }

    @Override
    public int getItemCount() {return 2;}
}
