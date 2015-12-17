package com.braintreepayments.testutils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static com.braintreepayments.testutils.SharedPreferencesHelper.getSharedPreferences;

public class BraintreeActivityTestRule<T extends Activity> extends ActivityTestRule<T> {

    private KeyguardLock mKeyguardLock;

    public BraintreeActivityTestRule(Class<T> activityClass) {
        super(activityClass);
        init();
    }

    public BraintreeActivityTestRule(Class<T> activityClass, boolean initialTouchMode,
            boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
        init();
    }

    private void init() {
        getSharedPreferences().edit().clear().commit();

        mKeyguardLock = ((KeyguardManager) getTargetContext().getSystemService(Context.KEYGUARD_SERVICE))
                .newKeyguardLock("BraintreeActivityTestRule");
        mKeyguardLock.disableKeyguard();
    }

    @Override
    protected void afterActivityLaunched() {
        Intents.init();
        super.afterActivityLaunched();
    }

    @Override
    protected void afterActivityFinished() {
        super.afterActivityFinished();

        try {
            Intents.release();
        } catch (IllegalStateException ignored) {}

        getSharedPreferences().edit().clear().commit();

        mKeyguardLock.reenableKeyguard();
    }
}
