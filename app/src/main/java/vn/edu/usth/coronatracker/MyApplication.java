package vn.edu.usth.coronatracker;

import android.app.Application;

import java.util.Stack;

public class MyApplication extends Application {

    public enum STACKS_FRAGMENT{
        DASHBOARD,
        NEWS,
        INFO,
        PROFILE
    }

    public static Stack<Integer> dashboardStack = new Stack<>();
    public static Stack<Integer> newsStack = new Stack<>();
    public static Stack<Integer> infoStack = new Stack<>();
    public static Stack<Integer> profileStack = new Stack<>();
    public static Stack<Integer> rootStack = new Stack<>();

    public static STACKS_FRAGMENT activeStack = STACKS_FRAGMENT.DASHBOARD;

    @Override
    public void onCreate() {
        super.onCreate();

        rootStack.push(R.id.item_nav_dashboard);
        dashboardStack.push(R.id.navigation_dashboard);
        newsStack.push(R.id.navigation_news);
        infoStack.push(R.id.navigation_info);
        profileStack.push(R.id.navigation_profile);

    }

    public static void addToBackStack(int destinationId){
        if (MyApplication.activeStack == MyApplication.STACKS_FRAGMENT.DASHBOARD){
            MyApplication.dashboardStack.push(destinationId);
        }

        else if (MyApplication.activeStack == MyApplication.STACKS_FRAGMENT.NEWS){
            MyApplication.newsStack.push(destinationId);
        }
        else if (MyApplication.activeStack == MyApplication.STACKS_FRAGMENT.INFO){
            MyApplication.infoStack.push(destinationId);
        }
        else if (MyApplication.activeStack == MyApplication.STACKS_FRAGMENT.PROFILE){
            MyApplication.profileStack.push(destinationId);
        }
    }
}
