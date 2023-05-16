'''
#完成接通视频通话的自动化脚本（以飞书会议、腾讯会议为例）
author:wpj
date:2022.2
'''
import touch as touch
import uiautomator2 as u2
import time
#连接手机(adb序列号方式)或者直接用wifi也可以
device=u2.connect('6HJDU19B04004216')

#直接点击接听即可
device(description="接听").click()