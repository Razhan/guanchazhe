package com.studio.ran.guanchazhe.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alp.library.base.helper.EnhancedFragmentManager;
import com.alp.library.base.ui.BaseActivity;
import com.alp.library.widget.navigationbar.NavigationBar;
import com.alp.library.widget.navigationbar.NavigationBarFragmentAdapter;
import com.alp.library.widget.navigationbar.NavigationItemClickListener;
import com.studio.ran.guanchazhe.R;
import com.studio.ran.guanchazhe.author.AuthorFragment;
import com.studio.ran.guanchazhe.news.NewsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigation_bar)
    NavigationBar navigationBar;

    private EnhancedFragmentManager fragmentManager;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isShowBackIcon() {
        return false;
    }

    @Override
    protected String setToolBarText() {
        return getText(R.string.app_name).toString();
    }

    @Override
    public void initData() {
        List<Fragment> fragments = new ArrayList<>();
        List<Integer> drawables = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        FragmentManager manager = getFragmentManager();

        fragments.add(NewsFragment.newInstance());
        drawables.add(R.drawable.ic_home);
        tags.add("新闻");

        fragments.add(AuthorFragment.newInstance());
        drawables.add(R.drawable.ic_widgets);
        tags.add("专栏");

        fragments.add(AuthorFragment.newInstance());
        drawables.add(R.drawable.ic_storage);
        tags.add("作者");

        fragments.add(AuthorFragment.newInstance());
        drawables.add(R.drawable.ic_account);
        tags.add("我的");

        NavigationBarFragmentAdapter adapter = new NavigationBarFragmentAdapter(fragments, drawables, tags);

        fragmentManager = new EnhancedFragmentManager(manager, adapter, R.id.home_fragment);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        navigationBar.setFragmentManager(fragmentManager);
    }

    @Override
    public void onBackPressed() {
        setDoubleClickExit(true);
        super.onBackPressed();
    }

}