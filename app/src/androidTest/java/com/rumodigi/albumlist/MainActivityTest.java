package com.rumodigi.albumlist;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.rumodigi.albumlist.model.Album;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest{


    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testClickLoadAlbumsAndDisplaysSortedList() {
        onView(withId(R.id.load_albums))
                .perform(click());
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        ArrayList<Album> listFromAdapter = activityRule.getActivity().albumAdapter.getAlbumList();
        int randomNumber = new Random().nextInt(listFromAdapter.size() -1);
        String titleToCheck = listFromAdapter.get(randomNumber).getTitle();
        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.scrollToPosition(
                        randomNumber))
                .check(matches(hasDescendant(withText(titleToCheck))));
    }
}