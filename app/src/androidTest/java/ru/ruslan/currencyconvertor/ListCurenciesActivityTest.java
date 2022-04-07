package ru.ruslan.currencyconvertor;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.ruslan.currencyconvertor.activities.ListCurenciesActivity;

@RunWith(AndroidJUnit4.class)
public class ListCurenciesActivityTest {

    //@Rule
    //ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);
    //ActivityScenario<ListCurrencyActivity> activityScenario = ActivityScenario.launch(ListCurrencyActivity.class);

    //TODO При использовании новых классов падает приложение и появляется ошибка -
    // (java.lang.AssertionError: Activity never becomes requested state "[DESTROYED, RESUMED, STARTED, CREATED]"
    // (last lifecycle transition = "PRE_ON_CREATE"))

    @Rule
    public ActivityTestRule<ListCurenciesActivity> rule = new ActivityTestRule<>(ListCurenciesActivity.class);

    @Test
    public void test_recyclerView_click() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.calculationActivity)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()));
    }
}