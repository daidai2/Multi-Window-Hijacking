'''
#完成侧边栏以FF模式打开某APP的自动化脚本
author:wpj
date:2022.2
'''
import touch as touch
import uiautomator2 as u2
#连接手机(adb序列号方式)或者直接用wifi也可以
device=u2.connect('6HJDU19B04004216')

# 用侧边栏形式打开APP
device.touch.down(1035,585).move(500,585).move(501,585).move(501,586).move(502,586).move(502,587).move(503,587).move(503,588).up(503,588)
#打开被测试的APP
device(text='交通银行').up(clickable='true').click()