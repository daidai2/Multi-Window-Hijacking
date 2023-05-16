'''
#完成穿透问题的自动化脚本
author:wpj
date:2022.2
'''
import touch as touch
import uiautomator2 as u2
#连接手机(adb序列号方式)或者直接用wifi也可以
device=u2.connect('6HJDU19B04004216')
print(device.device_info)
#主屏打开APP1
device(text='QQ').click()
# 用侧边栏形式打开APP2
device.touch.down(1035,585).move(500,585).move(501,585).move(501,586).move(502,586).move(502,587).move(503,587).move(503,588).up(503,588)
#打开被测试的APP
device(text='微信').up(clickable='true').click()

#完成持续点击观察是否穿透
device(clickable='false').click_gone(interval=1.0)