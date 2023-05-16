'''
#完成进入PIP模式的自动化脚本（两种方法，分别以飞书和腾讯会议为例）
author:wpj
date:2022.2
'''
import touch as touch
import uiautomator2 as u2
import time
#连接手机(adb序列号方式)或者直接用wifi也可以
device=u2.connect('6HJDU19B04004216')
'''
# 第一种PIP方式：直接上滑退出
x,y = device.window_size()
x1 = x / 2
y1 = y * 0.99
y2 = y * 0.5
device.swipe(x1,y1,x1,y2)
'''
#第二种PIP方式：点击专门的小窗模式功能后再退出到主屏
device(resourceId="com.tencent.wemeet.app:id/bw6").click()
#实操中这里常常慢一些，所以等待一下
time.sleep (2)
x,y = device.window_size()
x1 = x / 2
y1 = y * 0.99
y2 = y * 0.5
device.swipe(x1,y1,x1,y2)
