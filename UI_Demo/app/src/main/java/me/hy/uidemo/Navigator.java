package me.hy.uidemo;
import android.content.Context;
import android.content.Intent;

/**
 * 此处使用枚举实现单例, 枚举实现单例是单例的最佳实践,
 * 参见What is an efficient way to implement a singleton pattern in Java?,
 * 或者看Effective Java第三条。
 *
 * https://stackoverflow.com/questions/70689/what-is-an-efficient-way-to-implement-a-singleton-pattern-in-java
 */
public enum Navigator {
    INSTANCE;
    public void navigateToSecond(Context context){
        if(context != null){
            Intent callingIntent = TimeActivity.getCallingIntent(context);
            context.startActivity(callingIntent);
        }
    }
}
