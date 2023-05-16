    #打开视频软件并进入小窗模式
    def start_video(self, v_app):
        self.device(text=v_app).click()
        time.sleep(7)
        if v_app == "爱奇艺":
            self.device.xpath('//androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[1]/android.widget.RelativeLayout[1]/android.widget.ImageView[1]').click()
            time.sleep(120) #广告
            #开启画中画模式
            self.device(resourceId="com.android.systemui:id/center_group").click()
        if v_app == "人人视频":
            #该APP无画中画模式
            self.device.xpath('//*[@resource-id="com.example.pptv:id/rv_content"]/android.view.ViewGroup[1]/androidx.recyclerview.widget.RecyclerView[1]/android.view.ViewGroup[1]/android.widget.ImageView[1]').click()
            time.sleep(20)  #广告
        if v_app == "Plex":
            self.device.xpath('//*[@resource-id="com.plexapp.android:id/content_container"]/android.view.ViewGroup[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[1]').click()
            self.device(resourceId="com.plexapp.android:id/play").click()
            time.sleep(20)
            #开启画中画模式(只有Plex在画中画时，会顺利开启。)
            self.device.click(200,200)
            self.device(resourceId="com.android.systemui:id/center_group").click()
        if v_app == "咪咕视频":
            self.device.xpath('//*[@resource-id="com.cmcc.cmvideo:id/fragment_container"]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.view.ViewGroup[4]/android.widget.FrameLayout[1]').click()
            self.device.click(200, 200)
            time.sleep(61)
            #小窗的设计：需要点击小窗按钮。
            self.device.click(200, 200)
            self.device.xpath('//*[@resource-id="com.cmcc.cmvideo:id/playViewContainer"]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.view.ViewGroup[1]/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.ViewGroup[2]').click()
            self.device(resourceId="com.android.systemui:id/center_group").click()
        if v_app == "腾讯视频":
            self.device.xpath('//androidx.viewpager.widget.ViewPager/android.widget.RelativeLayout[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[1]/android.widget.RelativeLayout[1]').click()
            time.sleep(80)   #广告时长
            self.device.click(200, 200)
            self.device.xpath('//android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[4]/android.widget.ImageView[1]').click()
        if v_app == "优酷视频":
            self.device(resourceId="com.youku.phone:id/light_widget_gif_view_id").click()
            time.sleep(90)
            self.device.click(200, 200)
            self.device(resourceId="com.youku.phone:id/lottie_small_pip_btn").click()
            self.device(resourceId="com.android.systemui:id/center_group").click()
        if v_app == "哔哩哔哩":
            self.device(description="热门,6之3,标签").click()
            self.device.xpath('//*[@resource-id="tv.danmaku.bili:id/recycler_view"]/android.widget.LinearLayout[1]/android.view.ViewGroup[1]/android.widget.ImageView[1]').click()
            time.sleep(2)
            self.device.click(200,200)
            self.device(resourceId="tv.danmaku.bili:id/float_window").click()
            self.device(resourceId="com.android.systemui:id/center_group").click()
        if v_app == "搜狐视频":
            self.device(resourceId="com.sohu.sohuvideo:id/tv_normal", text="首页").click()
            self.device.xpath('//*[@resource-id="com.sohu.sohuvideo:id/rl_refresh_layout"]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.LinearLayout[4]/android.widget.FrameLayout[1]/android.widget.ImageView[1]').click()
            #广告
            time.sleep(50)
            self.device(resourceId="com.android.systemui:id/center_group").click()
        if v_app == "乐视视频":
            self.device.xpath('//*[@resource-id="com.letv.android.client:id/pull_list"]/android.widget.LinearLayout[3]/android.widget.RelativeLayout[1]/android.widget.FrameLayout[1]').click()
            time.sleep(70)
            self.device.click(200,200)
            self.device.click(1005, 405)
            self.device(resourceId="com.android.systemui:id/center_group").click()
        if v_app == "芒果TV":
            self.device.xpath('//*[@resource-id="com.hunantv.imgo.activity:id/viewPager"]/android.view.ViewGroup[1]').click()
            #Uiautomator2 弹窗处理
            self.device.watcher.when('com.hunantv.imgo.activity:id/btnClose').click()
            self.device.watcher.when('知道了').click()
            # 广告
            time.sleep(65)
            self.device.click(200, 200)
            self.device(resourceId="com.hunantv.imgo.activity:id/tv_float_window_play").click()


    def test_process_VideoCOnflict(self, device_name = "1a4587ef"):
        self.connect(device_name)
        v_name_list = ["爱奇艺", "人人视频", "Plex", "咪咕视频", "腾讯视频", "优酷视频", "哔哩哔哩", "搜狐视频", "乐视视频", "芒果TV"]
        #打开视频软件
        # for v_name in v_name_list:
        self.start_video(v_name_list[9])



#获取包名：
#adb shell pm list package （无需要root）
if __name__ == '__main__':
    test = Screen_record()
    #test.test_process_RecordConflict()
    test.test_process_VideoCOnflict()