import time

import uiautomator2 as u2
import weditor  #方便快速识别手机上的元素，方便写代码
from appium import webdriver
#adb路径：C:\Users\zlllllau\android-sdks\platform-tools
#元素定位工具：C:\Users\zlllllau\android-sdks\tools
#USB链接选项选择”传输文件“，否则abd不能连接
from tornado.gen import Wait
from win32api import Sleep


class Screen_record:
    device_name = "1a4587ef" #oppo机
    app_name = "腾讯会议"
    device = None

    def connect(self,device_name = "1a4587ef"):
        self.device_name = device_name
        self.device=u2.connect(self.device_name)
        print("链接成功")

    #打开对应的app
    def open_app(self, app_name):
        self.app_name = app_name
        self.device.app_start(app_name, stop=True)

    #开启腾讯会议app的录屏功能
    def start_app_screenRecord(self):
        try:
            self.device(textContains='快速会议').click()
        except Exception:
            print("无按钮1")
        try:
            self.device(textContains='进入会议').click()
        except Exception:
            print("无按钮2")
        try:
            self.device(textContains='更多').click()
        except Exception:
            print("无按钮3")
        try:
            self.device(textContains='云录制').click()
        except Exception:
            print("无按钮4")
        # if self.device(textContains='进入会议').exists:
        #     self.device(textContains='进入会议').click()

    #开启手机自身的录屏功能
    def start_syst_screenRecord(self,video_name="test"):
        try:
            #打开通知栏
            self.device.open_notification()
            time.sleep(2)
            self.device.swipe(550, 590, 550, 2000)
            self.device(description='屏幕录制').click()
            #Android已禁用：self.device.screenrecord("{}.mp4".format(video_name))
            self.device(resourceId="com.coloros.screenrecorder:id/iv_play_status").click()
        except Exception:
            print("没有找到屏幕录制按钮")


    #安装应用
    def install_app(self, load_url=""):
        pass

    #打开其他待测app
    def start_another(self,app_pkgname="QQ"):
        #self.device.app_start(app_pkgname, stop=True) 找不到该包名？
        if app_pkgname == "飞书":
            self.device(text="飞书").click()
            self.device(resourceId="com.ss.android.lark:id/textItem", text="更多").click()
            self.device(text="视频会议").click()
            self.device(text="发起会议").click()
            self.device(text="开始会议").click()
            #如果不等待一定时间，会没有小窗出现
            time.sleep(3)
            self.device.press("home")
        if app_pkgname == "QQ":
            self.device(text="QQ").click()
            self.device(resourceId="com.android.systemui:id/recent_apps").click()
            self.device.xpath('//*[@content-desc="QQ"]/android.view.ViewGroup[1]/android.widget.ImageButton[1]').click()
            self.device(resourceId="com.oppo.launcher:id/text", text="浮窗").click()
            self.device.click(584, 1382)
            time.sleep(3)
            self.device.click(319, 707)
            self.device.click(393, 1776)
            #self.device(text="输入QQ密码").set_text("1111")
        if app_pkgname == "微信":
            self.device(text="微信").click()
            self.device(resourceId="com.tencent.mm:id/g5z").click()
            self.device(resourceId="com.tencent.mm:id/knx", text="用密码登录").click()
            self.device(resourceId="com.android.systemui:id/recent_apps").click()
            self.device.xpath('//*[@content-desc="微信"]/android.view.ViewGroup[1]/android.widget.ImageButton[1]').click()
            self.device(resourceId="com.oppo.launcher:id/text", text="浮窗").click()
            self.device.click(542, 1493)
            time.sleep(2)
            self.device.click(393, 1776)
        if app_pkgname == "腾讯视频":
            self.device(text="腾讯视频").click()
            time.sleep(12)
            self.device.click(963, 2182)
            try:
                self.device.xpath('//*[@resource-id="android:id/content"]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.ImageView[1]').click()
            except Exception:
                pass
            #self.device.click(276,295)
            #self.device.xpath('//*[@resource-id="android:id/content"]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[4]').click()
            self.device.xpath('//*[@resource-id="android:id/content"]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[2]').click()
            self.device.xpath('//android.widget.CheckBox').click()
            #开启小窗
            self.device(resourceId="com.android.systemui:id/recent_apps").click()
            self.device.xpath('//*[@content-desc="腾讯视频"]/android.view.ViewGroup[1]/android.widget.ImageButton[1]').click()
            self.device.xpath('//*[@resource-id="com.oppo.launcher:id/drag_layer"]/android.widget.LinearLayout[2]').click()
            self.device.click(450, 1515)
            self.device.click(584, 1382)
            time.sleep(3)
            self.device.click(319, 707)
            self.device.click(393, 1776)
        if app_pkgname == "淘宝":
            self.device(text="淘宝").click()
            self.device.xpath('//*[@content-desc="我的淘宝"]/android.widget.ImageView[1]').click()
            self.device.click(111, 2099)
            self.device.click(612, 1023)
            self.device.click(606, 839)
            time.sleep(3)
            # 进入小窗
            self.device(resourceId="com.android.systemui:id/recent_apps").click()
            self.device.xpath('//*[@content-desc="淘宝"]/android.view.ViewGroup[1]/android.widget.ImageButton[1]').click()
            self.device(resourceId="com.oppo.launcher:id/text", text="浮窗").click()
            self.device.click(644, 1502)
            self.device.click(134, 1865)
        if app_pkgname == "创高体育":
            self.device(text="创高体育").click()
            time.sleep(3)
            #进入小窗
            self.device(resourceId="com.android.systemui:id/recent_apps").click()
            time.sleep(2)
            self.device.xpath('//*[@content-desc="创高体育"]/android.view.ViewGroup[1]/android.widget.ImageButton[1]').click()
            self.device(resourceId="com.oppo.launcher:id/text", text="浮窗").click()
            self.device(resourceId="net.crigh.cgsport:id/btn_login").click()
            self.device.click(535, 1736)
            self.device.click(579, 1674)
        if app_pkgname == "YouTube":
            self.device(text="YouTube").click()
            self.device(resourceId="com.google.android.youtube:id/image", description="帐号").click()
            self.device(resourceId="com.google.android.youtube:id/button").click()
            self.device(resourceId="com.google.android.youtube:id/add_account").click()
            time.sleep(5)
            self.device.click(381, 789)
            #进入小窗---失败
        if app_pkgname == "TikTok":
            self.device(text="TikTok").click()
            self.device.xpath('//*[@resource-id="com.zhiliaoapp.musically:id/egn"]/android.widget.ImageView[1]').click()
            self.device(resourceId="com.zhiliaoapp.musically:id/agd").click()
            #self.device.click(814, 2189)
            #self.device.xpath('//*[@resource-id="com.zhiliaoapp.musically:id/anz"]/android.view.ViewGroup[1]').click()
            #进入小窗
            self.device(resourceId="com.android.systemui:id/recent_apps").click()
            self.device.xpath('//*[@content-desc="TikTok"]/android.view.ViewGroup[1]/android.widget.ImageButton[1]').click()
            self.device(resourceId="com.oppo.launcher:id/text", text="浮窗").click()
            self.device.click(727, 2097)
            self.device.click(574, 1532)
            time.sleep(2)
            self.device.click(283, 1670)
        if app_pkgname == "Twitter":
            self.device(text="Twitter").click()
            time.sleep(3)
            self.device.click(408, 2165)
            time.sleep(2)
            #进入小窗
            self.device(resourceId="com.android.systemui:id/recent_apps").click()
            self.device.xpath('//*[@content-desc="Twitter"]/android.view.ViewGroup[1]/android.widget.ImageButton[1]').click()
            self.device(resourceId="com.oppo.launcher:id/text", text="浮窗").click()
            #输入信息
            self.device.click(339, 1463)
            self.device.click(332, 1892)
        if app_pkgname == "Teams":
            self.device(text="Teams").click()
            time.sleep(5)
            #进入小窗
            self.device(resourceId="com.android.systemui:id/recent_apps").click()
            self.device.xpath('//*[@content-desc="Teams"]/android.view.ViewGroup[1]/android.widget.ImageButton[1]').click()
            self.device(resourceId="com.oppo.launcher:id/text", text="浮窗").click()
            self.device.click(456, 1644)
            time.sleep(2)
            self.device.click(72, 1673)
            self.device(resourceId="com.microsoft.teams:id/sign_in_button").click()
        if app_pkgname == "Netflix":
            self.device(text="Netflix").click()
            time.sleep(3)
            # 进入小窗
            self.device(resourceId="com.android.systemui:id/recent_apps").click()
            self.device.xpath('//*[@content-desc="Netflix"]/android.view.ViewGroup[1]/android.widget.ImageButton[1]').click()
            self.device(resourceId="com.oppo.launcher:id/text", text="浮窗").click()
            self.device.click(782, 1087)
            self.device.click(669, 752)
            self.device.click(86, 1605)
            self.device.click(464, 778)
            self.device.click(86, 1605)
        if app_pkgname == "Zoom":
            self.device(text="Zoom").click()
            time.sleep(3)
            # 进入小窗
            self.device(resourceId="com.android.systemui:id/recent_apps").click()
            self.device.xpath('//*[@content-desc="Zoom"]/android.view.ViewGroup[1]/android.widget.ImageButton[1]').click()
            self.device(resourceId="com.oppo.launcher:id/text", text="浮窗").click()
            self.device.click(604, 2021)
            self.device.click(592, 1349)
            self.device.click(89, 1614)
    def test_process(self,device_name = "1a4587ef",app_test_name="QQ",app_name="com.tencent.wemeet.app"):
        self.connect(device_name)
        #打开腾讯会议录屏
        #self.open_app(app_name)
        #self.start_app_screenRecord()
        app_name_list = ["飞书", "QQ", "微信", "腾讯视频", "淘宝", "创高体育", "YouTube", "TikTok","Twitter",
                         "Teams", "Netflix", "Zoom"]
        #打开手机系统录屏
        self.start_syst_screenRecord()
        #打开另一个待测APP
        app_test_name = app_name_list[11]
        self.start_another(app_test_name)
        #结束手机录屏
        time.sleep(5)
        self.device.click(126, 1622)
        self.device.click(137, 1625)
#获取包名：
#adb shell pm list package （无需要root）
if __name__ == '__main__':
    test = Screen_record()
    test.test_process()


