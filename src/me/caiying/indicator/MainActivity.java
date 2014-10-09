
package me.caiying.indicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import me.caiying.indicator.FadingIndicator.FadingTab;

public class MainActivity extends FragmentActivity {
    private static final String[] CONTENT = new String[] {
            "A", "B", "C", "D"
    };
    private FadingIndicator fadingIndicator;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fadingIndicator = (FadingIndicator) findViewById(R.id.fading_indicator);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
        fadingIndicator.setViewPager(viewPager);
    }

    class SampleFragmentPagerAdapter extends FragmentPagerAdapter implements FadingTab {
        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return new String[]{getString(R.string.tab1_text), getString(R.string.tab2_text),
                    getString(R.string.tab3_text), getString(R.string.tab4_text)}[position];
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }

        @Override
        public int getTabNormalIconResId(int position) {
            return new int[]{R.drawable.ic_1_1, R.drawable.ic_2_1, 
                    R.drawable.ic_3_1, R.drawable.ic_4_1}[position];
        }

        @Override
        public int getTabSelectIconResId(int position) {
            return new int[]{R.drawable.ic_1_0, R.drawable.ic_2_0, 
                    R.drawable.ic_3_0, R.drawable.ic_4_0}[position];
        }

        @Override
        public int getTabNormalTextColorResId(int position) {
            return R.color.text_normal;
        }

        @Override
        public int getTabSelectTextColorResId(int position) {
            return R.color.text_select;
        }
    }

    static class TestFragment extends Fragment {
        private static final String KEY_CONTENT = "TestFragment:Content";

        public static TestFragment newInstance(String content) {
            TestFragment fragment = new TestFragment();

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                builder.append(content).append(" ");
            }
            builder.deleteCharAt(builder.length() - 1);
            fragment.mContent = builder.toString();

            return fragment;
        }

        private String mContent = "???";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
                mContent = savedInstanceState.getString(KEY_CONTENT);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            TextView text = new TextView(getActivity());
            text.setGravity(Gravity.CENTER);
            text.setText(mContent);
            text.setTextSize(20 * getResources().getDisplayMetrics().density);
            text.setPadding(20, 20, 20, 20);

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
            layout.setGravity(Gravity.CENTER);
            layout.addView(text);

            return layout;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString(KEY_CONTENT, mContent);
        }
    }
}
