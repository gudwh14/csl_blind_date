package com.csl.csl_blinddate;

import android.app.Application;
import android.content.Context;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // SDK 초기화
        KakaoSDK.init(new KakaoAdapter() {

            public ISessionConfig getSessionConfig() {
                return new ISessionConfig() {
                    @Override
                    public AuthType[] getAuthTypes() {
                        return new AuthType[] {AuthType.KAKAO_TALK_ONLY};
                    }

                    @Override
                    public boolean isUsingWebviewTimer() {
                        return false;
                    }

                    @Override
                    public boolean isSecureMode() {
                        return false;
                    }

                    @Override
                    public ApprovalType getApprovalType() {
                        return ApprovalType.INDIVIDUAL;
                    }

                    @Override
                    public boolean isSaveFormData() {
                        return true;
                    }
                };
            }


            @Override
            public IApplicationConfig getApplicationConfig() {
                return new IApplicationConfig() {
                    @Override
                    public Context getApplicationContext() {
                        return App.this;
                    }
                };
            }
        });
    }



}
