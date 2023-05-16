'''
#完成拨出视频电话的自动化脚本
author:wpj
date:2022.2
'''
import touch as touch
import uiautomator2 as u2
import time
#连接手机(adb序列号方式)或者直接用wifi也可以
device=u2.connect('6HJDU19B04004216')

#先点击更多功能按钮
device(description="更多功能按钮，已折叠").click()
#在更多功能的菜单中打开视频通话功能
device(text='视频通话').click()
#开始
device(text='视频通话').click()