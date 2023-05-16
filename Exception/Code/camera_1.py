'''
#完成加入会议的自动化脚本（以飞书会议、腾讯会议为例）
author:wpj
date:2022.2
'''
import touch as touch
import uiautomator2 as u2
#连接手机(adb序列号方式)或者直接用wifi也可以
from uiautomator2 import UiObject
device=u2.connect('6HJDU19B04004216')
'''
#飞书
device(text='飞书').click()
#等待元素出现（开屏缓慢）
device(textContains='更多').wait(timeout=10.0)
#由于飞书的”更多选项上没有给text，所以只能通过xpath绝对定位的方式“
device.xpath('//*[@resource-id="com.ss.android.lark:id/main_tab"]/android.view.ViewGroup[6]').click()
device(text='视频会议').click()
device(text='加入会议').click()
#根据对方提供的会议号加入会议
device.send_keys('647676676')
device(textContains='加入').click()
#打开摄像头的麦克风，
device(resourceId="com.ss.android.lark:id/audio_container").click()
device(resourceId="com.ss.android.lark:id/camera_container").click()
'''


#腾讯会议
device(text='腾讯会议').click()
#等待元素出现（开屏缓慢）
device(textContains='加入会议').wait(timeout=10.0)
device(text='加入会议').click()
#根据对方提供的会议号加入会议
device.send_keys('799649026')
device(resourceId="com.tencent.wemeet.app:id/atp").click()
#打开摄像头的麦克风，
device.xpath('//*[@resource-id="com.tencent.wemeet.app:id/a58"]/android.widget.FrameLayout[1]').click()
device(resourceId="com.tencent.wemeet.app:id/b4_").click()

