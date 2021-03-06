package com.example.autandroidapp;

import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.fragment.app.Fragment;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MapActivityLaunchTestWithNorthImage {

    @Rule
    public ActivityTestRule<MapActivity> mapActivityTestRule = new ActivityTestRule<MapActivity>(MapActivity.class);
    private MapActivity mapActivity = null;

    // A method to get a map activity for testing
    @Before
    public void setUp() throws Exception {
        mapActivity = mapActivityTestRule.getActivity();
    }

    @Test
    // A method to test launching a mapActivity, with map images of north campus
    public void mapLaunch(){
        View view = mapActivity.findViewById(R.id.imageViewNorthCampus);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        mapActivity = null;
    }
}