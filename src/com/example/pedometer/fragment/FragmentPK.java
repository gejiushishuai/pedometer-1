package com.example.pedometer.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.test6.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;


public class FragmentPK extends Fragment {
	private View view;
	private RadioButton rButton1;
	private RadioButton rButton2;
	private List<Fragment> fragments;
	private FragmentPagerAdapter mAdapter;
	private ViewPager viewPager;
	private ImageView imageView;
	private int mScreen1_3;
	FragmentPK_1 fPk_1;
	FragmentPK_2 fPk_2;

	private int mCurrentPageIndex;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.pk, container, false);
		init();
		initTabline();
		return view;
	}

	private void initTabline() {
		imageView = (ImageView) view.findViewById(R.id.pk_tabline);
		Display display =  getActivity().getWindow().getWindowManager()
				.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mScreen1_3 = outMetrics.widthPixels / 2 - dip2px(getActivity(),60);
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) imageView.getLayoutParams();
		lp.leftMargin = dip2px(getActivity(),60);
		lp.width = mScreen1_3;
		imageView.setLayoutParams(lp);
	}

	private void init() {
		rButton1 = (RadioButton) view.findViewById(R.id.pk_1);
		rButton2 = (RadioButton) view.findViewById(R.id.pk_2);
		viewPager = (ViewPager) view.findViewById(R.id.viewpager);

		rButton1.setClickable(false);
		rButton2.setClickable(false);
		fragments = new ArrayList<Fragment>();

		 fPk_1 = new FragmentPK_1();
		 fPk_2 = new FragmentPK_2();

		fragments.add(fPk_1);
		fragments.add(fPk_2);

		mAdapter = new FragmentPagerAdapter(getActivity()
				.getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragments.get(arg0);
			}
		};
		viewPager.setAdapter(mAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					rButton1.setChecked(true);
					rButton2.setChecked(false);
					break;
				case 1:
					rButton1.setChecked(false);
					rButton2.setChecked(true);
					break;

				}
				mCurrentPageIndex = position;

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPx) {
				LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) imageView
						.getLayoutParams();
				if (mCurrentPageIndex == 0 && position == 0)// 0->1
				{
					lp.leftMargin = dip2px(getActivity(),60)+(int) (positionOffset * mScreen1_3 + mCurrentPageIndex
							* mScreen1_3);
				} else if (mCurrentPageIndex == 1 && position == 0)// 1->0
				{
					lp.leftMargin = dip2px(getActivity(),60) +(int) (mCurrentPageIndex * mScreen1_3 + (positionOffset - 1)
							* mScreen1_3);
				}
				imageView.setLayoutParams(lp);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	

	}
	public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

}
