package vn.edu.usth.coronatracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Stack;

import vn.edu.usth.coronatracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN ACTIVITY";
    private ActivityMainBinding activityMainBinding;
//    private Fragment selectorFragment;
//    private FragmentManager fragmentManager;
//    private DashboardFragment dashboardFragment;
//    private NewsFragment newsFragment;
//    private InfoFragment infoFragment;
//    private ProfileFragment profileFragment;
//    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        activityMainBinding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_nav_dashboard:
                        routeToDestinationDashboard(item.getItemId());
                        break;
                    case R.id.item_nav_news:
                        routeToDestinationNews(item.getItemId());
                        break;
                    case R.id.item_nav_info:
                        routeToDestinationInfo(item.getItemId());
                        break;
                    case R.id.item_nav_profile:
                        routeToDestinationProfile(item.getItemId());
                        break;
                }
                return true;
            }
        });
        Navigation.findNavController(this, R.id.frameLayout).addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController
                                                     controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.d(TAG, MyApplication.activeStack + " ");
            }
        });

    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");

        if (MyApplication.activeStack == MyApplication.STACKS_FRAGMENT.DASHBOARD) {
            if (MyApplication.dashboardStack.size() > 1) {
                MyApplication.dashboardStack.pop();
                navigateFragment(MyApplication.dashboardStack);
            } else {
                performRootBack();
            }
        } else if (MyApplication.activeStack == MyApplication.STACKS_FRAGMENT.NEWS) {
            if (MyApplication.newsStack.size() > 1) {
                MyApplication.newsStack.pop();
                navigateFragment(MyApplication.newsStack);

            } else {
                performRootBack();
            }
        } else if (MyApplication.activeStack == MyApplication.STACKS_FRAGMENT.INFO) {
            if (MyApplication.infoStack.size() > 1) {
                MyApplication.infoStack.pop();
                navigateFragment(MyApplication.infoStack);

            } else {
                performRootBack();
            }
        } else if (MyApplication.activeStack == MyApplication.STACKS_FRAGMENT.PROFILE) {
            if (MyApplication.profileStack.size() > 1) {
                MyApplication.profileStack.pop();
                navigateFragment(MyApplication.profileStack);
            } else {
                performRootBack();
            }
        }

        Log.d(TAG, MyApplication.rootStack.toString());
    }

    private void routeToDestinationDashboard(int itemId) {
        MyApplication.activeStack = MyApplication.STACKS_FRAGMENT.DASHBOARD;
        pushToRootStack(itemId);
        navigateFragment(MyApplication.dashboardStack);
        Log.d(TAG, MyApplication.rootStack.toString());
    }

    private void routeToDestinationNews(int itemId) {
        MyApplication.activeStack = MyApplication.STACKS_FRAGMENT.NEWS;
        pushToRootStack(itemId);
        navigateFragment(MyApplication.newsStack);
        Log.d(TAG, MyApplication.rootStack.toString());
    }

    private void routeToDestinationInfo(int itemId) {

        MyApplication.activeStack = MyApplication.STACKS_FRAGMENT.INFO;
        pushToRootStack(itemId);
        navigateFragment(MyApplication.infoStack);
        Log.d(TAG, MyApplication.rootStack.toString());
    }

    private void routeToDestinationProfile(int itemId) {

        MyApplication.activeStack = MyApplication.STACKS_FRAGMENT.PROFILE;
        pushToRootStack(itemId);
        navigateFragment(MyApplication.profileStack);
        Log.d(TAG, MyApplication.rootStack.toString());
    }


    private void navigateFragment(Stack<Integer> stack) {
        if (!Navigation.findNavController(this, R.id.frameLayout).popBackStack(stack.peek(), false)) {
            Navigation.findNavController(this, R.id.frameLayout).navigate(stack.peek());
        }
    }

    private void performRootBack() {

        if (MyApplication.rootStack.size() > 1) {
            MyApplication.rootStack.pop();
            activityMainBinding.bottomNavigationView.setSelectedItemId(MyApplication.rootStack.peek());
        } else {
//            MyApplication.rootStack.pop();
//            MyApplication.rootStack.push(R.id.item_navigation_home);
//            navView.setSelectedItemId(R.id.item_navigation_home);
            finish();
        }
    }


    private void pushToRootStack(int itemId) {
        int elementIndex = MyApplication.rootStack.indexOf(itemId);
        Log.d(TAG, elementIndex + "");
        if (elementIndex == -1) {
            MyApplication.rootStack.push(itemId);
        } else {
            MyApplication.rootStack.removeElementAt(elementIndex);
            MyApplication.rootStack.push(itemId);
        }
    }
}



//        dashboardFragment = new DashboardFragment();
//        newsFragment = new NewsFragment();
//        infoFragment = new InfoFragment();
//        profileFragment = new ProfileFragment();
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.nav_home:
//                        pushFragments(DashboardFragment.TAG, dashboardFragment);
//                        break;
//
//                    case R.id.nav_news:
//                        pushFragments(NewsFragment.TAG, newsFragment);
//                        break;
//
//                    case R.id.nav_info:
//                        pushFragments(InfoFragment.TAG, infoFragment);
//                        break;
//
//                    case R.id.nav_profile:
//                        pushFragments(ProfileFragment.TAG, profileFragment);
//                        break;
//                }
////                pushFragments(DashboardFragment.TAG, dashboardFragment);
////                if (selectorFragment != null) {
////                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////                    ft.replace(R.id.framelayout, selectorFragment, selectorFragment.getTag());
////                    ft.addToBackStack(null);
////                    ft.commit();
////                    return true;
////                }
//
////                if (selectorFragment != null) {
////                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, selectorFragment).addToBackStack(null).commit();
////                }
//
//                return true;
//
//            }
//        });
////        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new DashboardFragment()).addToBackStack(null).commit();
//    }
//
//    public void pushFragments(String tag, Fragment fragment) {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction ft = manager.beginTransaction();
////        ft.setCustomAnimations(
////                R.anim.slide_in_right,
////                R.anim.slide_out_left,
////                R.anim.slide_in_left,
////                R.anim.slide_out_right
////        );
//        if (manager.findFragmentByTag(tag) == null) {
//            ft.add(R.id.framelayout, fragment, tag);
//        }
//
//        Fragment fragmentDashboard = manager.findFragmentByTag(DashboardFragment.TAG);
//        Fragment fragmentNews = manager.findFragmentByTag(NewsFragment.TAG);
//        Fragment fragmentInfo = manager.findFragmentByTag(InfoFragment.TAG);
//        Fragment fragmentProfile = manager.findFragmentByTag(ProfileFragment.TAG);
//        Fragment fragmentGlobal = manager.findFragmentByTag(GlobalFragment.TAG);
//        Fragment fragmentCountry = manager.findFragmentByTag(CountryFragment.TAG);
//        Fragment fragmentCountryDetail = manager.findFragmentByTag(CountryDetailFragment.TAG);
//
//
//        // Hide all Fragment
//        if (fragmentDashboard != null) {
//            ft.hide(fragmentDashboard);
//        }
//        if (fragmentNews != null) {
//            ft.hide(fragmentNews);
//        }
//        if (fragmentInfo != null) {
//            ft.hide(fragmentInfo);
//        }
//        if (fragmentProfile != null) {
//            ft.hide(fragmentProfile);
//        }
//        if (fragmentGlobal != null) {
//            ft.hide(fragmentGlobal);
//        }
//        if (fragmentCountry != null) {
//            ft.hide(fragmentCountry);
//        }
//        if (fragmentCountryDetail != null) {
//            ft.hide(fragmentCountryDetail);
//        }
//
//        // Show  current Fragment
//        if (tag.equals(DashboardFragment.TAG)) {
//            if (fragmentDashboard != null) {
//                ft.show(fragmentDashboard);
//            }
//        }
//        if (tag.equals(NewsFragment.TAG)) {
//            if (fragmentNews != null) {
//                ft.show(fragmentNews);
//            }
//        }
//        if (tag.equals(InfoFragment.TAG)) {
//            if (fragmentInfo != null) {
//                ft.show(fragmentInfo);
//            }
//        }
//        if (tag.equals(ProfileFragment.TAG)) {
//            if (fragmentProfile != null) {
//                ft.show(fragmentProfile);
//            }
//        }
//        if (tag.equals(GlobalFragment.TAG)) {
//            if (fragmentGlobal != null) {
//                ft.show(fragmentGlobal);
//            }
//        }
//        if (tag.equals(CountryFragment.TAG)) {
//            if (fragmentCountry != null) {
//                ft.show(fragmentCountry);
//            }
//        }
//        if (tag.equals(CountryDetailFragment.TAG)) {
//            if (fragmentCountryDetail != null) {
//                ft.show(fragmentCountryDetail);
//            }
//        }
//
//        ft.commitAllowingStateLoss();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }
//    }