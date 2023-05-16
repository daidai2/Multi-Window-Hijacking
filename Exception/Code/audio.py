'''
#完成语音条录制的自动化脚本
author:wpj
date:2022.2
'''

import touch as touch
import uiautomator2 as u2
import time
#连接手机(adb序列号方式)或者直接用wifi也可以
device=u2.connect('6HJDU19B04004216')

#切换到录制语音功能
device(description="切换到按住说话").click()
#通过长按按钮完成语音录入
device(description="按住说话").long_click(duration=5,timeout=10)
